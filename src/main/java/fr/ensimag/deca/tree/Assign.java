package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import static fr.ensimag.deca.codegen.CodeGenInst.codeGenSaveLastValue;
import static fr.ensimag.deca.codegen.MemoryManagement.freeRegister;
import static fr.ensimag.deca.codegen.MemoryManagement.getDAddr;
import static fr.ensimag.deca.codegen.MemoryManagement.getLastUsedRegisterToStore;
import static fr.ensimag.deca.codegen.MemoryManagement.setDAddr;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.IMAProgram;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.STORE;

/**
 * Assignment, i.e. lvalue = expr.
 *
 * @author gl17
 * @date 01/01/2017
 */
public class Assign extends AbstractBinaryExpr {

    protected static int ass = 0;

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
    protected void codeGenInst(IMAProgram compiler) {
        AbstractExpr lvalue = getLeftOperand();
        AbstractExpr rvalue = getRightOperand();
        //Il y a 2 cas: un Identifier ou une selection
        if (lvalue instanceof Identifier) {
            ass = 1;
            rvalue.codeGenInst(compiler);
            ass = 0;
            codeGenSaveLastValue(compiler, ((Identifier) lvalue).getExpDefinition().getOperand());
        } else if (lvalue instanceof Selection) {
            rvalue.codeGenInst(compiler);
            GPRegister reg = getLastUsedRegisterToStore();
            lvalue.codeGenInst(compiler);

            compiler.addInstruction(new STORE(reg, new RegisterOffset(0, getLastUsedRegisterToStore())));
            freeRegister(getLastUsedRegisterToStore().getNumber());
            freeRegister(reg.getNumber());
        }

    }
}
