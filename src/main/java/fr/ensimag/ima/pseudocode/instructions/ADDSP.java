package fr.ensimag.ima.pseudocode.instructions;

import static fr.ensimag.deca.codegen.MemoryManagement.increNumberInternalCycles;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.UnaryInstructionImmInt;

/**
 * Add a value to stack pointer.
 * 
 * @author Ensimag
 * @date 01/01/2017
 */
public class ADDSP extends UnaryInstructionImmInt {

    public ADDSP(ImmediateInteger operand) {
        super(operand);
        increNumberInternalCycles(4);
    }

    public ADDSP(int i) {
        super(i);
        increNumberInternalCycles(4);
    }

}
