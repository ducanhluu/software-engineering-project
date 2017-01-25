package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import static fr.ensimag.deca.codegen.MemoryManagement.divisionIsUsed;
import static fr.ensimag.deca.codegen.MemoryManagement.getAvailableRegister;
import static fr.ensimag.deca.codegen.MemoryManagement.getLastUsedRegisterToStore;
import static fr.ensimag.deca.codegen.MemoryManagement.setLastUsedRegister;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.deca.context.TypeDefinition;
import fr.ensimag.ima.pseudocode.IMAProgram;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.BEQ;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.REM;

/**
 *
 * @author gl17
 * @date 01/01/2017
 */
public class Modulo extends AbstractOpArith {

    public Modulo(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        Type type1 = this.getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
        Type type2 = this.getRightOperand().verifyExpr(compiler, localEnv, currentClass);
        if (!type1.isInt() || !type2.isInt()) {
            throw new ContextualError("you can apply % only on integers", this.getLocation());
        }
        this.getLeftOperand().setType(type1);
        this.getRightOperand().setType(type2);
        TypeDefinition typeDef = compiler.getEnvType().get(compiler.getEnvType().getDict().create("int"));
        this.setType(typeDef.getType());
        return typeDef.getType();
    }

    @Override
    protected String getOperatorName() {
        return "%";
    }

    @Override
    protected void codeGenInst(IMAProgram compiler) {
        super.codeGenInst(compiler);
        divisionIsUsed = true;

        if (getRightOperand() instanceof Identifier && !(getLeftOperand() instanceof Identifier)) {
            compiler.addInstruction(new CMP(0, reg));
            compiler.addInstruction(new BEQ(new Label("division_by_zero_error")));
            compiler.addInstruction(new LOAD(((Identifier) getRightOperand()).getVariableDefinition().getOperand(), getAvailableRegister(compiler)));
            GPRegister reg2 = getLastUsedRegisterToStore();
            compiler.addInstruction(new REM(reg, reg2));
            setLastUsedRegister(reg2.getNumber());
        } else {
            compiler.addInstruction(new LOAD(val, getAvailableRegister(compiler)));
            compiler.addInstruction(new CMP(0, getLastUsedRegisterToStore()));
            compiler.addInstruction(new BEQ(new Label("division_by_zero_error")));
            compiler.addInstruction(new REM(val, reg));
            setLastUsedRegister(reg.getNumber());
        }
    }

}
