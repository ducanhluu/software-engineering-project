/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import static fr.ensimag.deca.codegen.MemoryManagement.getLastUsedRegisterToStore;
import static fr.ensimag.deca.codegen.MemoryManagement.returnNeeded;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.FieldDefinition;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.IMAProgram;
import static fr.ensimag.ima.pseudocode.Register.getR;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;

/**
 *
 * @author chakirs
 */
public class Return extends AbstractInst {

    private AbstractExpr operand;

    public AbstractExpr getOperand() {
        return this.operand;
    }

    public Return(AbstractExpr operand) {
        Validate.notNull(operand);
        this.operand = operand;
    }

    @Override
    protected void verifyInst(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass, Type returnType) throws ContextualError {

        AbstractExpr op = this.operand.verifyRValue(compiler, localEnv, currentClass, returnType);
        if (op instanceof Identifier) {
            Identifier ident = (Identifier) op;
            if (ident.getDefinition() instanceof FieldDefinition) {
                AbstractExpr thisInstruction = new Selection(new This(), ident);
                this.operand = thisInstruction;
            }

        }
    }

    @Override
    protected void codeGenInst(IMAProgram compiler) {
        operand.codeGenInst(compiler);
        compiler.addInstruction(new LOAD(getLastUsedRegisterToStore(), getR(0)));
        returnNeeded = true;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("return ");
        this.operand.decompile(s);
        s.print(";");
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        operand.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        this.operand.iterChildren(f);
    }

}
