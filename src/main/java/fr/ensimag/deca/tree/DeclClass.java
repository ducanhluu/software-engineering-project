package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
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
    private  ListDeclField fields;
    private ListDeclMethod methods;
    private List<String> vtable = new ArrayList<String>();
    public DeclClass(AbstractIdentifier name,AbstractIdentifier extension, ListDeclField fields, ListDeclMethod methods){
            Validate.notNull(name);
            Validate.notNull(extension);
            Validate.notNull(fields);
            Validate.notNull(methods);
            this.name=name;
            this.extension=extension;
            this.fields=fields;
            this.methods=methods;
    }
    @Override
    public void decompile(IndentPrintStream s) {
        s.print("class { ... A FAIRE ... }");
    }

    @Override
    protected void verifyClass(DecacCompiler compiler) throws ContextualError {
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    protected void verifyClassMembers(DecacCompiler compiler)
            throws ContextualError {
        throw new UnsupportedOperationException("not yet implemented");
    }
    
    @Override
    protected void verifyClassBody(DecacCompiler compiler) throws ContextualError {
        throw new UnsupportedOperationException("not yet implemented");
    }


    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        name.prettyPrint(s, prefix, false);
        extension.prettyPrint(s, prefix, false);
        fields.prettyPrint(s, prefix, false);
        methods.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        throw new UnsupportedOperationException("Not yet supported");
    }

    @Override
    protected void buildTableOfLabels() {
        vtable.add("code.Object.equals");
        for (AbstractDeclMethod i : methods.getList()){
            vtable.add("code." + name + "." + i.getName());
        }
    }

}
