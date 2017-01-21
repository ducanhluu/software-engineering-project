package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.IMAProgram;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

/**
 * @author gl17
 * @date 01/01/2017
 */
public class Main extends AbstractMain {
    private static final Logger LOG = Logger.getLogger(Main.class);
    
    private ListDeclVar declVariables;
    private ListInst insts;
    public Main(ListDeclVar declVariables,
            ListInst insts) {
        Validate.notNull(declVariables);
        Validate.notNull(insts);
        this.declVariables = declVariables;
        this.insts = insts;
    }

    @Override
    protected void verifyMain(DecacCompiler compiler) throws ContextualError {
        EnvironmentExp envExp = new EnvironmentExp(null);
        this.declVariables.verifyListDeclVariable(compiler, envExp, null);
        this.insts.verifyListInst(compiler, envExp, null, null);
     
    }

    @Override
    protected void codeGenMain(IMAProgram compiler) {
        // A FAIRE: traiter les déclarations de variables.
        compiler.addComment("Variables declarations:");
        declVariables.codeGenListDeclVar(compiler);
        compiler.addComment("Beginning of main instructions:");
        insts.codeGenListInst(compiler);
    }
    
    @Override
    public void decompile(IndentPrintStream s) {
        s.print("{");
        s.println();
        s.indent();
        declVariables.decompile(s);
        s.println();
        insts.decompile(s);
        s.unindent();
        s.println();
        s.print("}");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        declVariables.iter(f);
        insts.iter(f);
    }
 
    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        declVariables.prettyPrint(s, prefix, false);
        insts.prettyPrint(s, prefix, true);
    }
}
