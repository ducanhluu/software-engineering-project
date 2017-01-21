package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import static fr.ensimag.deca.codegen.CodeGenInst.addTestOverall;
import static fr.ensimag.deca.codegen.MemoryManagement.freeRegisters;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.IMAProgram;
import fr.ensimag.ima.pseudocode.instructions.*;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

/**
 * Deca complete program (class definition plus main block)
 *
 * @author gl17
 * @date 01/01/2017
 */
public class Program extends AbstractProgram {

    private static final Logger LOG = Logger.getLogger(Program.class);

    public Program(ListDeclClass classes, AbstractMain main) {
        Validate.notNull(classes);
        Validate.notNull(main);
        this.classes = classes;
        this.main = main;
    }

    public ListDeclClass getClasses() {
        return classes;
    }

    public AbstractMain getMain() {
        return main;
    }
    private ListDeclClass classes;
    private AbstractMain main;

    @Override
    public void verifyProgram(DecacCompiler compiler) throws ContextualError {

        this.classes.verifyListClass(compiler);
        this.classes.verifyListClassMembers(compiler);
        this.classes.verifyListClassBody(compiler);
        this.main.verifyMain(compiler);

    }

    @Override
    public void codeGenProgram(DecacCompiler compiler) {
        // A FAIRE: compléter ce squelette très rudimentaire de code
        IMAProgram subProg = new IMAProgram();
        classes.codeGenBuildVTable(subProg);
        subProg.addComment("Main program");
        main.codeGenMain(subProg);
        subProg.addInstruction(new HALT());
        subProg.addComment("end main program");
        freeRegisters();
        classes.codeGenMethods(subProg);
        addTestOverall(subProg);
        compiler.append(subProg);
    }

    @Override
    public void decompile(IndentPrintStream s) {
        getClasses().decompile(s);
        getMain().decompile(s);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        classes.iter(f);
        main.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        classes.prettyPrint(s, prefix, false);
        main.prettyPrint(s, prefix, true);
    }
}
