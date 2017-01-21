package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.TypeDefinition;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import static fr.ensimag.deca.codegen.MemoryManagement.setLastUsedRegister;
import fr.ensimag.ima.pseudocode.IMAProgram;

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
        
        if(type1.isInt() && type2.isFloat()){
            ConvFloat cf=new ConvFloat(this.getLeftOperand());
            this.setLeftOperand(cf);

        }else if (type2.isInt() && type1.isFloat()){
            ConvFloat cf=new ConvFloat(this.getLeftOperand());
            this.setRightOperand(cf);
                    /*throw new ContextualError("cannot use operator eqquals on two different type",this.getLocation());*/
        }else if(type1.isClassOrNull() && type2.isClassOrNull() ) {
            if(this.getOperatorName() != "==" && this.getOperatorName() != "!=" ){
                throw new ContextualError("cannot use operator "+this.getOperatorName()+" on this type",this.getLocation());
            }
  
        }else if(((!type1.isInt()) && (!type1.isFloat())) || ((!type2.isInt()) && (!type2.isFloat()))) {
            throw new ContextualError("cannot use operator"+this.getOperatorName()+" on this type",this.getLocation());
        }
        
        //plusieurs a lever selon les cas possibles 
        TypeDefinition typeDef=compiler.getEnvType().get(compiler.getEnvType().getDict().create("boolean"));
        this.setType(typeDef.getType());
        return typeDef.getType();
    }

    @Override
    protected void codeGenInst(IMAProgram compiler) {
        super.codeGenInst(compiler);
        compiler.addInstruction(new CMP(val,reg));
        setLastUsedRegister(reg.getNumber());
    }

}
