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
public abstract class AbstractOpCmp extends AbstractBinaryExpr {

    public AbstractOpCmp(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        Type type1=this.getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
        Type type2=this.getRightOperand().verifyExpr(compiler, localEnv, currentClass);
        this.getLeftOperand().setType(type1);
        this.getRightOperand().setType(type2);
        //plusieurs a lever selon les cas possibles 
        TypeDefinition typeDef=compiler.getEnvType().get(compiler.getEnvType().getDict().create("boolean"));
        return typeDef.getType();
    }


}
