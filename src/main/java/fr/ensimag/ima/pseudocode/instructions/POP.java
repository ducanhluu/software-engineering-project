package fr.ensimag.ima.pseudocode.instructions;

import static fr.ensimag.deca.codegen.MemoryManagement.increNumberInternalCycles;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.UnaryInstructionToReg;

/**
 *
 * @author Ensimag
 * @date 01/01/2017
 */
public class POP extends UnaryInstructionToReg {

    public POP(GPRegister op) {
        super(op);
        increNumberInternalCycles(2);
    }

}
