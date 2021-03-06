package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import static fr.ensimag.deca.codegen.MemoryManagement.getNumberGlobalVariables;
import static fr.ensimag.deca.codegen.MemoryManagement.getNumberLocalVariables;
import static fr.ensimag.deca.codegen.MemoryManagement.getSizeOfVTables;
import static fr.ensimag.deca.codegen.MemoryManagement.increNumberGlobalVariables;
import static fr.ensimag.deca.codegen.MemoryManagement.increNumberLocalVariables;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.VariableDefinition;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.IMAProgram;
import static fr.ensimag.ima.pseudocode.Register.GB;
import static fr.ensimag.ima.pseudocode.Register.LB;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;

/**
 * @author gl17
 * @date 01/01/2017
 */
public class DeclVar extends AbstractDeclVar {

    protected static int dec = 0;
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
            this.type.setType(type);
            assert(type != null);
            if (this.type.getType().isVoid()){
                throw new ContextualError("type shouldn't be void in declaration",this.getLocation());
            }
            this.initialization.verifyInitialization(compiler, type, localEnv, currentClass);
            VariableDefinition vardef = new VariableDefinition(type,this.varName.getLocation());
            this.varName.setDefinition(vardef);
            
            try {
                localEnv.declare(this.varName.getName(), vardef);
            } catch (EnvironmentExp.DoubleDefException ex) {
                throw new ContextualError("this variable is already declared",this.varName.getLocation());
                //Logger.getLogger(DeclVar.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.varName.verifyExpr(compiler, localEnv, currentClass);
           
    }

    
    @Override
    public void decompile(IndentPrintStream s) {
        this.type.decompile(s);
        s.print(" ");
        this.varName.decompile(s);
        s.print(" ");
        this.initialization.decompile(s);
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
    protected void codeGenDeclVar(IMAProgram compiler) {
        increNumberGlobalVariables();
        varName.getVariableDefinition().setOperand(new RegisterOffset(getSizeOfVTables() + getNumberGlobalVariables(), GB));
        dec=1;
        initialization.codeGenInit(compiler, varName.getVariableDefinition().getOperand());
        dec=0;
    }

    @Override
    protected void codeGenDeclVarLocal(IMAProgram compiler) {
        increNumberLocalVariables();
        varName.getVariableDefinition().setOperand(new RegisterOffset(getNumberLocalVariables(), LB));
        dec=1;
        initialization.codeGenInit(compiler, varName.getVariableDefinition().getOperand());
        dec=0;
    }
}
