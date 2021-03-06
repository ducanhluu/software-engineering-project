/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import static fr.ensimag.deca.codegen.MemoryManagement.getAvailableRegister;
import static fr.ensimag.deca.codegen.MemoryManagement.getLastUsedRegisterToStore;
import static fr.ensimag.deca.codegen.MemoryManagement.heapOverflowNeeded;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.IMAProgram;
import fr.ensimag.ima.pseudocode.Label;
import static fr.ensimag.ima.pseudocode.Register.getR;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructions.BSR;
import fr.ensimag.ima.pseudocode.instructions.LEA;
import fr.ensimag.ima.pseudocode.instructions.NEW;
import fr.ensimag.ima.pseudocode.instructions.POP;
import fr.ensimag.ima.pseudocode.instructions.PUSH;
import fr.ensimag.ima.pseudocode.instructions.STORE;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;

/**
 * (e.g : Point p = new Point() ... )
 * @author gl17
 */
public class New extends AbstractExpr {
    private final AbstractIdentifier name;
    public New(AbstractIdentifier name){
           Validate.notNull(name);
           this.name=name;
    }
    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
        Type type=this.name.verifyType(compiler);
        name.setType(type);
        if ( !name.getType().isClass()){    
            throw new ContextualError("new can be applied only on classes",this.getLocation());
        }
        return name.getType();
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print(" new ");
        name.decompile(s);
        s.print("();");
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        name.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        name.iterChildren(f);
    }
    
    @Override
    protected void codeGenInst(IMAProgram compiler) {
        int size = name.getClassDefinition().getNumberOfFields() + 1;
        DAddr addrVTable = name.getClassDefinition().getAddressOfVTable();
        heapOverflowNeeded = true;
        compiler.addInstruction(new NEW(size, getAvailableRegister(compiler)));
        compiler.addInstruction(new BOV(new Label("heap_overflow_error")));
        compiler.addInstruction(new LEA(addrVTable, getR(0)));
        compiler.addInstruction(new STORE(getR(0), new RegisterOffset(0, getLastUsedRegisterToStore())));
        compiler.addInstruction(new PUSH(getLastUsedRegisterToStore()));
        compiler.addInstruction(new BSR(new Label("init." + name.getName().toString())));
        compiler.addInstruction(new POP(getLastUsedRegisterToStore()));
    }
}
