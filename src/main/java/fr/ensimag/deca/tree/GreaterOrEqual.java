package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import static fr.ensimag.deca.codegen.CodeGenInst.getLabel;
import static fr.ensimag.deca.codegen.CodeGenInst.getLabelFalse;
import static fr.ensimag.deca.codegen.CodeGenInst.getLabelTrue;
import static fr.ensimag.deca.codegen.CodeGenInst.setLabel;
import static fr.ensimag.deca.codegen.CodeGenInst.setLabelFalse;
import static fr.ensimag.deca.codegen.CodeGenInst.setLabelTrue;
import static fr.ensimag.deca.codegen.MemoryManagement.getAvailableRegister;
import static fr.ensimag.deca.tree.Assign.ass;
import static fr.ensimag.deca.tree.DeclVar.dec;
import static fr.ensimag.deca.tree.IfThenElse.Opp;
import fr.ensimag.ima.pseudocode.instructions.BGE;
import fr.ensimag.ima.pseudocode.instructions.BLT;
import fr.ensimag.ima.pseudocode.instructions.BRA;
import fr.ensimag.ima.pseudocode.instructions.LOAD;

/**
 * Operator "x >= y"
 *
 * @author gl17
 * @date 01/01/2017
 */
public class GreaterOrEqual extends AbstractOpIneq {

    public GreaterOrEqual(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected String getOperatorName() {
        return ">=";
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        super.codeGenInst(compiler);
        if (ass == 1 || dec == 1) {
            setLabelFalse();
            setLabelTrue();
            compiler.addInstruction(new BLT(getLabelFalse()));
            compiler.addInstruction(new LOAD(1, getAvailableRegister(compiler)));
            compiler.addInstruction(new BRA(getLabelTrue()));
            compiler.addLabel(getLabelFalse());
            compiler.addInstruction(new LOAD(0, getAvailableRegister(compiler)));
            compiler.addLabel(getLabelTrue());
        }else{
        if (Opp == 0) {
            compiler.addInstruction(new BGE(getLabel()));
        } else {
            compiler.addInstruction(new BLT(getLabel()));
        }
    }
    }
}
