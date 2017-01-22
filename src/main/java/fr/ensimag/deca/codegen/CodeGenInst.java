/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ensimag.deca.codegen;

import static fr.ensimag.deca.codegen.MemoryManagement.dereferencementNull;
import static fr.ensimag.deca.codegen.MemoryManagement.divisionIsUsed;
import static fr.ensimag.deca.codegen.MemoryManagement.freeLastUsedRegister;
import static fr.ensimag.deca.codegen.MemoryManagement.getAvailableRegister;
import static fr.ensimag.deca.codegen.MemoryManagement.getLastUsedRegisterToStore;
import static fr.ensimag.deca.codegen.MemoryManagement.getNumberGlobalVariables;
import static fr.ensimag.deca.codegen.MemoryManagement.getNumberSavedRegisters;
import static fr.ensimag.deca.codegen.MemoryManagement.getNumberTempMots;
import static fr.ensimag.deca.codegen.MemoryManagement.getSizeOfVTables;
import static fr.ensimag.deca.codegen.MemoryManagement.heapOverflowNeeded;
import static fr.ensimag.deca.codegen.MemoryManagement.overflowNeeded;
import static fr.ensimag.deca.codegen.MemoryManagement.overflowOPNeeded;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.IMAProgram;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Line;
import static fr.ensimag.ima.pseudocode.Register.getR;
import fr.ensimag.ima.pseudocode.instructions.ADDSP;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructions.ERROR;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.RFLOAT;
import fr.ensimag.ima.pseudocode.instructions.RINT;
import fr.ensimag.ima.pseudocode.instructions.STORE;
import fr.ensimag.ima.pseudocode.instructions.TSTO;
import fr.ensimag.ima.pseudocode.instructions.WFLOAT;
import fr.ensimag.ima.pseudocode.instructions.WINT;
import fr.ensimag.ima.pseudocode.instructions.WNL;
import fr.ensimag.ima.pseudocode.instructions.WSTR;

/**
 * CodeGen
 *
 * @author gl17
 * @date 13/01/2017
 */
public class CodeGenInst {

    private static Label label;
    private static Label labelFin;
    private static boolean ioIsUsed = false;

    public static void codeGenPrintInteger(IMAProgram compiler, int value) {
        compiler.addInstruction(new LOAD(value, getR(1)));
        compiler.addInstruction(new WINT());
    }

    public static void codeGenPrintInteger(IMAProgram compiler, DAddr val) {
        compiler.addInstruction(new LOAD(val, getR(1)));
        compiler.addInstruction(new WINT());
    }

    public static void codeGenPrintFloat(IMAProgram compiler, float val) {
        compiler.addInstruction(new LOAD(val, getR(1)));
        compiler.addInstruction(new WFLOAT());
    }

    public static void codeGenPrintFloat(IMAProgram compiler, DAddr val) {
        compiler.addInstruction(new LOAD(val, getR(1)));
        compiler.addInstruction(new WFLOAT());
    }

    public static void codeGenSaveLastValue(IMAProgram compiler, DAddr val) {
        freeLastUsedRegister();
        compiler.addInstruction(new STORE(getLastUsedRegisterToStore(), val));
    }

    public static void addTestOverall(IMAProgram compiler) {
        addEPOverflow(compiler);
        addEPIO(compiler);
        addEPOverflowOP(compiler);
        addEPHeapOverflow(compiler);
        addEPDivideBy0(compiler);
        addEPDereferencementNull(compiler);
    }

