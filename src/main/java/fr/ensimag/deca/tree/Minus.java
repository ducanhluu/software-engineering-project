package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import static fr.ensimag.deca.codegen.MemoryManagement.freeRegister;
import static fr.ensimag.deca.codegen.MemoryManagement.getAvailableRegister;
import static fr.ensimag.deca.codegen.MemoryManagement.getLastUsedRegisterToStore;
import static fr.ensimag.deca.codegen.MemoryManagement.setLastUsedRegister;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.IMAProgram;
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
    protected void codeGenInst(IMAProgram compiler) {
        super.codeGenInst(compiler);
        
        if (getRightOperand() instanceof Identifier && !(getLeftOperand() instanceof Identifier)) {
            if (!((Identifier) getRightOperand()).getDefinition().isParam()) {
                compiler.addInstruction(new LOAD(((Identifier) getRightOperand()).getExpDefinition().getOperand(), getAvailableRegister(compiler)));
                GPRegister reg2 = getLastUsedRegisterToStore();
                compiler.addInstruction(new SUB(reg, reg2));
                setLastUsedRegister(reg2.getNumber());
            } else {
                GPRegister reg2 = getLastUsedRegisterToStore();
                compiler.addInstruction(new SUB(val, reg2));
                setLastUsedRegister(reg2.getNumber());
            }
        } else {
            compiler.addInstruction(new SUB(val, reg));
            setLastUsedRegister(reg.getNumber());
        }
    }
    
}
