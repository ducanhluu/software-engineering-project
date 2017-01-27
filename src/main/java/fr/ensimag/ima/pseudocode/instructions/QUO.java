package fr.ensimag.ima.pseudocode.instructions;

import static fr.ensimag.deca.codegen.MemoryManagement.increNumberInternalCycles;
import fr.ensimag.ima.pseudocode.BinaryInstructionDValToReg;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.GPRegister;

/**
 *
 * @author Ensimag
 * @date 01/01/2017
 */
public class QUO extends BinaryInstructionDValToReg {

    public QUO(DVal op1, GPRegister op2) {
        super(op1, op2);
        increNumberInternalCycles(40);
    }

}
