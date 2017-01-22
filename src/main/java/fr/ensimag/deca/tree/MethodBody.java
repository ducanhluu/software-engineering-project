/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.IMAProgram;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;

/**
 *
 * @author chakirs
 */
public class MethodBody extends AbstractMethodBody{
    private ListDeclVar declVariables;
    private ListInst insts;
    public MethodBody(ListInst insts,ListDeclVar declVariables){
        Validate.notNull(insts);
        Validate.notNull(declVariables);
        this.declVariables=declVariables;
        this.insts=insts;
    }
        
    @Override
    protected void verifyMethodBody(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass, Type returnType) throws ContextualError {
        this.declVariables.verifyListDeclVariable(compiler, localEnv, currentClass);
        this.insts.verifyListInst(compiler, localEnv, currentClass, returnType);
    }

    @Override
    protected void codeGenMethodBody(IMAProgram compiler) {
        declVariables.codeGenListDeclVarLocal(compiler);
        insts.codeGenListInst(compiler);
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.println();
        s.indent();
        declVariables.decompile(s);
        s.println();
        insts.decompile(s);
        s.unindent();
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        declVariables.prettyPrint(s, prefix, false);
        insts.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        this.declVariables.iterChildren(f);
        this.insts.iterChildren(f);
    }
    
}
