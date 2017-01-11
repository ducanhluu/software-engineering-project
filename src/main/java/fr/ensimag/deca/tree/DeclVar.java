package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import static fr.ensimag.deca.codegen.MemoryManagement.getNumberGlobalVariables;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.TypeDefinition;
import fr.ensimag.deca.context.VariableDefinition;
import fr.ensimag.deca.tools.IndentPrintStream;
import static fr.ensimag.ima.pseudocode.Register.GB;
import static fr.ensimag.ima.pseudocode.Register.getR;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.STORE;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.Validate;

/**
 * @author gl17
 * @date 01/01/2017
 */
public class DeclVar extends AbstractDeclVar {

    
    final private AbstractIdentifier type;
    final private AbstractIdentifier varName;
    final private AbstractInitialization initialization;

    public DeclVar(AbstractIdentifier type, AbstractIdentifier varName, AbstractInitialization initialization) {
        Validate.notNull(type);
        Validate.notNull(varName);
        Validate.notNull(initialization);
        this.type = type;
        this.varName = varName;
        this.initialization = initialization;
    }

    @Override
    protected void verifyDeclVar(DecacCompiler compiler,
            EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
            Type type = this.type.verifyType(compiler);
            this.varName.verifyExpr(compiler, localEnv, currentClass);
            this.type.setType(type);
            assert(type != null);
            if (this.type.getType().isVoid()){
                throw new ContextualError("type shouldn't be void in declaration",this.getLocation());
            }
            this.initialization.verifyInitialization(compiler, type, localEnv, currentClass);
            VariableDefinition vardef = new VariableDefinition(type,this.varName.getLocation());
            this.varName.setDefinition(vardef);
            EnvironmentExp currentEnv=new EnvironmentExp(localEnv);
            
            try {
                currentEnv.declare(this.varName.getName(), vardef);
            } catch (EnvironmentExp.DoubleDefException ex) {
                Logger.getLogger(DeclVar.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    
    @Override
    public void decompile(IndentPrintStream s) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    protected
    void iterChildren(TreeFunction f) {
        type.iter(f);
        varName.iter(f);
        initialization.iter(f);
    }
    
    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        type.prettyPrint(s, prefix, false);
        varName.prettyPrint(s, prefix, false);
        initialization.prettyPrint(s, prefix, true);
    }

    @Override
    protected void codeGenDeclVar(DecacCompiler compiler) {    
        varName.getVariableDefinition().setOperand(new RegisterOffset(getNumberGlobalVariables(), GB));
        if (initialization instanceof Initialization) {
            ((Initialization) initialization).getExpression().codeGenInst(compiler);
            compiler.addInstruction(new STORE(getR(2), varName.getVariableDefinition().getOperand()));
        }
    }
}
