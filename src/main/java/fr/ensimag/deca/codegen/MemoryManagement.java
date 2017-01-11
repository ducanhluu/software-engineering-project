/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ensimag.deca.codegen;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Register;
import static fr.ensimag.ima.pseudocode.Register.getR;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.POP;
import fr.ensimag.ima.pseudocode.instructions.PUSH;
import fr.ensimag.ima.pseudocode.instructions.WINT;

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
    public static int getNumberGlobalVariables() {
        return ++numberGlobalVariables;
    }
    
    public static void setRMAX(int max) {
        RMAX = max;
    }
    
    public static GPRegister getAvailableRegister() {
        int i;
        for (i = 2; i <= RMAX; i++) {
            if (avaRegs[i]) {
                avaRegs[i] = false;
                lastReg = i;
                break;
            }
        }
        return getR(i);
    }
    
    public static GPRegister getLastUsedRegisterToStore() {
        avaRegs[lastReg] = true;
        return getR(lastReg);
    }
    
    public static void codeGenPrintInteger(DecacCompiler compiler, int value) {
        if (avaRegs[1]) {
            compiler.addInstruction(new LOAD(value, getR(1)));
            compiler.addInstruction(new WINT());
            avaRegs[1] = false;
        } else {
            compiler.addInstruction(new PUSH(getR(1)));
            compiler.addInstruction(new LOAD(value, getR(1)));
            compiler.addInstruction(new WINT());
            compiler.addInstruction(new POP(getR(1)));
        }
    }
}
