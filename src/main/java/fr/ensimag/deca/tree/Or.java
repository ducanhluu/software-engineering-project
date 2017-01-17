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
public class Or extends AbstractOpBool {

    public Or(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected String getOperatorName() {
        return "||";
    }

    protected static Label labelOr;
    protected static Label labelTmpOr;
    private static int nbLabel = 0;
    
    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        if (Opp == 0) {
            getLeftOperand().codeGenInst(compiler);
            getRightOperand().codeGenInst(compiler);
        } else {
            labelTmpOr = getLabel();
            setLabelOr();
            setLabel(getLabelOr());
            getLeftOperand().codeGenInst(compiler);
            compiler.addInstruction(new BRA(getLabelFin()));
            compiler.addLabel(getLabelOr());
            setLabel(labelTmpOr);
            getRightOperand().codeGenInst(compiler);
        }
    }
        
    private void setLabelOr() {
        nbLabel++;
        labelOr = new Label("E_Or." + nbLabel);
    }

    private Label getLabelOr(){
        return labelOr;
    }

}
