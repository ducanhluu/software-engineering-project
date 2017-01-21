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
import fr.ensimag.deca.context.MethodDefinition;
import fr.ensimag.deca.context.Signature;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.IMAProgram;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    /*
    public void verifyDeclMethodCurrent(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass, 
            ClassDefinition superClass,Type currentType,Signature sign) throws ContextualError{
         MethodDefinition currentMethod= (MethodDefinition) currentClass.getMembers().get(this.name.getName());
                if(currentMethod.getSignature().sameSignature(sign) && currentType.sameType(currentMethod.getType())){
                    throw new ContextualError("this method exist in the current class with the same signature and return type",this.getLocation());
                }else{
                     try {
                localEnv.declare(this.name.getName(), new MethodDefinition(currentType,this.getLocation(),sign,currentClass.getNumberOfMethods()+1));
            } catch (EnvironmentExp.DoubleDefException ex) {
                Logger.getLogger(DeclMethod.class.getName()).log(Level.SEVERE, null, ex);
            }
            currentClass.incNumberOfMethods();
            }
    }*/
    protected void verifyDeclMethodSuper(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass, 
            ClassDefinition superClass,Type currentType,Signature sign) throws ContextualError {
        if (superClass ==null){
            try {
                localEnv.declare(this.name.getName(), new MethodDefinition(currentType,this.getLocation(),sign,currentClass.getNumberOfMethods()+1));
            } catch (EnvironmentExp.DoubleDefException ex) {
                Logger.getLogger(DeclMethod.class.getName()).log(Level.SEVERE, null, ex);
            }
            currentClass.incNumberOfMethods();
        }else{
            MethodDefinition SuperMethod= (MethodDefinition) superClass.getMembers().get(this.name.getName());
                if(!SuperMethod.getSignature().sameSignature(sign)){
                    throw new ContextualError("this method does not have the same signature as the method in the superclass",this.getLocation());
                }else if (!SuperMethod.getType().isClass()){
                    if  (!SuperMethod.getType().sameType(this.typeM.getType())){// ici on doit voir si il est un soustype 
                        throw new ContextualError("this method is already exsisting in a superClass with a different type",this.getLocation());
                    }else{
                        try {
                             localEnv.declare(this.name.getName(), new MethodDefinition(currentType,this.getLocation(),sign,SuperMethod.getIndex()));
                         } catch (EnvironmentExp.DoubleDefException ex) {
                             Logger.getLogger(DeclMethod.class.getName()).log(Level.SEVERE, null, ex);
                         }
                    }
                }else{
                     if ( !this.typeM.getType().isClass()){
                         throw new ContextualError("this method is already exsisting in a superClass with a different type",this.getLocation());
                     }else{
                         ClassType currentType1= (ClassType) this.typeM.getType();
                         ClassType superType= (ClassType) SuperMethod.getType();
                         if (!currentType1.isSubClassOf(superType)){
                             throw new ContextualError("this method type is not a sub type of the method in the super Class",this.getLocation());
                         }
                         try {
                             localEnv.declare(this.name.getName(), new MethodDefinition(currentType,this.getLocation(),sign,SuperMethod.getIndex()));
                         } catch (EnvironmentExp.DoubleDefException ex) {
                             Logger.getLogger(DeclMethod.class.getName()).log(Level.SEVERE, null, ex);
                         }
                    }
                } 
        }
    }
    @Override
    protected void verifyDeclMethod(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
        boolean current=false;
        if ( localEnv.get(this.name.getName()) != null){
            current=true;
        }
        ClassDefinition mem=null;
        ClassDefinition cour=currentClass.getSuperClass();
        while (cour != null){
            if (cour.getMembers().get(this.name.getName()) != null){
                if ( cour.getMembers().get(this.name.getName()).isMethod()){
                    mem=cour;
                    cour=null;
                }else {
                    throw new ContextualError("name of the method is used as a field in a superClass",this.getLocation());
                }
            }else{
                cour=cour.getSuperClass();
            }
        }
        EnvironmentExp methodEnv=new EnvironmentExp(localEnv);
        this.parametres.verifyListDeclParam(compiler, methodEnv, currentClass);
        Type type=this.typeM.verifyType(compiler);
        this.typeM.setType(type);
        Signature sign=this.getSignatureParams();
        // test si il exsite une method de meme nom dans la classe courante 
        if ( current){
            throw new ContextualError("this name is already used for a field or method in the current class ",this.getLocation());
            //this.verifyDeclMethodCurrent(compiler, localEnv, currentClass, currentClass, type, sign);
        }else{
            this.verifyDeclMethodSuper(compiler,localEnv,currentClass,mem,typeM.getType(),sign);
        }
        MethodDefinition m=(MethodDefinition) localEnv.get(this.name.getName());
        m.setEnv(methodEnv);
        this.name.verifyExpr(compiler, localEnv, currentClass);
    }
    public Signature getSignatureParams(){
        Signature sign=new Signature();
        Iterator it = this.parametres.iterator();
        while(it.hasNext()){
            DeclParam cour2 = (DeclParam)it.next();
            sign.add(cour2.getType().getType());
        }
        return sign;
    }
     @Override
    protected  void verifyDeclMethodBody(DecacCompiler compiler,
            EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError{
        MethodDefinition m=(MethodDefinition) localEnv.get(this.name.getName());
        this.body.verifyMethodBody(compiler, m.getEnv(), currentClass, this.typeM.getType());
    }
    @Override
    protected void codeGenDeclMethod(DecacCompiler compiler) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void decompile(IndentPrintStream s) {
        
        this.typeM.decompile(s);
        s.print(" ");
        this.name.decompile(s);
        s.print(" (");
        this.parametres.decompile(s);
        s.print(" ) {");
        s.println();
        this.body.decompile(s);
        s.println("}");
        
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
        typeM.iterChildren(f);
        name.iterChildren(f);
        parametres.iterChildren(f);
        body.iterChildren(f);
    }

    @Override
    protected String getName() {
        return name.getName().getName();
    }

    @Override
    protected void codeGenMethods(IMAProgram subProg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
