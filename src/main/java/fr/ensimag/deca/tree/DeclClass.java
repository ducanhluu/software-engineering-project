package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.DecacCompiler;
import static fr.ensimag.deca.codegen.MemoryManagement.freeRegisters;
import static fr.ensimag.deca.codegen.MemoryManagement.getNumberLocalVariables;
import static fr.ensimag.deca.codegen.MemoryManagement.getNumberSavedRegistersInMet;
import static fr.ensimag.deca.codegen.MemoryManagement.getPusedRegs;
import static fr.ensimag.deca.codegen.MemoryManagement.getSizeOfVTables;
import static fr.ensimag.deca.codegen.MemoryManagement.increNumberSavedRegisters;
import static fr.ensimag.deca.codegen.MemoryManagement.increSizeOfVtables;
import static fr.ensimag.deca.codegen.MemoryManagement.overflowNeeded;
import static fr.ensimag.deca.codegen.MemoryManagement.returnNeeded;
import static fr.ensimag.deca.codegen.MemoryManagement.isMain;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.MethodDefinition;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.IMAProgram;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Line;
import static fr.ensimag.ima.pseudocode.Register.GB;
import static fr.ensimag.ima.pseudocode.Register.getR;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.ADDSP;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructions.BRA;
import fr.ensimag.ima.pseudocode.instructions.BSR;
import fr.ensimag.ima.pseudocode.instructions.ERROR;
import fr.ensimag.ima.pseudocode.instructions.LEA;
import fr.ensimag.ima.pseudocode.instructions.STORE;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.POP;
import fr.ensimag.ima.pseudocode.instructions.PUSH;
import fr.ensimag.ima.pseudocode.instructions.RTS;
import fr.ensimag.ima.pseudocode.instructions.SUBSP;
import fr.ensimag.ima.pseudocode.instructions.TSTO;
import fr.ensimag.ima.pseudocode.instructions.WNL;
import fr.ensimag.ima.pseudocode.instructions.WSTR;
import java.io.PrintStream;
import java.util.Deque;
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
    private ListDeclField fields;
    private ListDeclMethod methods;

    public DeclClass(AbstractIdentifier name, AbstractIdentifier extension, ListDeclField fields, ListDeclMethod methods) {
        Validate.notNull(name);
        Validate.notNull(extension);
        Validate.notNull(fields);
        Validate.notNull(methods);
        this.name = name;
        this.extension = extension;
        this.fields = fields;
        this.methods = methods;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        this.name.decompile(s);
        s.print(" extends ");
        this.extension.decompile(s);
        s.print(" {");
        s.println();
        this.fields.decompile(s);
        s.println();
        this.methods.decompile(s);
        s.println();
        s.print("}");
    }

    @Override
    protected void verifyClass(DecacCompiler compiler) throws ContextualError {
        if (compiler.getEnvType().find(this.name.getName())) {
            throw new ContextualError("erreur Contextuelle:cette class a deja etait déclaré", this.getLocation());
        }
        if (!compiler.getEnvType().find(this.extension.getName())) {
            throw new ContextualError("erreur Contextuelle:la class mére est non encore déclaré", this.getLocation());
        }
        ClassDefinition superClass = (ClassDefinition) compiler.getEnvType().get(this.extension.getName());
        ClassType type = new ClassType(this.name.getName(), this.getLocation(), superClass);
        ClassDefinition currentClass = type.getDefinition();

        compiler.getEnvType().declare(this.name.getName(), currentClass);
    }

    @Override
    protected void verifyClassMembers(DecacCompiler compiler)
            throws ContextualError {
        this.name.verifyType(compiler);
        this.extension.verifyType(compiler);
        ClassDefinition currentClass = (ClassDefinition) compiler.getEnvType().get(this.name.getName());
        currentClass.setNumberOfMethods(currentClass.getSuperClass().getNumberOfMethods());
        currentClass.setNumberOfFields(currentClass.getSuperClass().getNumberOfFields());
        this.fields.verifyListDeclField(compiler, currentClass.getMembers(), currentClass);
        this.methods.verifyListDeclMethod(compiler, currentClass.getMembers(), currentClass);

    }

    @Override
    protected void verifyClassBody(DecacCompiler compiler) throws ContextualError {
        //verify listDeclfield intialization 
        //verify listDecl field method
        ClassDefinition currentClass = (ClassDefinition) compiler.getEnvType().get(this.name.getName());
        this.fields.verifyListDeclFieldInit(compiler, currentClass.getMembers(), currentClass);
        this.methods.verifyListDeclMethodBody(compiler, currentClass.getMembers(), currentClass);
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
        this.name.iterChildren(f);
        this.extension.iterChildren(f);
        this.fields.iterChildren(f);
        this.methods.iterChildren(f);
    }

    @Override
    protected void buildTableOfLabels() {
        Map<Integer, String> vtable = extension.getClassDefinition().getVTable();
        ClassDefinition nameDefinition = name.getClassDefinition();

        nameDefinition.copyAllElements(vtable);
        String s;
        int index;
        for (AbstractDeclMethod i : methods.getList()) {
            s = "code." + name.getName().toString() + "." + i.getStringName();
            index = ((MethodDefinition) nameDefinition.getMembers().get(i.getSymbolName())).getIndex();
            nameDefinition.addLabelToVTable(index, s);
        }
    }

    @Override
    protected void codeGenBuildVTable(IMAProgram compiler) {
        DAddr addrVTSP = extension.getClassDefinition().getAddressOfVTable();
        DAddr addrVT = new RegisterOffset(getSizeOfVTables() + 1, GB);
        name.getClassDefinition().setAddressOfVTable(addrVT);
        Map<Integer, String> vtable = name.getClassDefinition().getVTable();

        compiler.addInstruction(new LEA(addrVTSP, getR(0)));
        compiler.addInstruction(new STORE(getR(0), addrVT));

        int i = 1;
        String s;
        Iterator<Integer> it = name.getClassDefinition().getIteratorIndex();
        while (it.hasNext()) {
            i++;
            int index = it.next();
            s = vtable.get(index);
            compiler.addInstruction(new LOAD(s, getR(0)));
            compiler.addInstruction(new STORE(getR(0), new RegisterOffset(getSizeOfVTables() + i, GB)));
        }

        increSizeOfVtables(i);
    }

    @Override
    protected void codeGenMethods(IMAProgram compiler) {
        isMain = false;
        IMAProgram subProg = new IMAProgram();
        
        subProg.addComment("----------------------------------------------------");
        subProg.addComment("                      Classe " + name.getName().toString());
        subProg.addComment("----------------------------------------------------");

        subProg.addLabel(new Label("init." + name.getName().toString()));
        freeRegisters();
        if (extension.getClassDefinition().getSuperClass() == null) {
            fields.codeGenListDeclField(subProg);
        } else {
            subProg.addInstruction(new TSTO(3));
            subProg.addInstruction(new BOV(new Label("stack_overflow_error")));
            overflowNeeded = true;
            fields.codeGenInitNull(subProg);
            subProg.addInstruction(new PUSH(getR(1)));
            subProg.addInstruction(new BSR(new Label("init." + extension.getName().toString())));
            subProg.addInstruction(new SUBSP(1));
            fields.codeGenInitExplicit(subProg);
        }

        subProg.addInstruction(new RTS());

        for (AbstractDeclMethod i : methods.getList()) {
            freeRegisters();
            subProg.addComment("---------- Codage de la methode " 
                    + i.getStringName() + " dans la classe " + name.getName().toString());
            subProg.addLabel(new Label("code." + name.getName().toString() + "." + i.getStringName()));
            IMAProgram metProg = new IMAProgram();
            i.codeGenDeclMethod(metProg);
            if (returnNeeded) {
                metProg.addInstruction(new BRA(new Label("fin." 
                        + name.getName().toString() + "." + i.getStringName())));
                metProg.addInstruction(new WSTR("Error: Exit of method "+ 
                        name.getName().toString() + "." + i.getStringName() + " without return"));
                metProg.addInstruction(new WNL());
                metProg.addInstruction(new ERROR());
            }
            metProg.addLabel(new Label("fin." + name.getName().toString() + "." + i.getStringName()));
            Deque<GPRegister> list = getPusedRegs();
            if (list.size() > 0) {
                int d = 0;
                int v = getNumberLocalVariables();
                metProg.addFirst(new Line("-----------------------------"));
                metProg.addComment("Restauration des registres");
                while (!list.isEmpty()) {
                    GPRegister reg = list.remove();
                    metProg.addFirst(new PUSH(reg));
                    metProg.addInstruction(new POP(reg));
                    d++;
                }
                metProg.addFirst(new Line("Sauvegarde des registres"));
                if (v > 0) {
                    metProg.addFirst(new ADDSP(v));
                }
                metProg.addFirst(new BOV(new Label("stack_overflow_error")));
                metProg.addFirst(new TSTO(d + v + getNumberSavedRegistersInMet()));
                increNumberSavedRegisters(d);
            }
            metProg.addInstruction(new RTS());
            subProg.append(metProg);
        }
        compiler.append(subProg);

    }

}
