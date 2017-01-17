package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import static fr.ensimag.deca.codegen.CodeGenInst.getLabel;
import static fr.ensimag.deca.codegen.CodeGenInst.getLabelFin;
import static fr.ensimag.deca.codegen.CodeGenInst.setLabel;
import static fr.ensimag.deca.tree.IfThenElse.Opp;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.BRA;

/**
 *
 * @author gl17
 * @date 01/01/2017
 */
public class And extends AbstractOpBool {

    public And(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected String getOperatorName() {
        return "&&";
    }

    protected static Label labelAnd;
    protected static Label labelTmpAnd;
    private static int nbLabel = 0;

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        if (Opp == 0) {
            labelTmpAnd = getLabel();
            setLabelAnd();
            setLabel(getLabelAnd());
            getLeftOperand().codeGenInst(compiler);
            compiler.addInstruction(new BRA(getLabelFin()));
            compiler.addLabel(getLabelAnd());
            setLabel(labelTmpAnd);
            getRightOperand().codeGenInst(compiler);
        } else {
            getLeftOperand().codeGenInst(compiler);
            getRightOperand().codeGenInst(compiler);
        }

    }

    private void setLabelAnd() {
        nbLabel++;
        labelAnd = new Label("E_And." + nbLabel);
    }

    private Label getLabelAnd() {
        return labelAnd;
    }

}
