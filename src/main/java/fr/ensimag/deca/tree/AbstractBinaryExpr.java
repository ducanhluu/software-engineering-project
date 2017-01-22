package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import static fr.ensimag.deca.codegen.MemoryManagement.getLastUsedRegisterToStore;
import fr.ensimag.deca.context.Definition;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.IMAProgram;
import static fr.ensimag.ima.pseudocode.Register.LB;
import static fr.ensimag.ima.pseudocode.Register.getR;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.POP;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;

/**
 * Binary expressions.
 *
 * @author gl17
 * @date 01/01/2017
 */
public abstract class AbstractBinaryExpr extends AbstractExpr {

    public AbstractExpr getLeftOperand() {
        return leftOperand;
    }

    public AbstractExpr getRightOperand() {
        return rightOperand;
    }
    
    protected void setLeftOperand(AbstractExpr leftOperand) {
        Validate.notNull(leftOperand);
        this.leftOperand = leftOperand;
    }

    protected void setRightOperand(AbstractExpr rightOperand) {
        Validate.notNull(rightOperand);
        this.rightOperand = rightOperand;
    }

    private AbstractExpr leftOperand;
    private AbstractExpr rightOperand;

    public AbstractBinaryExpr(AbstractExpr leftOperand,
            AbstractExpr rightOperand) {
        Validate.notNull(leftOperand, "left operand cannot be null");
        Validate.notNull(rightOperand, "right operand cannot be null");
        Validate.isTrue(leftOperand != rightOperand, "Sharing subtrees is forbidden");
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }


    @Override
    public void decompile(IndentPrintStream s) {
        s.print("(");
        getLeftOperand().decompile(s);
        s.print(" " + getOperatorName() + " ");
        getRightOperand().decompile(s);
        s.print(")");
    }

    abstract protected String getOperatorName();
    
    @Override
    protected void iterChildren(TreeFunction f) {
        leftOperand.iter(f);
        rightOperand.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        leftOperand.prettyPrint(s, prefix, false);
        rightOperand.prettyPrint(s, prefix, true);
    }
    
    protected GPRegister reg;
    protected DVal val;
    
    @Override
    protected void codeGenInst(IMAProgram compiler) {
        AbstractExpr rvalue = getRightOperand();
        AbstractExpr lvalue = getLeftOperand();
        
        if (lvalue instanceof Identifier && rvalue instanceof Identifier) {
            Definition def = ((Identifier) rvalue).getDefinition();
            if (def.isParam()) {
                int index = -2 - ((Identifier) rvalue).getParamDefinition().getIndex();
                val = new RegisterOffset(index, LB);
            } else if (def.isField()) {
                int index = ((Identifier) rvalue).getFieldDefinition().getIndex();
                val = new RegisterOffset(index, getLastUsedRegisterToStore());
            } else {
                val = ((Identifier) rvalue).getExpDefinition().getOperand();
            }
            lvalue.codeGenInst(compiler);
            reg = getLastUsedRegisterToStore();

        } else if (rvalue instanceof Identifier) {
            Definition def = ((Identifier) rvalue).getDefinition();
            if (def.isParam()) {
                int index = -2 - ((Identifier) rvalue).getParamDefinition().getIndex();
                val = new RegisterOffset(index, LB);
            } else if (def.isField()) {
                int index = ((Identifier) rvalue).getFieldDefinition().getIndex();
                val = new RegisterOffset(index, getLastUsedRegisterToStore());
            } else {
                val = ((Identifier) rvalue).getExpDefinition().getOperand();
            }
            lvalue.codeGenInst(compiler);
            reg = getLastUsedRegisterToStore();
        } else {
            lvalue.codeGenInst(compiler);
            reg = getLastUsedRegisterToStore();
            rvalue.codeGenInst(compiler);
            val = getLastUsedRegisterToStore();
        }

        if (val instanceof GPRegister && reg == val) {
            compiler.addInstruction(new LOAD(reg, getR(0)));
            compiler.addInstruction(new POP((GPRegister) reg), "restauration");
            val = getR(0);
        }
    }
}
