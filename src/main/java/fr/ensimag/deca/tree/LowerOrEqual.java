package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import static fr.ensimag.deca.codegen.CodeGenInst.getLabel;

import static fr.ensimag.deca.codegen.MemoryManagement.getAvailableRegister;
import static fr.ensimag.deca.tree.Assign.ass;
import static fr.ensimag.deca.tree.DeclVar.dec;
import static fr.ensimag.deca.tree.IfThenElse.Opp;
import fr.ensimag.ima.pseudocode.instructions.BGT;
import fr.ensimag.ima.pseudocode.instructions.BLE;
import fr.ensimag.ima.pseudocode.instructions.SLE;


/**
 *
 * @author gl17
 * @date 01/01/2017
 */
public class LowerOrEqual extends AbstractOpIneq {

    public LowerOrEqual(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected String getOperatorName() {
        return "<=";
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        super.codeGenInst(compiler);
        if (ass == 1 || dec == 1) {
            compiler.addInstruction(new SLE(getAvailableRegister(compiler)));
        } else {
            if (Opp == 0) {
                compiler.addInstruction(new BLE(getLabel()));
            } else {
                compiler.addInstruction(new BGT(getLabel()));
            }
        }
    }
}
