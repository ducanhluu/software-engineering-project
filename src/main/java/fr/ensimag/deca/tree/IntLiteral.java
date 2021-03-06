package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import static fr.ensimag.deca.codegen.CodeGenInst.codeGenPrintInteger;
import static fr.ensimag.deca.codegen.MemoryManagement.getAvailableRegister;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import java.io.PrintStream;
import fr.ensimag.deca.context.TypeDefinition;
import fr.ensimag.ima.pseudocode.IMAProgram;
import static fr.ensimag.ima.pseudocode.Register.getR;

/**
 * Integer literal
 *
 * @author gl17
 * @date 01/01/2017
 */
public class IntLiteral extends AbstractExpr {
    public int getValue() {
        return value;
    }

    private int value;

    public IntLiteral(int value) {
        this.value = value;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        TypeDefinition typeDef=compiler.getEnvType().get(compiler.getEnvType().getDict().create("int"));
        this.setType(typeDef.getType());
        return typeDef.getType();
    }


    @Override
    String prettyPrintNode() {
        return "Int (" + getValue() + ")";
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print(Integer.toString(value));
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
    protected void codeGenPrint(IMAProgram compiler) {
        codeGenPrintInteger(compiler, value);
   }
    
    @Override
    protected void codeGenInst(IMAProgram compiler) {
        compiler.addInstruction(new LOAD(value, getAvailableRegister(compiler)));
    }

    @Override
    protected void codeGenInstObject(IMAProgram subProg) {
        subProg.addInstruction(new LOAD(value, getR(0)));
    }
}
