/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ensimag.deca.codegen;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tree.AbstractExpr;
import fr.ensimag.deca.tree.Identifier;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Label;
import static fr.ensimag.ima.pseudocode.Register.getR;
import fr.ensimag.ima.pseudocode.instructions.ADDSP;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructions.ERROR;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.OPP;
import fr.ensimag.ima.pseudocode.instructions.PUSH;
import fr.ensimag.ima.pseudocode.instructions.RFLOAT;
import fr.ensimag.ima.pseudocode.instructions.RINT;
import fr.ensimag.ima.pseudocode.instructions.STORE;
import fr.ensimag.ima.pseudocode.instructions.WFLOAT;
import fr.ensimag.ima.pseudocode.instructions.TSTO;
import fr.ensimag.ima.pseudocode.instructions.WINT;
import fr.ensimag.ima.pseudocode.instructions.WNL;
import fr.ensimag.ima.pseudocode.instructions.WSTR;

/**
 *
 * @author macintosh
 */
public class MemoryManagement {

    private static int numberGlobalVariables = 0;
    private static int RMAX = 15;
    private static boolean[] avaRegs = {true, true, true, true,
        true, true, true, true,
        true, true, true, true,
        true, true, true, true};
    private static int lastReg = 2;
    private static int numberSavedRegisters = 0;
    private static boolean ioUsed = false;

    public static int getNumberGlobalVariables() {
        return ++numberGlobalVariables;
    }

    public static void setRMAX(int max) {
        RMAX = max;
    }

    public static GPRegister getAvailableRegister(DecacCompiler compiler) {
        int i;
        for (i = 2; i <= RMAX; i++) {
            if (avaRegs[i]) {
                avaRegs[i] = false;
                lastReg = i;
                break;
            }
        }

        if (i == RMAX + 1) {
            compiler.addInstruction(new PUSH(getR(2)), "sauvegarde");
            numberSavedRegisters++;
            return getR(2);
        }
        return getR(i);
    }

    public static GPRegister getLastUsedRegisterToStore() {
        return getR(lastReg);
    }

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
        avaRegs[lastReg] = true;
        compiler.addInstruction(new STORE(getR(lastReg), val));
    }

    public static void setLastUsedRegister(int val) {
        lastReg = val;
    }

    public static void addTestOverflow(DecacCompiler compiler) {
        int i = numberSavedRegisters + numberGlobalVariables;
        compiler.addFirst(new ADDSP(i));
        compiler.addFirst(new BOV(new Label("stack_overflow_error")));
        compiler.addFirst(new TSTO(i), "test de debordement de pile");
        compiler.addFirstComment("start main program");
        compiler.addLabel(new Label("stack_overflow_error"));
        compiler.addInstruction(new WSTR("Error: Overflow during arithmetic operation"));
        compiler.addInstruction(new WNL());
        compiler.addInstruction(new ERROR());
        addTestIO(compiler);
    }

    public static void codeGenReadFloat(DecacCompiler compiler) {
        ioUsed = true;
        compiler.addInstruction(new RFLOAT());
        compiler.addFirst(new BOV(new Label("io_error")));
        compiler.addInstruction(new LOAD(getR(1), getAvailableRegister(compiler)));
    }

    public static void codeGenReadInt(DecacCompiler compiler) {
        ioUsed = true;
        compiler.addInstruction(new RINT());
        compiler.addFirst(new BOV(new Label("io_error")));
        compiler.addInstruction(new LOAD(getR(1), getAvailableRegister(compiler)));
    }

    public static void addTestIO(DecacCompiler compiler) {
        if (ioUsed) {
            compiler.addLabel(new Label("io_error"));
            compiler.addInstruction(new WSTR("Error: Input/Output error"));
            compiler.addInstruction(new WNL());
            compiler.addInstruction(new ERROR());
        }
    }
}
