package fr.ensimag.ima.pseudocode.instructions;

import static fr.ensimag.deca.codegen.MemoryManagement.freeRegister;
import static fr.ensimag.deca.codegen.MemoryManagement.increNumberInternalCycles;
import fr.ensimag.ima.pseudocode.BinaryInstructionDValToReg;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.ImmediateInteger;

/**
 *
 * @author Ensimag
 * @date 01/01/2017
 */
public class CMP extends BinaryInstructionDValToReg {

    public CMP(DVal op1, GPRegister op2) {
        super(op1, op2);
        increNumberInternalCycles(2);
        if (op1 instanceof GPRegister) {
            freeRegister(((GPRegister) op1).getNumber());
        }
        freeRegister(op2.getNumber());
    }

    public CMP(int val, GPRegister op2) {
        this(new ImmediateInteger(val), op2);
    }

}
