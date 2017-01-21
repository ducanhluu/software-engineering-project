package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.IMAProgram;
import java.io.PrintStream;

/**
 * Empty main Deca program
 *
 * @author gl17
 * @date 01/01/2017
 */
public class EmptyMain extends AbstractMain {
    @Override
    protected void verifyMain(DecacCompiler compiler) throws ContextualError {
        
    }

    @Override
    protected void codeGenMain(IMAProgram compiler) {
        compiler.addComment("Empty Main");
    }

    /**
     * Contains no real information => nothing to check.
     */
    @Override
    protected void checkLocation() {
        // nothing
    }
    
    @Override
    public void decompile(IndentPrintStream s) {
        // no main program => nothing
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // leaf node => nothing to do
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // leaf node => nothing to do
    }
}
