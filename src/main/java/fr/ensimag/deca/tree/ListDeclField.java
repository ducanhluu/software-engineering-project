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
import java.util.Iterator;

/**
 *
 * @author chakirs
 */
public class ListDeclField extends TreeList<AbstractDeclField> {
     @Override
    public void decompile(IndentPrintStream s) {
        for (AbstractDeclField i : getList()){
            i.decompile(s);
            s.println();
        }
    }

    /**
     * Implements non-terminal "list_decl_var" of [SyntaxeContextuelle] in pass 3
     * @param compiler contains the "env_types" attribute
     * @param localEnv 
     *   its "parentEnvironment" corresponds to "env_exp_sup" attribute
     *   in precondition, its "current" dictionary corresponds to 
     *      the "env_exp" attribute
     *   in postcondition, its "current" dictionary corresponds to 
     *      the "env_exp_r" attribute
     * @param currentClass 
     *          corresponds to "class" attribute (null in the main bloc).
     */    
    void verifyListDeclField(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError{
            Iterator it = this.iterator();
            while(it.hasNext()){
				AbstractDeclField cour = (AbstractDeclField)it.next();
				cour.verifyDeclField(compiler,localEnv,currentClass);
			}
    }
    void verifyListDeclFieldInit(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError{
            Iterator it = this.iterator();
            while(it.hasNext()){
				AbstractDeclField cour = (AbstractDeclField)it.next();
				cour.verifyDeclFieldInit(compiler,localEnv,currentClass);
	    }
    }
    public void codeGenListDeclField(DecacCompiler compiler) {
        for (AbstractDeclField i : getList()) {
            i.codeGenDeclField(compiler);
        }
    }
}
