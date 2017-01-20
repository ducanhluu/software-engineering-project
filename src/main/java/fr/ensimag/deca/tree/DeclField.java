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
         ClassDefinition cour=currentClass;
         while (cour != null){
        
            if (cour.getMembers().get(this.varName.getName()) != null){
                 throw new ContextualError("variable is already a field or a method in a super class",this.getLocation());
            }
            cour=cour.getSuperClass();
         }
         Type type=this.type.verifyType(compiler);
         this.type.setType(type);
         if (type.isVoid()){
             throw new ContextualError("in a field type shouldn't be void",this.getLocation());
         }
        try {
            //cela est l'environnment de la classe courante 
            localEnv.declare(this.varName.getName(), new FieldDefinition(type,this.getLocation(),this.v,currentClass,currentClass.getNumberOfFields()+1));
        } catch (EnvironmentExp.DoubleDefException ex) {
            Logger.getLogger(DeclField.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.varName.verifyExpr(compiler, localEnv, currentClass);
        currentClass.incNumberOfFields();
      
        
    }
    //nouvelle fonction verify 
    @Override
    protected  void verifyDeclFieldInit(DecacCompiler compiler,
            EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError{
        this.initialization.verifyInitialization(compiler, this.type.getType(), localEnv, currentClass);
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
