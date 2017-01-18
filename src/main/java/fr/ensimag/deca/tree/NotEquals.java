package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import static fr.ensimag.deca.codegen.CodeGenInst.getLabel;
import static fr.ensimag.deca.tree.IfThenElse.Opp;
import fr.ensimag.ima.pseudocode.instructions.BEQ;
import fr.ensimag.ima.pseudocode.instructions.BNE;


/**
 *
 * @author gl17
 * @date 01/01/2017
 */
public class NotEquals extends AbstractOpExactCmp {

    public NotEquals(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return "!=";
    }
    
    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        super.codeGenInst(compiler);
        if (Opp == 0) {
            compiler.addInstruction(new BNE(getLabel()));
        } else {
            compiler.addInstruction(new BEQ(getLabel()));
        }
    }

}
