package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import static fr.ensimag.deca.tree.While.getLabelDebut;
import fr.ensimag.ima.pseudocode.instructions.BLT;


/**
 *
 * @author gl17
 * @date 01/01/2017
 */
public class Greater extends AbstractOpIneq {

    public Greater(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return ">";
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        super.codeGenInst(compiler);
        compiler.addInstruction(new BLT(getLabelDebut()));
    }
    
}
