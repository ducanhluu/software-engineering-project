package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import static fr.ensimag.deca.codegen.MemoryManagement.increSizeOfVtables;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.NullOperand;
import static fr.ensimag.ima.pseudocode.Register.GB;
import static fr.ensimag.ima.pseudocode.Register.getR;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.STORE;
import org.apache.log4j.Logger;

/**
 *
 * @author gl17
 * @date 01/01/2017
 */
public class ListDeclClass extends TreeList<AbstractDeclClass> {

    private static final Logger LOG = Logger.getLogger(ListDeclClass.class);

    @Override
    public void decompile(IndentPrintStream s) {
        for (AbstractDeclClass c : getList()) {
            c.decompile(s);
            s.println();
        }
    }

    /**
     * Pass 1 of [SyntaxeContextuelle]
     */
    void verifyListClass(DecacCompiler compiler) throws ContextualError {
        LOG.debug("verify listClass: start");
        throw new UnsupportedOperationException("not yet implemented");
        // LOG.debug("verify listClass: end");
    }

    /**
     * Pass 2 of [SyntaxeContextuelle]
     */
    public void verifyListClassMembers(DecacCompiler compiler) throws ContextualError {
        throw new UnsupportedOperationException("not yet implemented");
    }

    /**
     * Pass 3 of [SyntaxeContextuelle]
     */
    public void verifyListClassBody(DecacCompiler compiler) throws ContextualError {
        throw new UnsupportedOperationException("not yet implemented");
    }

    /**
     * Pass 1 of [Gencode] - build table of labels of methods
     */
    public void buildTableOfLabels() {
        for (AbstractDeclClass i : getList()) {
            i.buildTableOfLabels();
        }
    }

    /**
     * Pass 1 of [Gencode] - generation of code to build table of methods
     */
    public void codeGenBuildVTable(DecacCompiler compiler) {
        buildTableOfLabels();
        compiler.addComment("Code des tables des methodes");
        codeGenBuildVTableObject(compiler);
        for (AbstractDeclClass i : getList()) {
            i.codeGenBuildVTable(compiler);
        }
        compiler.addComment("-----------------------------------------------");
    }

    private void codeGenBuildVTableObject(DecacCompiler compiler) {
        compiler.addInstruction(new LOAD(new NullOperand(), getR(0)));
        compiler.addInstruction(new STORE(getR(0), new RegisterOffset(1, GB)));
        compiler.addInstruction(new LOAD("code.Object.equals", getR(0)));
        compiler.addInstruction(new STORE(getR(0), new RegisterOffset(2, GB)));
        increSizeOfVtables(2);
    }
}
