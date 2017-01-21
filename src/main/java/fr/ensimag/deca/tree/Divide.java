package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import static fr.ensimag.deca.codegen.MemoryManagement.divisionIsUsed;
import static fr.ensimag.deca.codegen.MemoryManagement.overflowOPNeeded;
import static fr.ensimag.deca.codegen.MemoryManagement.getAvailableRegister;
import static fr.ensimag.deca.codegen.MemoryManagement.getLastUsedRegisterToStore;
import static fr.ensimag.deca.codegen.MemoryManagement.setLastUsedRegister;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.IMAProgram;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.BEQ;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.DIV;
import fr.ensimag.ima.pseudocode.instructions.QUO;

/**
 *
 * @author gl17
 * @date 01/01/2017
 */
public class Divide extends AbstractOpArith {

    public Divide(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected String getOperatorName() {
        return "/";
    }
// test type pour savoir si on utilise div ou quo

    @Override
    protected void codeGenInst(IMAProgram compiler) {
        super.codeGenInst(compiler);

        if (getRightOperand() instanceof Identifier && !(getLeftOperand() instanceof Identifier)) {
            compiler.addInstruction(new LOAD(((Identifier) getRightOperand()).getVariableDefinition().getOperand(), getAvailableRegister(compiler)));
            GPRegister reg2 = getLastUsedRegisterToStore();
            if (getRightOperand().getType().isInt()) {
                divisionIsUsed = true;
                compiler.addInstruction(new CMP(0, reg));
                compiler.addInstruction(new BEQ(new Label("division_by_zero_error")));
            } else {
                compiler.addInstruction(new LOAD((float) 0.0, getAvailableRegister(compiler)));
                compiler.addInstruction(new CMP(getLastUsedRegisterToStore(), reg));
                compiler.addInstruction(new BEQ(new Label("overflow_error")));
            }
            if (getRightOperand().getType().isInt() && getLeftOperand().getType().isInt()) {
                compiler.addInstruction(new QUO(reg, reg2));
                setLastUsedRegister(reg2.getNumber());
            } else {
                compiler.addInstruction(new DIV(reg, reg2));
                setLastUsedRegister(reg2.getNumber());
            }
        } else {
            if (getRightOperand().getType().isInt()) {
                divisionIsUsed = true;
                compiler.addInstruction(new LOAD(val, getAvailableRegister(compiler)));
                compiler.addInstruction(new CMP(0, getLastUsedRegisterToStore()));
                compiler.addInstruction(new BEQ(new Label("division_by_zero_error")));

            } else {
                overflowOPNeeded = true;
                compiler.addInstruction(new LOAD((float) 0.0, getAvailableRegister(compiler)));
                GPRegister regTemps = getLastUsedRegisterToStore();
                compiler.addInstruction(new LOAD(val, getAvailableRegister(compiler)));
                compiler.addInstruction(new CMP(regTemps, getLastUsedRegisterToStore()));
                compiler.addInstruction(new BEQ(new Label("overflow_error")));
            }

            if (getRightOperand().getType().isInt() && getLeftOperand().getType().isInt()) {
                compiler.addInstruction(new QUO(val, reg));
                setLastUsedRegister(reg.getNumber());
            } else {
                compiler.addInstruction(new DIV(val, reg));
                setLastUsedRegister(reg.getNumber());
            }
        }
    }

}
