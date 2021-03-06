package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.IMAProgram;
import java.util.Iterator;
/**
 * List of declarations (e.g. int x; float y,z).
 * 
 * @author gl17
 * @date 01/01/2017
 */
public class ListDeclVar extends TreeList<AbstractDeclVar> {

    @Override
    public void decompile(IndentPrintStream s) {
        for (AbstractDeclVar i : getList()){
            i.decompile(s);
             if ( getList().get(getList().size()-1) != i){
                s.println();
            }
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
    void verifyListDeclVariable(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError{
            Iterator it = this.iterator();
            while(it.hasNext()){
				AbstractDeclVar cour = (AbstractDeclVar)it.next();
				cour.verifyDeclVar(compiler,localEnv,currentClass);
			}
    }

    public void codeGenListDeclVar(IMAProgram compiler) {
        for (AbstractDeclVar i : getList()) {
            i.codeGenDeclVar(compiler);
        }
    }

    public void codeGenListDeclVarLocal(IMAProgram compiler) {
        for (AbstractDeclVar i : getList()) {
            i.codeGenDeclVarLocal(compiler);
        }
    }
}
