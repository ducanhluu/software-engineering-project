package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import static fr.ensimag.deca.codegen.MemoryManagement.getAvailableRegister;
import static fr.ensimag.deca.codegen.MemoryManagement.getLastUsedRegisterToStore;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.OPP;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;

/**
 * Unary expression.
 *
 * @author gl17
 * @date 01/01/2017
 */
public abstract class AbstractUnaryExpr extends AbstractExpr {

    public AbstractExpr getOperand() {
        return operand;
    }
    private AbstractExpr operand;

    public AbstractUnaryExpr(AbstractExpr operand) {
        Validate.notNull(operand);
        this.operand = operand;
    }

    protected abstract String getOperatorName();

    @Override
    public void decompile(IndentPrintStream s) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        operand.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        operand.prettyPrint(s, prefix, true);
    }

    protected GPRegister reg;
    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        AbstractExpr expr = getOperand();
        if (expr instanceof Identifier) {
            compiler.addInstruction(new LOAD(((Identifier) expr).getVariableDefinition().getOperand(),
                    getAvailableRegister(compiler)));
        } else {
            expr.codeGenInst(compiler);
        }
        reg = getLastUsedRegisterToStore();
    }
}
