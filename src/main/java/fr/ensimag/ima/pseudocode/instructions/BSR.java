package fr.ensimag.ima.pseudocode.instructions;

import static fr.ensimag.deca.codegen.MemoryManagement.increNumberInternalCycles;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.LabelOperand;
import fr.ensimag.ima.pseudocode.UnaryInstruction;

/**
 * @author Ensimag
 * @date 01/01/2017
 */
public class BSR extends UnaryInstruction {

    public BSR(DVal operand) {
        super(operand);
        increNumberInternalCycles(9);
    }
    
    public BSR(Label target) {
        super(new LabelOperand(target));
        increNumberInternalCycles(9);
    }

}
