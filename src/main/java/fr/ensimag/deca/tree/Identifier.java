package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import static fr.ensimag.deca.codegen.CodeGenInst.codeGenPrintFloat;
import static fr.ensimag.deca.codegen.CodeGenInst.codeGenPrintInteger;
import static fr.ensimag.deca.codegen.MemoryManagement.getAvailableRegister;
import static fr.ensimag.deca.codegen.MemoryManagement.getLastUsedRegisterToStore;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.Definition;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.FieldDefinition;
import fr.ensimag.deca.context.MethodDefinition;
import fr.ensimag.deca.context.ExpDefinition;
import fr.ensimag.deca.context.ParamDefinition;
import fr.ensimag.deca.context.VariableDefinition;
import fr.ensimag.deca.tools.DecacInternalError;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;
import fr.ensimag.deca.context.TypeDefinition;
import fr.ensimag.ima.pseudocode.IMAProgram;
import static fr.ensimag.ima.pseudocode.Register.LB;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.LOAD;

/**
 * Deca Identifier
 *
 * @author gl17
 * @date 01/01/2017
 */
public class Identifier extends AbstractIdentifier {

    @Override
    protected void checkDecoration() {
        if (getDefinition() == null) {
            throw new DecacInternalError("Identifier " + this.getName() + " has no attached Definition");
        }
    }

    @Override
    public Definition getDefinition() {
        return definition;
    }

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a
     * ClassDefinition.
     *
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     *
     * @throws DecacInternalError if the definition is not a class definition.
     */
    @Override
    public ClassDefinition getClassDefinition() {
        try {
            return (ClassDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                    + getName()
                    + " is not a class identifier, you can't call getClassDefinition on it");
        }
    }

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a
     * MethodDefinition.
     *
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     *
     * @throws DecacInternalError if the definition is not a method definition.
     */
    @Override
    public MethodDefinition getMethodDefinition() {
        try {
            return (MethodDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                    + getName()
                    + " is not a method identifier, you can't call getMethodDefinition on it");
        }
    }

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a
     * FieldDefinition.
     *
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     *
     * @throws DecacInternalError if the definition is not a field definition.
     */
    @Override
    public FieldDefinition getFieldDefinition() {
        try {
            return (FieldDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                    + getName()
                    + " is not a field identifier, you can't call getFieldDefinition on it");
        }
    }

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a
     * VariableDefinition.
     *
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     *
     * @throws DecacInternalError if the definition is not a variable definition.
     */
    @Override
    public VariableDefinition getVariableDefinition() {
        try {
            return (VariableDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                    + getName()
                    + " is not a variable identifier, you can't call getVariableDefinition on it");
        }
    }

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a
     * ExpDefinition.
     *
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     *
     * @throws DecacInternalError if the definition is not a Exp definition.
     */
    @Override
    public ExpDefinition getExpDefinition() {
        try {
            return (ExpDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                    + getName()
                    + " is not a Exp identifier, you can't call getExpDefinition on it");
        }
    }
    
     /**
     * Like {@link #getDefinition()}, but works only if the definition is a
     * ParamDefinition.
     *
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     *
     * @throws DecacInternalError if the definition is not a param definition.
     */
    @Override
    public ParamDefinition getParamDefinition() {
        try {
            return (ParamDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                    + getName()
                    + " is not a Param identifier, you can't call getParamDefinition on it");
        }
    }

    @Override
    public void setDefinition(Definition definition) {
        this.definition = definition;
    }

    @Override
    public Symbol getName() {
        return name;
    }

    private Symbol name;

    public Identifier(Symbol name) {
        Validate.notNull(name);
        this.name = name;
    }

    public ExpDefinition verifySelection(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        EnvironmentExp cour = localEnv;
        EnvironmentExp mem = null;
        while (cour != null) {
            if (cour.get(name) != null) {
                mem = cour;
                cour = null;
            } else {
                cour = cour.getParent();
                if (cour == null) {
                    throw new ContextualError("variable not declared ", this.getLocation());
                }
            }
        }
        this.setDefinition(mem.get(name));
        return mem.get(name);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        EnvironmentExp cour = localEnv;
        EnvironmentExp mem = null;
        while (cour != null) {
            if (cour.get(name) != null) {
                mem = cour;
                cour = null;
            } else {
                cour = cour.getParent();
                if (cour == null) {
                    throw new ContextualError("variable or field  not declared ", this.getLocation());
                }
            }
        }
        this.setDefinition(mem.get(name));
        return mem.get(name).getType();
    }

    /**
     * Implements non-terminal "type" of [SyntaxeContextuelle] in the 3 passes
     *
     * @param compiler contains "env_types" attribute
     */
    @Override
    public Type verifyType(DecacCompiler compiler) throws ContextualError {
        //throw new UnsupportedOperationException("not yet implemented");
        if (compiler.getEnvType().get(name) == null) {
            throw new ContextualError("type not defined ", this.getLocation());
        }
        TypeDefinition typeDef = compiler.getEnvType().get(compiler.getEnvType().getDict().create(this.getName().getName()));
        typeDef.setLocation(Location.BUILTIN);
        this.setDefinition(typeDef);
        return typeDef.getType();
    }

    private Definition definition;

    @Override
    protected void iterChildren(TreeFunction f) {
        // leaf node => nothing to do
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // leaf node => nothing to do
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print(name.toString());
    }

    @Override
    String prettyPrintNode() {
        return "Identifier (" + getName() + ")";
    }

    @Override
    protected void prettyPrintType(PrintStream s, String prefix) {
        Definition d = getDefinition();
        if (d != null) {
            s.print(prefix);
            s.print("definition: ");
            s.print(d);
            s.println();
        }
    }

    @Override
    protected void codeGenPrint(IMAProgram compiler) {
        if (definition.getType().isInt()) {
            codeGenPrintInteger(compiler, getExpDefinition().getOperand());
        } else if (definition.getType().isFloat()) {
            codeGenPrintFloat(compiler, getExpDefinition().getOperand());
        }
    }

    @Override
    protected void codeGenInst(IMAProgram compiler) {
        if (definition.isParam()) {
            int index = -2 - getParamDefinition().getIndex();
            compiler.addInstruction(new LOAD(new RegisterOffset(index, LB), getAvailableRegister(compiler)));
        } else if (definition.isField()) {
            int index = getFieldDefinition().getIndex();
            compiler.addInstruction(new LOAD(new RegisterOffset(index, getLastUsedRegisterToStore()), getAvailableRegister(compiler)));
        } else {
            compiler.addInstruction(new LOAD(getExpDefinition().getOperand(), getAvailableRegister(compiler)));
        }
    }
}
