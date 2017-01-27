package fr.ensimag.ima.pseudocode.instructions;

import static fr.ensimag.deca.codegen.MemoryManagement.freeRegister;
import static fr.ensimag.deca.codegen.MemoryManagement.increNumberInternalCycles;
import fr.ensimag.ima.pseudocode.BinaryInstruction;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Register;

/**
 * @author Ensimag
 * @date 01/01/2017
 */
public class STORE extends BinaryInstruction {
    public STORE(Register op1, DAddr op2) {
        super(op1, op2);
        increNumberInternalCycles(2);
        if (op1 instanceof GPRegister) {
            freeRegister(((GPRegister) op1).getNumber());
        }
    }
}
