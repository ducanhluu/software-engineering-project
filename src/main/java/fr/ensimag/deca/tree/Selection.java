/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import static fr.ensimag.deca.codegen.CodeGenInst.codeGenPrintFloat;
import static fr.ensimag.deca.codegen.CodeGenInst.codeGenPrintInteger;
import static fr.ensimag.deca.codegen.MemoryManagement.getDAddr;
import static fr.ensimag.deca.codegen.MemoryManagement.getLastUsedRegisterToStore;
import static fr.ensimag.deca.codegen.MemoryManagement.setDAddr;
import static fr.ensimag.deca.codegen.MemoryManagement.dereferencementNull;
import static fr.ensimag.deca.codegen.MemoryManagement.freeRegister;
import static fr.ensimag.deca.codegen.MemoryManagement.isMain;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.FieldDefinition;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.IMAProgram;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.NullOperand;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.BEQ;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;

/**
 * (e.g: this.x, a.getx(),...)
 * @author gl17
 */
public class Selection extends AbstractLValue {

    private AbstractExpr leftOperand;
    private AbstractIdentifier ident;

    public Selection(AbstractExpr leftOp, AbstractIdentifier ident) {
        Validate.notNull(leftOp);
        Validate.notNull(ident);
        this.leftOperand = leftOp;
        this.ident = ident;
    }

   
    @Override
    public void decompile(IndentPrintStream s) {
        this.leftOperand.decompile(s);
        s.print(".");
        this.ident.decompile(s);

    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        leftOperand.prettyPrint(s, prefix, false);
        ident.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        leftOperand.iterChildren(f);
        ident.iterChildren(f);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
        Identifier classIdent = null;
        FieldDefinition def = null;
        Type type;

        type = this.leftOperand.verifyExpr(compiler, localEnv, currentClass);
        this.leftOperand.setType(type);
        if (!type.isClass()) {
            throw new ContextualError("you can select only from classes", this.getLocation());
        }
        Identifier rightIdent = (Identifier) this.ident;
        ClassDefinition local = type.asClassType("not a class", Location.BUILTIN).getDefinition();
        def = (FieldDefinition) rightIdent.verifySelection(compiler, local.getMembers(), local);
        if (def.getVisibility() == Visibility.PROTECTED) {
            if (currentClass == null) {
                throw new ContextualError("field has protected access", this.getLocation());

            } else {
                if (!type.asClassType("not a class", Location.BUILTIN).isSubClassOf(currentClass.getType())
                        || !currentClass.getType().isSubClassOf(def.getContainingClass().getType())) {
                    throw new ContextualError("field has protected access,and this class is not a subclass", this.getLocation());
                }
            }

        }
        return def.getType();
        //    }
    }

    @Override
    protected void codeGenInst(IMAProgram compiler) {
        leftOperand.codeGenInst(compiler);
        if (isMain) {
            dereferencementNull = true;
            compiler.addInstruction(new CMP(new NullOperand(), getLastUsedRegisterToStore()));
            compiler.addInstruction(new BEQ(new Label("dereferencement_null_error")));
        }
        freeRegister(getLastUsedRegisterToStore().getNumber());
        ident.codeGenInst(compiler);
        setDAddr(new RegisterOffset(ident.getFieldDefinition().getIndex(), getLastUsedRegisterToStore()));
    }
    
    @Override
    protected void codeGenPrint(IMAProgram compiler) {
        codeGenInst(compiler);
        if (getType().isInt()) {
            codeGenPrintInteger(compiler, getDAddr());
        }
        else if (getType().isFloat()){
            codeGenPrintFloat(compiler, getDAddr());
        }
    }
}
