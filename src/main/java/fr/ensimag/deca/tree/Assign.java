package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import static fr.ensimag.deca.codegen.MemoryManagement.getAvailableRegister;
import static fr.ensimag.deca.codegen.MemoryManagement.getLastUsedRegisterToStore;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.Definition;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
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
        return (AbstractLValue) super.getLeftOperand();
    }

    public Assign(AbstractLValue leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        Type type = this.getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
        if (this.getRightOperand() instanceof AbstractReadExpr) {
            Type type1 = this.getRightOperand().verifyExpr(compiler, localEnv, currentClass);
            if (!type1.sameType(type)) {
                throw new ContextualError("variable not compatible to instruction read", this.getLocation());
            }
        } else {
            this.getRightOperand().verifyRValue(compiler, localEnv, currentClass, type);
        }
        //j'ai intialiser le type de l'instruction assign
        this.setType(type);
        return type;
    }

    @Override
    protected String getOperatorName() {
        return "=";
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        AbstractExpr lvalue = getLeftOperand();
        AbstractExpr rvalue = getRightOperand();
        //Il y a 2 cas: un Identifier ou une selection
        if (lvalue instanceof Identifier) {
            if (rvalue instanceof Identifier) {
                compiler.addInstruction(new LOAD(((Identifier) rvalue).getVariableDefinition().getOperand(),
                        getAvailableRegister(compiler)));

            } else {
                rvalue.codeGenInst(compiler);
            }
            lvalue.codeGenInst(compiler);
        }
    }
}
