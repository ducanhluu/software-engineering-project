package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import static fr.ensimag.deca.codegen.MemoryManagement.getLastUsedRegisterToStore;
import static fr.ensimag.deca.tree.While.getLabel;
import fr.ensimag.ima.pseudocode.instructions.BEQ;

/**
 *
 * @author gl17
 * @date 01/01/2017
 */
public class Equals extends AbstractOpExactCmp {

    public Equals(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected String getOperatorName() {
        return "==";
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        super.codeGenInst(compiler);
        //if (getLastUsedRegisterToStore(). == 0) {
            compiler.addInstruction(new BEQ(getLabel()));
        //}
    }
}