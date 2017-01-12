package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import static fr.ensimag.deca.codegen.MemoryManagement.getAvailableRegister;
import static fr.ensimag.deca.codegen.MemoryManagement.getLastUsedRegisterToStore;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.SUB;

/**
 * @author gl17
 * @date 01/01/2017
 */
public class Minus extends AbstractOpArith {

    public Minus(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected String getOperatorName() {
        return "-";
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        super.codeGenInst(compiler);
        
        if (getRightOperand() instanceof Identifier) {
            compiler.addInstruction(new LOAD(((Identifier) getRightOperand()).getVariableDefinition().getOperand(), getAvailableRegister(compiler)));
            GPRegister reg2 = getLastUsedRegisterToStore();
            compiler.addInstruction(new SUB(reg, reg2));
        } else {
            compiler.addInstruction(new SUB(val, reg));
        }
    }
    
}