    public static void addEPOverflow(IMAProgram compiler) {
        int i = getNumberSavedRegisters() + getNumberGlobalVariables() + getSizeOfVTables() + getNumberTempMots();
        if (i > 0) {
            overflowNeeded = true;
            compiler.addFirst(new ADDSP(getNumberGlobalVariables() + getSizeOfVTables()));
            compiler.addFirst(new BOV(new Label("stack_overflow_error")));
            compiler.addFirst(new TSTO(i), "test de debordement de pile");
            compiler.addFirst(new Line("start main program"));
        }

        if (overflowNeeded) {
            compiler.addComment("----------------------------------------------------");
            compiler.addComment("          Error Message: Stack Overflow Error");
            compiler.addComment("----------------------------------------------------");
            compiler.addLabel(new Label("stack_overflow_error"));
            compiler.addInstruction(new WSTR("Error: Stack Overflow"));
            compiler.addInstruction(new WNL());
            compiler.addInstruction(new ERROR());
        }
    }

    public static void codeGenReadFloat(IMAProgram compiler) {
        ioIsUsed = true;
        compiler.addInstruction(new RFLOAT());
        compiler.addInstruction(new BOV(new Label("io_error")));
        compiler.addInstruction(new LOAD(getR(1), getAvailableRegister(compiler)));
    }

    public static void codeGenReadInt(IMAProgram compiler) {
        ioIsUsed = true;
        compiler.addInstruction(new RINT());
        compiler.addInstruction(new BOV(new Label("io_error")));
        compiler.addInstruction(new LOAD(getR(1), getAvailableRegister(compiler)));
    }

    public static void addEPIO(IMAProgram compiler) {
        if (ioIsUsed) {
            compiler.addComment("----------------------------------------------------");
            compiler.addComment("          Error Message: IO Error");
            compiler.addComment("----------------------------------------------------");
            compiler.addLabel(new Label("io_error"));
            compiler.addInstruction(new WSTR("Error: Input/Output error"));
            compiler.addInstruction(new WNL());
            compiler.addInstruction(new ERROR());
        }
    }

    public static void addEPOverflowOP(IMAProgram compiler) {
        if (overflowOPNeeded) {
            compiler.addComment("----------------------------------------------------");
            compiler.addComment("          Error Message: Overflow Error");
            compiler.addComment("----------------------------------------------------");
            compiler.addLabel(new Label("overflow_error"));
            compiler.addInstruction(new WSTR("Error: Overflow during arithmetic operation"));
            compiler.addInstruction(new WNL());
            compiler.addInstruction(new ERROR());
        }
    }

    public static void addEPHeapOverflow(IMAProgram compiler) {
        if (heapOverflowNeeded) {
            compiler.addComment("----------------------------------------------------");
            compiler.addComment("          Error Message: Heap Overflow Error");
            compiler.addComment("----------------------------------------------------");
            compiler.addLabel(new Label("heap_overflow_error"));
            compiler.addInstruction(new WSTR("Error: Impossible allocation, heap overflow"));
            compiler.addInstruction(new WNL());
            compiler.addInstruction(new ERROR());
        }
    }

    public static void addEPDivideBy0(IMAProgram compiler) {
        if (divisionIsUsed) {
            compiler.addComment("----------------------------------------------------");
            compiler.addComment("          Error Message: Division By Zero Error");
            compiler.addComment("----------------------------------------------------");
            compiler.addLabel(new Label("division_by_zero_error"));
            compiler.addInstruction(new WSTR("Error: division by zero"));
            compiler.addInstruction(new WNL());
            compiler.addInstruction(new ERROR());
        }
    }

    public static void addEPDereferencementNull(IMAProgram compiler) {
        
        if (dereferencementNull) {
            compiler.addComment("----------------------------------------------------");
            compiler.addComment("          Error Message: Dereferencement Null Error");
            compiler.addComment("----------------------------------------------------");
            compiler.addLabel(new Label("dereferencement_null_error"));
            compiler.addInstruction(new WSTR("Error: Dereferencement null"));
            compiler.addInstruction(new WNL());
            compiler.addInstruction(new ERROR());
        }
    }

    public static void setLabel(Label lab) {
        label = lab;
    }

    public static void setLabelFin(Label lab) {
        labelFin = lab;
    }

    public static Label getLabel() {
        return label;
    }

    public static Label getLabelFin() {
        return labelFin;
    }
}
