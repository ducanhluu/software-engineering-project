package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import static fr.ensimag.deca.codegen.MemoryManagement.getAvailableRegister;
import static fr.ensimag.deca.codegen.MemoryManagement.getLastUsedRegisterToStore;
import fr.ensimag.deca.context.Definition;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.IMAProgram;
import static fr.ensimag.ima.pseudocode.Register.LB;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
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
        s.print("(");
        s.print(this.getOperatorName());
        this.operand.decompile(s);
        s.print(")");
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
    protected DVal dval;

    @Override
    protected void codeGenInst(IMAProgram compiler) {
        AbstractExpr oper = getOperand();

        if (oper instanceof Identifier) {
            Definition def = ((Identifier) oper).getDefinition();
            if (def.isParam()) {
                int index = -2 - ((Identifier) oper).getParamDefinition().getIndex();
                dval = new RegisterOffset(index, LB);
            } else if (def.isField()) {
                int index = ((Identifier) oper).getFieldDefinition().getIndex();
                dval = new RegisterOffset(index, getLastUsedRegisterToStore());
            } else {
                dval = ((Identifier) oper).getExpDefinition().getOperand();
            }
            reg = getAvailableRegister(compiler);
        } else {
            oper.codeGenInst(compiler);
            reg = getLastUsedRegisterToStore();
            dval = null;
        }
    }
}
