package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import static fr.ensimag.deca.codegen.CodeGenInst.codeGenSaveLastValue;
import static fr.ensimag.deca.codegen.CodeGenInst.getLabel;
import static fr.ensimag.deca.codegen.CodeGenInst.getLabelFin;
import static fr.ensimag.deca.codegen.CodeGenInst.setLabel;
import static fr.ensimag.deca.codegen.MemoryManagement.getAvailableRegister;
import static fr.ensimag.deca.tree.Assign.ass;
import static fr.ensimag.deca.tree.DeclVar.dec;
import static fr.ensimag.deca.tree.IfThenElse.Opp;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.BEQ;
import fr.ensimag.ima.pseudocode.instructions.BNE;
import fr.ensimag.ima.pseudocode.instructions.BRA;
import fr.ensimag.ima.pseudocode.instructions.LOAD;

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
        AbstractExpr lvalue = getLeftOperand();
        AbstractExpr rvalue = getRightOperand();
        // si dans un while
        setLabelOr();
        if (ass == 1|| dec ==1) {
            boolean tmp;
            tmp = true;
            if (getLeftOperand() instanceof AbstractBinaryExpr) {
                lvalue.codeGenInst(compiler);
            } else {
                if (((BooleanLiteral) lvalue).getValue() == true) {
                    lvalue.codeGenInst(compiler);
                    compiler.addInstruction(new LOAD(1, getAvailableRegister(compiler)));
                }else{
                    tmp = false;
                }
            }
            if (getRightOperand() instanceof AbstractBinaryExpr) {
                rvalue.codeGenInst(compiler);
            } else {
                if (((BooleanLiteral) rvalue).getValue() == true) {
                    rvalue.codeGenInst(compiler);
                    compiler.addInstruction(new LOAD(1, getAvailableRegister(compiler)));
                }else if(tmp == false){
                    rvalue.codeGenInst(compiler);
                    compiler.addInstruction(new LOAD(0, getAvailableRegister(compiler)));
                }
            }
        } else {
            if (Opp == 0) {
                if (getLeftOperand() instanceof AbstractBinaryExpr) {
                    lvalue.codeGenInst(compiler);
                } else {
                    lvalue.codeGenInst(compiler);
                    compiler.addInstruction(new BNE(getLabel()));
                }
                
                if (getRightOperand() instanceof AbstractBinaryExpr) {
                    rvalue.codeGenInst(compiler);
                } else {
                    rvalue.codeGenInst(compiler);
                    compiler.addInstruction(new BNE(getLabel()));
                }

                // si dans un if
            } else {
                labelTmpOr = getLabel();
                setLabel(getLabelOr());
                if (getLeftOperand() instanceof AbstractBinaryExpr) {
                    lvalue.codeGenInst(compiler);
                } else {
                    lvalue.codeGenInst(compiler);
                    compiler.addInstruction(new BEQ(getLabel()));
                }
                
                compiler.addInstruction(new BRA(labelOrFin));
                compiler.addLabel(getLabelOr());
                setLabel(labelTmpOr);
                if (getRightOperand() instanceof AbstractBinaryExpr) {
                    rvalue.codeGenInst(compiler);
                } else {
                    rvalue.codeGenInst(compiler);
                    compiler.addInstruction(new BEQ(getLabel()));
                }
                compiler.addLabel(labelOrFin);
            }
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
