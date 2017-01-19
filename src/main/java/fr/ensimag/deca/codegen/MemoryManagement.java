/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ensimag.deca.codegen;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.GPRegister;
import static fr.ensimag.ima.pseudocode.Register.getR;
import fr.ensimag.ima.pseudocode.instructions.PUSH;

/**
 * Management of registers
 *
 * @author gl17
 * @date 13/01/2017
 */
public class MemoryManagement {

    private static int numberGlobalVariables = 0;
    private static int sizeOfVTables = 0;
    private static int RMAX = 15;
    private static boolean[] avaRegs = {true, true, true, true,
        true, true, true, true,
        true, true, true, true,
        true, true, true, true};
    private static int lastReg = 2;
    private static int numberSavedRegisters = 0;
    public static boolean overflowNeeded = false;
    public static boolean divisionIsUsed = false;

    public static int getNumberGlobalVariables() {
        return ++numberGlobalVariables;
    }
    
    public static int getSizeOfVTables() {
        return sizeOfVTables;
    }
    
    public static void increSizeOfVtables(int val) {
        sizeOfVTables += val;
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

    public static void setLastUsedRegister(int val) {
        lastReg = val;
    }

    public static void freeLastUsedRegister() {
        avaRegs[lastReg] = true;
    }

    public static int getNumberSavedRegisters() {
        return numberSavedRegisters;
    }
}
