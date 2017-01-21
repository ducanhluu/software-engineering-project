package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import static fr.ensimag.deca.codegen.CodeGenInst.getLabel;
import static fr.ensimag.deca.codegen.CodeGenInst.getLabelFin;
import static fr.ensimag.deca.codegen.CodeGenInst.setLabel;
import static fr.ensimag.deca.codegen.MemoryManagement.getAvailableRegister;
import static fr.ensimag.deca.codegen.MemoryManagement.getLastUsedRegisterToStore;
import static fr.ensimag.deca.tree.Assign.ass;
import static fr.ensimag.deca.tree.DeclVar.dec;
import static fr.ensimag.deca.tree.IfThenElse.Opp;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.ADD;
import fr.ensimag.ima.pseudocode.instructions.BEQ;
import fr.ensimag.ima.pseudocode.instructions.BNE;
import fr.ensimag.ima.pseudocode.instructions.BRA;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.MUL;

/**
 *
 * @author gl17
 * @date 01/01/2017
 */
public class And extends AbstractOpBool {

    protected static int and = 0;

    public And(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected String getOperatorName() {
        return "&&";
    }

    private Label labelAnd;
    private Label labelTmpAnd;
    private static int nbLabel = 0;

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        AbstractExpr lvalue = getLeftOperand();
        AbstractExpr rvalue = getRightOperand();
        GPRegister Tmp;
        if (ass == 1 || dec == 1) {
            lvalue.codeGenInst(compiler);
            Tmp = getLastUsedRegisterToStore();
            rvalue.codeGenInst(compiler);
            compiler.addInstruction(new MUL(Tmp, getLastUsedRegisterToStore()));
        } else {
            if (Opp == 0) {
                labelTmpAnd = getLabel();
                setLabelAnd();
                setLabel(getLabelAnd());
                if (getLeftOperand() instanceof AbstractBinaryExpr) {
                    lvalue.codeGenInst(compiler);
                } else {
                    lvalue.codeGenInst(compiler);
                    compiler.addInstruction(new BNE(getLabel()));
                }
                compiler.addInstruction(new BRA(getLabelFin()));
                compiler.addLabel(getLabelAnd());
                setLabel(labelTmpAnd);
                if (getRightOperand() instanceof AbstractBinaryExpr) {
                    rvalue.codeGenInst(compiler);
                } else {
                    rvalue.codeGenInst(compiler);
                    compiler.addInstruction(new BNE(getLabel()));
                }
            } else {
                if (getLeftOperand() instanceof AbstractBinaryExpr) {
                    lvalue.codeGenInst(compiler);
                } else {
                    lvalue.codeGenInst(compiler);
                    compiler.addInstruction(new BEQ(getLabel()));
                }

                if (getRightOperand() instanceof AbstractBinaryExpr) {
                    rvalue.codeGenInst(compiler);
                } else {
                    rvalue.codeGenInst(compiler);
                    compiler.addInstruction(new BEQ(getLabel()));
                }
            }

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
