package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import static fr.ensimag.deca.codegen.MemoryManagement.getLabel;
import static fr.ensimag.deca.codegen.MemoryManagement.setLabel;
import fr.ensimag.ima.pseudocode.Label;


/**
 *
 * @author gl17
 * @date 01/01/2017
 */
public class And extends AbstractOpBool {

    public And(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected String getOperatorName() {
        return "&&";
    }


    protected static Label labelAnd;
    protected static Label labelTmp;
    private static int nbLabel = 0;
    
    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        labelTmp=getLabel();
        setLabelAnd();
        setLabel(getlabelAnd());
        getLeftOperand().codeGenInst(compiler);
        compiler.addLabel(labelAnd);
        setLabel(labelTmp);
        getRightOperand().codeGenInst(compiler);
        
    }

    private void setLabelAnd() {
        nbLabel++;
        labelAnd = new Label("E_And." + nbLabel);
    }

    private Label getlabelAnd() {
        return labelAnd;
    }
    
    
    

}
