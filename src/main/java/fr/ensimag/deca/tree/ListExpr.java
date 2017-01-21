package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import static fr.ensimag.deca.codegen.MemoryManagement.getLastUsedRegisterToStore;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import static fr.ensimag.ima.pseudocode.Register.SP;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.STORE;

/**
 * List of expressions (eg list of parameters).
 *
 * @author gl17
 * @date 01/01/2017
 */
public class ListExpr extends TreeList<AbstractExpr> {


    @Override
    public void decompile(IndentPrintStream s) {
        for (AbstractExpr i : getList()){
            i.decompile(s);
            if ( getList().get(getList().size()-1) != i){
                s.print(",");
            }
        }
    }

    public void codeGenInst(DecacCompiler compiler) {
        int i = 0;
        for (AbstractExpr e : getReversedList()){
            i--;
            e.codeGenInst(compiler);
            compiler.addInstruction(new STORE(getLastUsedRegisterToStore(), new RegisterOffset(i, SP)));
        }
        
    }
}
