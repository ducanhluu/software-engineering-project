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
import fr.ensimag.deca.context.ParamDefinition;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.VariableDefinition;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;

/**
 *
 * @author chakirs
 */
public class DeclParam extends AbstractDeclParam{
    private final AbstractIdentifier type;
    private final AbstractIdentifier name;
    public DeclParam(AbstractIdentifier type,AbstractIdentifier name){
        Validate.notNull(type);
        Validate.notNull(name);
        this.type=type;
        this.name=name;
    }
    public AbstractIdentifier getType(){
        return this.type;
    }
    @Override
    protected void verifyDeclParam(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
        Type type1=this.type.verifyType(compiler);
        this.type.setType(type1);
        if (this.type.getType().isVoid()){
                throw new ContextualError("type shouldn't be void in declaration",this.getLocation());
        }
        ParamDefinition param=new ParamDefinition(type1,this.name.getLocation());
        this.name.setDefinition(param);
        //a revoir cette implementation 
        try {
                
                localEnv.declare(this.name.getName(), param);
        } catch (EnvironmentExp.DoubleDefException ex) {
                throw new ContextualError("this variable is already declared",this.name.getLocation());
                //Logger.getLogger(DeclVar.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.name.verifyExpr(compiler, localEnv, currentClass);
    }

    @Override
    protected void codeGenDeclParam(DecacCompiler compiler) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void decompile(IndentPrintStream s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
         name.prettyPrint(s, prefix, false);
         type.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
