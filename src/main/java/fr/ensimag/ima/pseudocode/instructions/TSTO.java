package fr.ensimag.ima.pseudocode.instructions;

import static fr.ensimag.deca.codegen.MemoryManagement.increNumberInternalCycles;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.UnaryInstructionImmInt;

/**
 * @author Ensimag
 * @date 01/01/2017
 */
public class TSTO extends UnaryInstructionImmInt {
    public TSTO(ImmediateInteger i) {
        super(i);
        increNumberInternalCycles(4);
    }

    public TSTO(int i) {
        super(i);
        increNumberInternalCycles(4);
    }
}
