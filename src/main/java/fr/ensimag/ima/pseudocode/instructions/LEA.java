package fr.ensimag.ima.pseudocode.instructions;

import static fr.ensimag.deca.codegen.MemoryManagement.increNumberInternalCycles;
import fr.ensimag.ima.pseudocode.BinaryInstructionDAddrToReg;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.GPRegister;

/**
 * @author Ensimag
 * @date 01/01/2017
 */
public class LEA extends BinaryInstructionDAddrToReg {

    public LEA(DAddr op1, GPRegister op2) {
        super(op1, op2);
        increNumberInternalCycles(0);
    }

}
