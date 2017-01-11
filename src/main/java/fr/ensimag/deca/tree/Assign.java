package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import static fr.ensimag.deca.codegen.MemoryManagement.getLastUsedRegisterToStore;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.Definition;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.ima.pseudocode.instructions.STORE;

/**
 * Assignment, i.e. lvalue = expr.
 *
 * @author gl17
 * @date 01/01/2017
 */
public class Assign extends AbstractBinaryExpr {

    @Override
    public AbstractLValue getLeftOperand() {
        // The cast succeeds by construction, as the leftOperand has been set
        // as an AbstractLValue by the constructor.
        return (AbstractLValue)super.getLeftOperand();
    }

    public Assign(AbstractLValue leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        this.getLeftOperand().verifyExpr(compiler,null, null);
        this.getRightOperand().verifyExpr(compiler, null,null);
        //throw new UnsupportedOperationException("not yet implemented");
        return null;
    }


    @Override
    protected String getOperatorName() {
        return "=";
    }
    
    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        getRightOperand().codeGenInst(compiler);
        //Il y a 2 cas: un Identifier ou une selection
        if (getLeftOperand() instanceof Identifier) {
            getLeftOperand().codeGenInst(compiler);
        }
    }
}
