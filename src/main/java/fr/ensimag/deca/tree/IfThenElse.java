package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import static fr.ensimag.deca.codegen.MemoryManagement.setLabel;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Label;
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
    protected void codeGenInst(DecacCompiler compiler) {
        if (condition instanceof BooleanLiteral) {
            if (((BooleanLiteral) condition).getValue()) {
                setLabelInit();
                setLabelSinon();
                thenBranch.codeGenListInst(compiler);
                if (!elseBranch.isEmpty()) {
                    compiler.addInstruction(new BRA(getLabelFin()));
                    compiler.addLabel(getLabelSinon());
                    elseBranch.codeGenListInst(compiler);
                } 
                if (elseBranch.size() <= 1 ){
                    labelReset();
                    compiler.addLabel(getLabelFin());
                }
            }
        } else {
            setLabelInit();
            setLabelSinon();
            setLabel(getLabelSinon());
            condition.codeGenInst(compiler);
            thenBranch.codeGenListInst(compiler);
            if (!elseBranch.isEmpty()) {
                compiler.addInstruction(new BRA(getLabelFin()));
                compiler.addLabel(getLabelSinon());
                elseBranch.codeGenListInst(compiler);
            }
            if (elseBranch.size() <= 1) {
                labelReset();
                compiler.addLabel(getLabelFin());
            }
        }
    }

    @Override
    public void decompile(IndentPrintStream s) {
      /*  s.print("if(");
        condition.decompile(s);
        s.println(" ){");
        thenBranch.decompile(s);
        s.print("}");
        if (elseBranch instanceof IfThenElse){
        s.print("else ");
        elseBranch.decompile(s);
        s.print("}");*/
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

    private static int nbLabel = 0;
    private static int nbCond = 0;
    protected static Label labelSinon;
    protected static Label labelFin;

    private void setLabelInit() {
        labelFin = new Label("E_Fin." + nbLabel);
    }

    private void setLabelSinon() {
        nbCond++;
        labelSinon = new Label("E_Sinon." + nbLabel + nbCond);
    }

    private void labelReset() {
        nbLabel++;
        nbCond = 0;
    }

    public static Label getLabelSinon() {
        return labelSinon;
    }

    public static Label getLabelFin() {
        return labelFin;
    }

}
