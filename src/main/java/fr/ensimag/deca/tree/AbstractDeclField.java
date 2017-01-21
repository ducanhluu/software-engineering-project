package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.ima.pseudocode.IMAProgram;

/**
 * Variable declaration
 *
 * @author gl17
 * @date 01/01/2017
 */
public abstract class AbstractDeclField extends Tree {
    
    /**
     * Implements non-terminal "decl_var" of [SyntaxeContextuelle] in pass 3
     * @param compiler contains "env_types" attribute
     * @param localEnv 
     *   its "parentEnvironment" corresponds to the "env_exp_sup" attribute
     *   in precondition, its "current" dictionary corresponds to 
     *      the "env_exp" attribute
     *   in postcondition, its "current" dictionary corresponds to 
     *      the synthetized attribute
     * @param currentClass 
     *          corresponds to the "class" attribute (null in the main bloc).
     */    
    protected abstract void verifyDeclField(DecacCompiler compiler,
            EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError;
    
    protected abstract void verifyDeclFieldInit(DecacCompiler compiler,
            EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError;
    /**
     * Generate assembly code for the instruction.
     * 
     * @param compiler
     */
    protected abstract void codeGenDeclField(IMAProgram subProg);

    protected abstract void codeGenInitNull(IMAProgram subProg);

    protected abstract void codeGenInitExplicit(IMAProgram subProg);

  
}
