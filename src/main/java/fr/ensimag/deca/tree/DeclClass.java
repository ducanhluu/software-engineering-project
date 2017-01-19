package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;

/**
 * Declaration of a class (<code>class name extends superClass {members}<code>).
 * 
 * @author gl17
 * @date 01/01/2017
 */
public class DeclClass extends AbstractDeclClass {
    private final AbstractIdentifier name;
    private final AbstractIdentifier extension;
    private  ListDeclField field;
    private ListDeclMethod method;
    public DeclClass(AbstractIdentifier name,AbstractIdentifier extension, ListDeclField field, ListDeclMethod method){
            Validate.notNull(name);
            Validate.notNull(extension);
            Validate.notNull(field);
            Validate.notNull(method);
            this.name=name;
            this.extension=extension;
            this.field=field;
            this.method=method;
    }
    @Override
    public void decompile(IndentPrintStream s) {
        s.print("class { ... A FAIRE ... }");
    }

    @Override
    protected void verifyClass(DecacCompiler compiler) throws ContextualError {
        if (compiler.getEnvType().find(this.name.getName())){
            throw new ContextualError("erreur Contextuelle:cette class a deja etait déclaré",this.getLocation());
        }
        if (!compiler.getEnvType().find(this.extension.getName())){
            throw new ContextualError("erreur Contextuelle:la class mére est non encore delcaré",this.getLocation());
        }
        ClassDefinition superClass= (ClassDefinition) compiler.getEnvType().get(this.extension.getName()); 
        ClassType type=new ClassType(this.name.getName(),this.getLocation(),superClass);
        ClassDefinition currentClass=type.getDefinition();
        compiler.getEnvType().declare(this.name.getName(), currentClass);
    }
    

    @Override
    protected void verifyClassMembers(DecacCompiler compiler)
            throws ContextualError {
        this.name.verifyType(compiler);
        this.extension.verifyType(compiler);
        ClassDefinition currentClass=(ClassDefinition) compiler.getEnvType().get(this.name.getName());
        currentClass.incNumberOfMethods();
        this.field.verifyListDeclField(compiler, currentClass.getMembers(), currentClass);
        this.method.verifyListDeclMethod(compiler, currentClass.getMembers(), currentClass);
    }
    
    @Override
    protected void verifyClassBody(DecacCompiler compiler) throws ContextualError {
        throw new UnsupportedOperationException("not yet implemented");
    }


    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        name.prettyPrint(s, prefix, false);
        extension.prettyPrint(s, prefix, false);
        field.prettyPrint(s, prefix, false);
        method.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        throw new UnsupportedOperationException("Not yet supported");
    }

}
