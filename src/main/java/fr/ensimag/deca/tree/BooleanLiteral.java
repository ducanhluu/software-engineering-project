package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import static fr.ensimag.deca.codegen.MemoryManagement.getAvailableRegister;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.TypeDefinition;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.IMAProgram;
import static fr.ensimag.ima.pseudocode.Register.getR;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import java.io.PrintStream;

/**
 *
 * @author gl17
 * @date 01/01/2017
 */
public class BooleanLiteral extends AbstractExpr {

    private boolean value;

    public BooleanLiteral(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        TypeDefinition typeDef = compiler.getEnvType().get(compiler.getEnvType().getDict().create("boolean"));
        return typeDef.getType();
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print(Boolean.toString(value));
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // leaf node => nothing to do
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // leaf node => nothing to do
    }

    @Override
    String prettyPrintNode() {
        return "BooleanLiteral (" + value + ")";
    }

    @Override
    protected void codeGenInst(IMAProgram compiler) {
        if (value) {
            compiler.addInstruction(new LOAD(1, getAvailableRegister(compiler)));
        } else {
            compiler.addInstruction(new LOAD(0, getAvailableRegister(compiler)));
        }
    }

    @Override
    protected void codeGenInstObject(IMAProgram subProg) {
        if (value) {
            subProg.addInstruction(new LOAD(1, getR(0)));
        } else {
            subProg.addInstruction(new LOAD(0, getR(0)));
        }
    }

}
