/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ensimag.deca.codegen;

import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.IMAProgram;
import static fr.ensimag.ima.pseudocode.Register.getR;
import fr.ensimag.ima.pseudocode.instructions.PUSH;
import java.util.Deque;
import java.util.LinkedList;

/**
 * Management of registers
 *
 * @author gl17
 * @date 13/01/2017
 */
public class MemoryManagement {

    private static int RMAX = 15;
    private static boolean[] avaRegs = {true, true, true, true,
        true, true, true, true,
        true, true, true, true,
        true, true, true, true};
    private static boolean[] mRegs = {true, true, true, true,
        true, true, true, true,
        true, true, true, true,
        true, true, true, true};
    private static Deque<GPRegister> pushedRegs = new LinkedList<GPRegister>();
    private static DAddr daddr;
    private static int lastReg = 2;
    private static int numberSavedRegisters = 0;
    private static int numberSavedRegistersInMet = 0;
    private static int numberGlobalVariables = 0;
    private static int sizeOfVTables = 0;
    private static int numberLocalVariables = 0;
    private static int numberTempMots = 0; //nombre maximal de paramètres des méthodes appelées
    
    public static boolean dereferencementNull = false;
    public static boolean overflowOPNeeded = false;
    public static boolean divisionIsUsed = false;
    public static boolean overflowNeeded = false;
    public static boolean heapOverflowNeeded = false;
    public static boolean returnNeeded = false;
    public static boolean isMain = true;
    

    public static Deque<GPRegister> getPusedRegs() {
        return pushedRegs;
    }
    
    public static void increNumberSavedRegisters(int val) {
        numberSavedRegisters += val;
    }
    
    public static void increNumberTempMots(int val) {
        numberTempMots += val;
    }
    
    public static int getNumberTempMots() {
        return numberTempMots;
    }
    
    public static DAddr getDAddr() {
        return daddr;
    }

    public static void setDAddr(DAddr val) {
        daddr = val;
    }

    public static void increNumberGlobalVariables() {
        numberGlobalVariables++;
    }

    public static void increNumberLocalVariables() {
        numberLocalVariables++;
    }
    
    public static int getNumberGlobalVariables() {
        return numberGlobalVariables;
    }

    public static int getNumberLocalVariables() {
        return numberLocalVariables;
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

    public static void freeRegisters() {
        for (int i = 2; i <= RMAX; i++) {
            avaRegs[i] = true;
        }
        pushedRegs.clear();
        numberLocalVariables = 0;
        numberSavedRegistersInMet = 0;
    }

    public static void freeRegister(int i) {
        avaRegs[i] = true;
    }

    public static GPRegister getAvailableRegister(IMAProgram compiler) {
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
            numberSavedRegistersInMet++;
            return getR(2);
        }
        if (!pushedRegs.contains(getR(i))) {
            pushedRegs.addFirst(getR(i));
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
    
    public static int getNumberSavedRegistersInMet() {
        return numberSavedRegistersInMet;
    }
}
