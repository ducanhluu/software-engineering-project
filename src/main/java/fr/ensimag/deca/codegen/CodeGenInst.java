/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ensimag.deca.codegen;

import fr.ensimag.deca.DecacCompiler;
import static fr.ensimag.deca.codegen.MemoryManagement.divisionIsUsed;
import static fr.ensimag.deca.codegen.MemoryManagement.freeLastUsedRegister;
import static fr.ensimag.deca.codegen.MemoryManagement.getAvailableRegister;
import static fr.ensimag.deca.codegen.MemoryManagement.getLastUsedRegisterToStore;
import static fr.ensimag.deca.codegen.MemoryManagement.getNumberGlobalVariables;
import static fr.ensimag.deca.codegen.MemoryManagement.getNumberSavedRegisters;
import static fr.ensimag.deca.codegen.MemoryManagement.overflowNeeded;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.Label;
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
    private static boolean ioIsUsed = false;
    
    public static void codeGenPrintInteger(DecacCompiler compiler, int value) {
        compiler.addInstruction(new LOAD(value, getR(1)));
        compiler.addInstruction(new WINT());
    }

    public static void codeGenPrintInteger(DecacCompiler compiler, DAddr val) {
        compiler.addInstruction(new LOAD(val, getR(1)));
        compiler.addInstruction(new WINT());
    }

    public static void codeGenPrintFloat(DecacCompiler compiler, float val) {
        compiler.addInstruction(new LOAD(val, getR(1)));
        compiler.addInstruction(new WFLOAT());
    }

    public static void codeGenPrintFloat(DecacCompiler compiler, DAddr val) {
        compiler.addInstruction(new LOAD(val, getR(1)));
        compiler.addInstruction(new WFLOAT());
    }
    
    public static void codeGenSaveLastValue(DecacCompiler compiler, DAddr val) {
        freeLastUsedRegister(); 
        compiler.addInstruction(new STORE(getLastUsedRegisterToStore(), val));
    }
    
    public static void addTestOverall(DecacCompiler compiler) {
        addTestOverflow(compiler);
        addTestIO(compiler);
        addTestOverflowOP(compiler);
        addTestDivideBy0(compiler);
    }
    
    public static void addTestOverflow(DecacCompiler compiler) {
        int i = getNumberSavedRegisters() + getNumberGlobalVariables();
        if (i > 0) {
            compiler.addFirst(new ADDSP(i));
            compiler.addFirst(new BOV(new Label("stack_overflow_error")));
            compiler.addFirst(new TSTO(i), "test de debordement de pile");
            compiler.addFirstComment("start main program");
            compiler.addLabel(new Label("stack_overflow_error"));
            compiler.addInstruction(new WSTR("Error: Stack Overflow"));
            compiler.addInstruction(new WNL());
            compiler.addInstruction(new ERROR());
        }
    }

    public static void codeGenReadFloat(DecacCompiler compiler) {
        ioIsUsed = true;
        compiler.addInstruction(new RFLOAT());
        compiler.addInstruction(new BOV(new Label("io_error")));
        compiler.addInstruction(new LOAD(getR(1), getAvailableRegister(compiler)));
    }

    public static void codeGenReadInt(DecacCompiler compiler) {
        ioIsUsed = true;
        compiler.addInstruction(new RINT());
        compiler.addInstruction(new BOV(new Label("io_error")));
        compiler.addInstruction(new LOAD(getR(1), getAvailableRegister(compiler)));
    }

    public static void addTestIO(DecacCompiler compiler) {
        if (ioIsUsed) {
            compiler.addLabel(new Label("io_error"));
            compiler.addInstruction(new WSTR("Error: Input/Output error"));
            compiler.addInstruction(new WNL());
            compiler.addInstruction(new ERROR());
        }
    }

    public static void addTestOverflowOP(DecacCompiler compiler) {
        if (overflowNeeded) {
            compiler.addLabel(new Label("overflow_error"));
            compiler.addInstruction(new WSTR("Error: Overflow during arithmetic operation"));
            compiler.addInstruction(new WNL());
            compiler.addInstruction(new ERROR());
        }
    }

    public static void addTestDivideBy0(DecacCompiler compiler) {
        if (divisionIsUsed) {
            compiler.addLabel(new Label("division_by_zero_error"));
            compiler.addInstruction(new WSTR("Error: division by zero"));
            compiler.addInstruction(new WNL());
            compiler.addInstruction(new ERROR());
        }
    }

    public static void setLabel(Label lab) {
        label = lab;
    }

    public static Label getLabel() {
        return label;
    }
}