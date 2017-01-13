package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;


/**
 *
 * @author gl17
 * @date 01/01/2017
 */
public class Or extends AbstractOpBool {

    public Or(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected String getOperatorName() {
        return "||";
    }

      @Override
    protected void codeGenInst(DecacCompiler compiler) {   
        getLeftOperand().codeGenInst(compiler);
        getRightOperand().codeGenInst(compiler);
    }

}
