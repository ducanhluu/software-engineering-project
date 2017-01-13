package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import static fr.ensimag.deca.codegen.MemoryManagement.setLastUsedRegister;
import fr.ensimag.ima.pseudocode.instructions.ADD;

/**
 * @author gl17
 * @date 01/01/2017
 */
public class Plus extends AbstractOpArith {
    public Plus(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return "+";
    }
    
    
    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        super.codeGenInst(compiler);
        compiler.addInstruction(new ADD(val, reg));
        setLastUsedRegister(reg.getNumber());
    }
}
