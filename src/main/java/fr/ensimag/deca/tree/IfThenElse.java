package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import static fr.ensimag.deca.codegen.CodeGenInst.getLabel;
import static fr.ensimag.deca.codegen.CodeGenInst.getLabelFin;
import static fr.ensimag.deca.codegen.CodeGenInst.setLabel;
import static fr.ensimag.deca.codegen.CodeGenInst.setLabelFin;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.IMAProgram;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.BEQ;
import fr.ensimag.ima.pseudocode.instructions.BRA;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;

/**
 * Full if/else if/else statement.
 *
 * @author gl17
 * @date 01/01/2017
 */
public class IfThenElse extends AbstractInst {

    private final AbstractExpr condition;
    private final ListInst thenBranch;
    private ListInst elseBranch;
    protected static int Opp = 0;

    public IfThenElse(AbstractExpr condition, ListInst thenBranch, ListInst elseBranch) {
        Validate.notNull(condition);
        Validate.notNull(thenBranch);
        Validate.notNull(elseBranch);
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }

    public AbstractExpr getCondition() {
        return condition;
    }

    public ListInst getThenBranch() {
        return thenBranch;
    }

    public ListInst getElseBranch() {
        return elseBranch;
    }

    @Override
    protected void verifyInst(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass, Type returnType)
            throws ContextualError {
        this.condition.verifyCondition(compiler, localEnv, currentClass);
        this.thenBranch.verifyListInst(compiler, localEnv, currentClass, returnType);
        this.elseBranch.verifyListInst(compiler, localEnv, currentClass, returnType);
    }

    @Override
    protected void codeGenInst(IMAProgram compiler) {
        setLabelInit();
        setLabelFin(getLabelFinIf());
        setLabel(getLabelSinon());
        if (condition instanceof AbstractBinaryExpr) {
            Opp = 1;
            condition.codeGenInst(compiler);
            Opp = 0;
        } else {
            condition.codeGenInst(compiler);
            compiler.addInstruction(new BEQ(getLabel()));
        }
        thenBranch.codeGenListInst(compiler);
        compiler.addInstruction(new BRA(labelFinIf));
        compiler.addLabel(getLabelSinon());
        elseBranch.codeGenListInst(compiler);
        compiler.addLabel(labelFinIf);
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("if(");
        condition.decompile(s);
        s.println(" ){");
        thenBranch.decompile(s);
        s.print("}");
        if (elseBranch.size() > 0) {
            if (elseBranch.getList().get(0) instanceof IfThenElse) {
                s.print("else ");
                s.println();
                elseBranch.decompile(s);
            } else {
                s.print("else {");
                s.println();
                elseBranch.decompile(s);
                s.print("}");
            }

        }
    }

    @Override
    protected
            void iterChildren(TreeFunction f) {
        condition.iter(f);
        thenBranch.iter(f);
        elseBranch.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        condition.prettyPrint(s, prefix, false);
        thenBranch.prettyPrint(s, prefix, false);
        elseBranch.prettyPrint(s, prefix, true);
    }

    protected static int nbLabel = 0;
    protected Label labelSinon;
    private Label labelFinIf;

    private void setLabelInit() {
        nbLabel++;
        labelFinIf = new Label("E_Fin." + nbLabel);
        labelSinon = new Label("E_Sinon." + nbLabel);
    }


    public Label getLabelSinon() {
        return labelSinon;
    }

    public Label getLabelFinIf() {
        return labelFinIf;
    }

}
