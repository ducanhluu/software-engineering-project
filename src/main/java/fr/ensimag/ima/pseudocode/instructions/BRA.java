package fr.ensimag.ima.pseudocode.instructions;

import static fr.ensimag.deca.codegen.MemoryManagement.increNumberInternalCycles;
import fr.ensimag.ima.pseudocode.BranchInstruction;
import fr.ensimag.ima.pseudocode.Label;

/**
 *
 * @author Ensimag
 * @date 01/01/2017
 */
public class BRA extends BranchInstruction {

    public BRA(Label op) {
        super(op);
        increNumberInternalCycles(5);
    }

}
