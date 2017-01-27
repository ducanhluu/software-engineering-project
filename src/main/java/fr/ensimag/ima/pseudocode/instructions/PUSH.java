package fr.ensimag.ima.pseudocode.instructions;

import static fr.ensimag.deca.codegen.MemoryManagement.increNumberInternalCycles;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.UnaryInstruction;

/**
 * @author Ensimag
 * @date 01/01/2017
 */
public class PUSH extends UnaryInstruction {
    public PUSH(Register op1) {
        super(op1);
        increNumberInternalCycles(4);
    }
}
