package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.TypeDefinition;

/**
 *
 * @author gl17
 * @date 01/01/2017
 */
public abstract class AbstractOpBool extends AbstractBinaryExpr {

    public AbstractOpBool(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        Type type1=this.getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
        Type type2=this.getRightOperand().verifyExpr(compiler, localEnv, currentClass);
        if ( !type1.isBoolean() || !type2.isBoolean() ){
            throw new ContextualError("this operation can be done only on boolean types",this.getLocation());
        }
        this.getLeftOperand().setType(type1);
        this.getRightOperand().setType(type2);
        TypeDefinition typeDef=compiler.getEnvType().get(compiler.getEnvType().getDict().create("boolean"));
        this.setType(typeDef.getType());
        return typeDef.getType();
    }

}
