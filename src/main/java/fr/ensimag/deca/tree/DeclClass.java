package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.DecacCompiler;
import static fr.ensimag.deca.codegen.MemoryManagement.getSizeOfVTables;
import static fr.ensimag.deca.codegen.MemoryManagement.increSizeOfVtables;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.MethodDefinition;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable;
import fr.ensimag.ima.pseudocode.DAddr;
import static fr.ensimag.ima.pseudocode.Register.GB;
import static fr.ensimag.ima.pseudocode.Register.getR;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.LEA;
import fr.ensimag.ima.pseudocode.instructions.STORE;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.Map;
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

    public DeclClass(AbstractIdentifier name, AbstractIdentifier extension, ListDeclField fields, ListDeclMethod methods){
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
        if (compiler.getEnvType().find(this.name.getName())){
            throw new ContextualError("erreur Contextuelle:cette class a deja etait déclaré",this.getLocation());
        }
        if (!compiler.getEnvType().find(this.extension.getName())){
            throw new ContextualError("erreur Contextuelle:la class mére est non encore delcaré",this.getLocation());
        }
        ClassDefinition superClass= (ClassDefinition) compiler.getEnvType().get(this.extension.getName());
        ClassType type=new ClassType(this.name.getName(),this.getLocation(),superClass);
        ClassDefinition currentClass=new ClassDefinition(type,this.getLocation(),superClass);
        compiler.getEnvType().declare(this.name.getName(), currentClass);
        
       // System.out.println(compiler.getEnvType().get(this.name.getName()).toString());
     //   System.out.println(compiler.getEnvType().toString());
    }
    

    @Override
    protected void verifyClassMembers(DecacCompiler compiler)
            throws ContextualError {
        this.name.verifyType(compiler);
        this.extension.verifyType(compiler);
        ClassDefinition currentClass=(ClassDefinition) compiler.getEnvType().get(this.name.getName());
        currentClass.incNumberOfMethods();
        this.fields.verifyListDeclField(compiler, currentClass.getMembers(), currentClass);
        //this.method.verifyListDeclMethod(compiler, currentClass.getMembers(), currentClass);
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
        Map<Integer, String> vtable = extension.getClassDefinition().getVTable();
        ClassDefinition nameDefinition = name.getClassDefinition();
        
        nameDefinition.copyAllElements(vtable);
        String s;
        int index;
        SymbolTable st = nameDefinition.getMembers().getSymTable();
        for (AbstractDeclMethod i : methods.getList()) {
            s = "code." + name + "." + i.getName();
            index = ((MethodDefinition) nameDefinition.getMembers().get(st.create(i.getName()))).getIndex();
            nameDefinition.addLabelToVTable(index, s);
        }
    }

    @Override
    protected void codeGenBuildVTable(DecacCompiler compiler) {
        DAddr addrVTSP = extension.getClassDefinition().getAddressOfVTable();
        DAddr addrVT = new RegisterOffset(getSizeOfVTables() + 1, GB);
        name.getClassDefinition().setAddressOfVTable(addrVT);
        Map<Integer, String> vtable = name.getClassDefinition().getVTable();

        compiler.addInstruction(new LEA(addrVTSP, getR(0)));
        compiler.addInstruction(new STORE(getR(0), addrVT));
        
        int i = 1;
        String s;
        Iterator<Integer> it = name.getClassDefinition().getIteratorLabel();
        while (it.hasNext()) {
           i++;
           s = vtable.get(it.next());
           compiler.addInstruction(new LOAD(s, getR(0)));
           compiler.addInstruction(new STORE(getR(0), new RegisterOffset(getSizeOfVTables() + i, GB))); 
        }
        
        increSizeOfVtables(i);
    }

}
