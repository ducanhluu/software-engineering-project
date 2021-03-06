package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import static fr.ensimag.deca.codegen.MemoryManagement.overflowOPNeeded;
import static fr.ensimag.deca.codegen.MemoryManagement.setLastUsedRegister;
import fr.ensimag.ima.pseudocode.IMAProgram;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructions.MUL;



/**
 * @author gl17
 * @date 01/01/2017
 */
public class Multiply extends AbstractOpArith {
    public Multiply(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return "*";
    }
 @Override
    protected void codeGenInst(IMAProgram compiler) {
        super.codeGenInst(compiler);
        compiler.addInstruction(new MUL(val,reg));
        if (!(getLeftOperand().getType().isInt() && getRightOperand().getType().isInt())) {
            overflowOPNeeded = true;
            compiler.addInstruction(new BOV(new Label("overflow_error")));
        }
        setLastUsedRegister(reg.getNumber());
    }
}
