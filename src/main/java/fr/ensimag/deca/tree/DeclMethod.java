/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;

/**
 *
 * @author chakirs
 */
public class DeclMethod extends AbstractDeclMethod{
    final private AbstractIdentifier typeM;
    final private AbstractIdentifier name;
    final private ListDeclParam parametres;
    final private AbstractMethodBody body;
    public DeclMethod(AbstractIdentifier typeM, AbstractIdentifier name,ListDeclParam parametres, AbstractMethodBody body){
         Validate.notNull(typeM);
         Validate.notNull(name);
         Validate.notNull(parametres);
         Validate.notNull(body);
         this.name=name;
         this.typeM=typeM;
         this.parametres=parametres;
         this.body=body;
    }
    
    @Override
    protected void verifyDeclMethod(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void codeGenDeclMethod(DecacCompiler compiler) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void decompile(IndentPrintStream s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        typeM.prettyPrint(s, prefix, false);
        name.prettyPrint(s, prefix, false);
        parametres.prettyPrint(s, prefix, false);
        body.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected String getName() {
        return name.getName().getName();
    }

}
