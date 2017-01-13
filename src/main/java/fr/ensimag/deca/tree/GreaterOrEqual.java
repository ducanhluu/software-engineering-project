package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import static fr.ensimag.deca.tree.While.getLabelDebut;
import fr.ensimag.ima.pseudocode.instructions.BLE;


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
        compiler.addInstruction(new BLE(getLabelDebut()));
    }
    
}
