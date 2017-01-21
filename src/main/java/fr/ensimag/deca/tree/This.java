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
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;

/**
 *
 * @author chakirs
 */
public class This extends AbstractExpr {
    
    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
        if (currentClass == null){
            throw new ContextualError("you can't call this in a main ",this.getLocation());
        }
        return currentClass.getType();
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("this");
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        //leaf node nothing to do 
    }
    @Override
    public String toString(){
        return "this";
    }
    @Override
    protected void iterChildren(TreeFunction f) {
       //leaf node nothing to do
    }
    
}