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
    
    @Override
    protected void verifyDeclMethod(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
        if ( localEnv.get(this.name.getName()) != null){
            throw new ContextualError("this name has been already used in the current class either as a field or a method",this.getLocation());
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
        // declarer l'environment de la methode 
        EnvironmentExp methodEnv=new EnvironmentExp(localEnv);
        this.parametres.verifyListDeclParam(compiler, methodEnv, currentClass);
        Type type=this.typeM.verifyType(compiler);
        this.typeM.setType(type);
        Signature sign=this.getSignatureParams();
        if (mem ==null){
            try {
                localEnv.declare(this.name.getName(), new MethodDefinition(type,this.getLocation(),sign,currentClass.getNumberOfMethods()+1));
            } catch (EnvironmentExp.DoubleDefException ex) {
                Logger.getLogger(DeclMethod.class.getName()).log(Level.SEVERE, null, ex);
            }
            currentClass.incNumberOfMethods();
        }else{
            MethodDefinition SuperMethod= (MethodDefinition) mem.getMembers().get(this.name.getName());
                if(!SuperMethod.getSignature().sameSignature(sign)){
                    throw new ContextualError("this method does not have the same signature as the method in the superclass",this.getLocation());
                }else if (!SuperMethod.getType().isClass()){
                    if  (!SuperMethod.getType().sameType(this.typeM.getType())){// ici on doit voir si il est un soustype 
                        throw new ContextualError("this method is already exsisting in a superClass with a different type",this.getLocation());
                    }
                }else{
                     if ( !this.typeM.getType().isClass()){
                         throw new ContextualError("this method is already exsisting in a superClass with a different type",this.getLocation());
                     }else{
                         ClassType currentType= (ClassType) this.typeM.getType();
                         ClassType superType= (ClassType) SuperMethod.getType();
                         if (!currentType.isSubClassOf(superType)){
                             throw new ContextualError("this method type is not a sub type of the method in the super Class",this.getLocation());
                         }
                         try {
                             localEnv.declare(this.name.getName(), new MethodDefinition(type,this.getLocation(),sign,SuperMethod.getIndex()));
                         } catch (EnvironmentExp.DoubleDefException ex) {
                             Logger.getLogger(DeclMethod.class.getName()).log(Level.SEVERE, null, ex);
                         }
                    }
                }
                
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

    @Override
    protected void codeGenMethods(IMAProgram subProg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
