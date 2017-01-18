package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.util.Iterator;
import org.apache.log4j.Logger;

/**
 *
 * @author gl17
 * @date 01/01/2017
 */
public class ListDeclClass extends TreeList<AbstractDeclClass> {
    private static final Logger LOG = Logger.getLogger(ListDeclClass.class);
    
    @Override
    public void decompile(IndentPrintStream s) {
        for (AbstractDeclClass c : getList()) {
            c.decompile(s);
            s.println();
        }
    }

    /**
     * Pass 1 of [SyntaxeContextuelle]
     */
    void verifyListClass(DecacCompiler compiler) throws ContextualError {
        LOG.debug("verify listClass: start");
         Iterator it = this.iterator();
            while(it.hasNext()){
                AbstractDeclClass cour= (AbstractDeclClass) it.next();
                cour.verifyClass(compiler);
            }
        LOG.debug("verify listClass: end");
    }

    /**
     * Pass 2 of [SyntaxeContextuelle]
     */
    public void verifyListClassMembers(DecacCompiler compiler) throws ContextualError {
        LOG.debug("verify listClassMembers: start");
        Iterator it = this.iterator();
            while(it.hasNext()){
                AbstractDeclClass cour= (AbstractDeclClass) it.next();
                cour.verifyClassMembers(compiler);
            }
         LOG.debug("verify listClass: end");
            
         
    }
    
    /**
     * Pass 3 of [SyntaxeContextuelle]
     */
    public void verifyListClassBody(DecacCompiler compiler) throws ContextualError {
        throw new UnsupportedOperationException("not yet implemented");
    }


}
