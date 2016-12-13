package fr.ensimag.deca.tree;


/**
 *
 * @author gl17
 * @date 01/01/2017
 */
public class Equals extends AbstractOpExactCmp {

    public Equals(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return "==";
    }    
    
}
