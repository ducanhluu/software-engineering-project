/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.ExpDefinition;
import fr.ensimag.deca.context.MethodDefinition;
import fr.ensimag.deca.context.Signature;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;

/**
 *
 * @author chakirs
 */
public class MethodCall extends AbstractExpr {
    private AbstractExpr object;
    private AbstractIdentifier ident;
    private ListExpr arguments;
    public MethodCall(AbstractExpr object,AbstractIdentifier ident,ListExpr arguments){
        Validate.notNull(object);
        Validate.notNull(ident);
        Validate.notNull(arguments);
        this.object=object;
        this.ident=ident;
        this.arguments=arguments;
     
    }
    public Signature getSignatureArgs(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)throws ContextualError{
        Signature sign=new Signature();
        for (AbstractExpr i : arguments.getList()){
            sign.add(this.verifyExpr(compiler,localEnv, currentClass));
        }
        return sign;
        
    }
    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
            Type type=this.object.verifyExpr(compiler, localEnv, currentClass);
            if ( !type.isClass()){
                throw new ContextualError("can't call a method : left operand is not a class",this.getLocation());
            }else{
                ClassType typeClass=(ClassType) type;
                Identifier methodIdent=(Identifier) ident;
                ExpDefinition def= methodIdent.verifySelection(compiler, typeClass.getDefinition().getMembers(), typeClass.getDefinition());
                if ( !def.isMethod()){
                     throw new ContextualError("this ident is not a method",this.getLocation());
                }else{
                   Signature signExpected= ((MethodDefinition) def).getSignature();
                   Signature args =this.getSignatureArgs(compiler, localEnv, currentClass);
                   if ( !signExpected.sameSignature(args)){
                       throw new ContextualError("this method has a different signature ",this.getLocation());
                   }
                   return def.getType();
                }
            }            
    }

    @Override
    public void decompile(IndentPrintStream s) {
        this.object.decompile(s);
        s.print(".");
        this.ident.decompile(s);
        s.print("(");
        this.arguments.decompile(s);
        s.print(")");
        
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        this.object.prettyPrint(s, prefix, false);
        this.ident.prettyPrint(s, prefix, false);
        this.arguments.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        this.object.iterChildren(f);
        this.ident.iterChildren(f);
        this.arguments.iterChildren(f);
    }
}
