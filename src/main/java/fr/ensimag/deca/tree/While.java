package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import static fr.ensimag.deca.codegen.MemoryManagement.getLastUsedRegisterToStore;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.BNE;
import fr.ensimag.ima.pseudocode.instructions.BRA;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;

/**
 *
 * @author gl17
 * @date 01/01/2017
 */
public class While extends AbstractInst {

    private AbstractExpr condition;
    private ListInst body;

    public AbstractExpr getCondition() {
        return condition;
    }

    public ListInst getBody() {
        return body;
    }

    public While(AbstractExpr condition, ListInst body) {
        Validate.notNull(condition);
        Validate.notNull(body);
        this.condition = condition;
        this.body = body;
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        if (condition instanceof BooleanLiteral) {
            if (((BooleanLiteral) condition).getValue()) {
                setLabelWhile();
                compiler.addLabel(getLabelDebut());
                body.codeGenListInst(compiler);
                compiler.addInstruction(new BRA(getLabelDebut()));
            }
        } else {
            setLabelWhile();
            compiler.addInstruction(new BRA(getLabelCond()));
            compiler.addLabel(getLabelDebut());
            body.codeGenListInst(compiler);
            compiler.addLabel(getLabelCond());
            condition.codeGenInst(compiler);
        }
    }

    @Override
    protected void verifyInst(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass, Type returnType)
            throws ContextualError {
        this.condition.verifyCondition(compiler, localEnv, currentClass);
        this.body.verifyListInst(compiler, localEnv, currentClass, returnType);
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("while (");
        getCondition().decompile(s);
        s.println(") {");
        s.indent();
        getBody().decompile(s);
        s.unindent();
        s.print("}");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        condition.iter(f);
        body.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        condition.prettyPrint(s, prefix, false);
        body.prettyPrint(s, prefix, true);
    }

    private static int nbLabel = 0;
    protected static Label labelCond;
    protected static Label labelDebut;

    private void setLabelWhile() {
        nbLabel++;
        labelCond = new Label("E_Cond." + nbLabel);
        labelDebut = new Label ("E_Debut." + nbLabel);
    }

    public static Label getLabelCond() {
        return labelCond;
    }
    
    public static Label getLabelDebut() {
        return labelDebut;
    }

}
