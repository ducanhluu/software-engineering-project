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
import fr.ensimag.deca.context.FieldDefinition;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;
import fr.ensimag.deca.context.Type;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author chakirs
 */
public class DeclField extends AbstractDeclField {
    final private Visibility v;
    final private AbstractIdentifier type;
    final private AbstractIdentifier varName;
    final private AbstractInitialization initialization;

    public DeclField(Visibility v, AbstractIdentifier type, AbstractIdentifier varName, AbstractInitialization initialization) {
        Validate.notNull(v);
        Validate.notNull(type);
        Validate.notNull(varName);
        Validate.notNull(initialization);
        this.v=v;
        this.type = type;
        this.varName = varName;
        this.initialization = initialization;
    }
    @Override
    protected  void verifyDeclField(DecacCompiler compiler,
            EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError{
        //je sais pas si on doit parcourir tout les environnement parents
         if (localEnv.get(this.varName.getName()) != null){
             throw new ContextualError("cette variable est deja un champ",this.getLocation());
         }
         Type type=this.type.verifyType(compiler);
         if (type.isVoid()){
             throw new ContextualError("in each field type shouldn't be void",this.getLocation());
         }
        try {
            localEnv.declare(this.varName.getName(), new FieldDefinition(type,this.getLocation(),this.v,currentClass,currentClass.getNumberOfFields()+1));
        } catch (EnvironmentExp.DoubleDefException ex) {
            Logger.getLogger(DeclField.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.initialization.verifyInitialization(compiler, type, localEnv, currentClass);
        this.varName.verifyExpr(compiler, localEnv, currentClass);
        currentClass.incNumberOfFields();
        System.out.println(compiler.getEnvType().toString()); 
        
    }
    
    @Override
    protected  void codeGenDeclField(DecacCompiler compiler){
        
    }
    @Override
    protected
    void iterChildren(TreeFunction f) {
        type.iter(f);
        varName.iter(f);
        initialization.iter(f);
    }

    @Override
    public void decompile(IndentPrintStream s) {
        this.type.decompile(s);
        s.print(" ");
        this.varName.decompile(s);
        s.print(" ");
        this.initialization.decompile(s);
    }
    @Override
    public String prettyPrintNode() {
        // a revoir 
        return "[visibility="+this.v.toString()+"] "+this.getClass().getSimpleName();
    }
    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        type.prettyPrint(s, prefix, false);    
        varName.prettyPrint(s, prefix, false);
        initialization.prettyPrint(s, prefix, true);
    }
}
