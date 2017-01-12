package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import static fr.ensimag.deca.codegen.MemoryManagement.getAvailableRegister;
import static fr.ensimag.deca.codegen.MemoryManagement.getLastUsedRegisterToStore;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.TypeDefinition;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.GPRegister;
import static fr.ensimag.ima.pseudocode.Register.getR;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.POP;

/**
 * Arithmetic binary operations (+, -, /, ...)
 *
 * @author gl17
 * @date 01/01/2017
 */
public abstract class AbstractOpArith extends AbstractBinaryExpr {

    public AbstractOpArith(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        Type type1=this.getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
        Type type2=this.getRightOperand().verifyExpr(compiler, localEnv, currentClass);
        this.getLeftOperand().setType(type1);
        this.getRightOperand().setType(type2);
        if((!type1.isFloat() && !type1.isInt()) || (!type2.isFloat() && !type2.isInt())  ){
            
            throw new ContextualError("operation impossible",this.getLocation());
        }
        //plusieurs a lever selon les cas possibles 
        if(type1.isFloat() || type2.isFloat()){
            TypeDefinition typeDef=compiler.getEnvType().get(compiler.getEnvType().getDict().create("float"));
            return typeDef.getType();
        }
        TypeDefinition typeDef=compiler.getEnvType().get(compiler.getEnvType().getDict().create("int"));
        return typeDef.getType();
    }

    protected GPRegister reg;
    protected DVal val;

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        
        if (getLeftOperand() instanceof Identifier && getRightOperand() instanceof Identifier ) {
            val = ((Identifier) getLeftOperand()).getVariableDefinition().getOperand();
            getRightOperand().codeGenInst(compiler);
            reg = getLastUsedRegisterToStore();
            
        } else if (getRightOperand() instanceof Identifier) {
            val = ((Identifier) getRightOperand()).getVariableDefinition().getOperand();
            getLeftOperand().codeGenInst(compiler);
            reg = getLastUsedRegisterToStore();
            
        } else if (getLeftOperand() instanceof Identifier) {
            val = ((Identifier) getLeftOperand()).getVariableDefinition().getOperand();
            compiler.addInstruction(
                    new LOAD(((Identifier) getRightOperand()).getVariableDefinition().getOperand(),
                            getAvailableRegister(compiler)));
            reg = getLastUsedRegisterToStore();
        }
        else {
            getLeftOperand().codeGenInst(compiler);
            val = getLastUsedRegisterToStore();
            getRightOperand().codeGenInst(compiler);
            reg = getLastUsedRegisterToStore();
        }
        
        if (val instanceof GPRegister && reg == val) {
            compiler.addInstruction(new POP(getR(0)));
            val = getR(0);
        }
    }

}
