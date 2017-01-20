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
import fr.ensimag.deca.context.NullType;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.TypeDefinition;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.IMAProgram;
import fr.ensimag.ima.pseudocode.NullOperand;
import static fr.ensimag.ima.pseudocode.Register.getR;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import java.io.PrintStream;

/**
 *
 * @author ensimag
 */
public class Null extends AbstractExpr {
    
    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
       TypeDefinition def=compiler.getEnvType().get(compiler.getEnvType().getDict().create("null"));
       this.setType(def.getType());
       return this.getType();
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("null");
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        //leaf node :nothing to do 
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        //leaf node : nothing to do 
        
    }

    @Override
    protected void codeGenInstObject(IMAProgram subProg) {
        subProg.addInstruction(new LOAD(new NullOperand(), getR(0)));
    }
    
}
