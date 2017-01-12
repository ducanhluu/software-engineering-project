package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.TypeDefinition;

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
}
