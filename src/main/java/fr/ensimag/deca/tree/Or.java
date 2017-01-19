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

    private Label labelOr;
    private Label labelTmpOr;
    private Label labelOrFin;

    private static int nbLabel = 0;

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        // si dans un while
        if (Opp == 0) { 
            if (getLeftOperand() instanceof BooleanLiteral) {
                if (((BooleanLiteral) getLeftOperand()).getValue()) {
                    compiler.addInstruction(new BRA(getLabel()));
                }
            } else if (getRightOperand() instanceof BooleanLiteral) {
                if (((BooleanLiteral) getRightOperand()).getValue()) {
                    compiler.addInstruction(new BRA(getLabel()));
                }
            } else {
                getLeftOperand().codeGenInst(compiler);
                getRightOperand().codeGenInst(compiler);
            }
        // si dans un if
        } else {
            labelTmpOr = getLabel();
            setLabelOr();
            setLabel(getLabelOr());
            //En cas de booléen a gauche:
            if (getLeftOperand() instanceof BooleanLiteral) {
                if (!(((BooleanLiteral) getLeftOperand()).getValue())) {
                    compiler.addInstruction(new BRA(getLabel()));
                }
            //Pas booléen :
            } else {
                getLeftOperand().codeGenInst(compiler);
            }
            compiler.addInstruction(new BRA(labelOrFin));
            compiler.addLabel(getLabelOr());
            setLabel(labelTmpOr);
            //En cas de booléen a gauche:
            if (getRightOperand() instanceof BooleanLiteral) {
                if (!(((BooleanLiteral) getRightOperand()).getValue())) {
                    compiler.addInstruction(new BRA(getLabel()));
                }
            //Pas de Booléen :
            } else {
                getRightOperand().codeGenInst(compiler);
            }
            compiler.addLabel(labelOrFin);
        }
    }

    private void setLabelOr() {
        nbLabel++;
        labelOr = new Label("E_Or." + nbLabel);
        labelOrFin = new Label("E_Or_Fin." + nbLabel);
    }

    private Label getLabelOr() {
        return labelOr;
    }

}
