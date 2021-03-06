package fr.ensimag.deca.context;

import fr.ensimag.deca.tree.Location;
import fr.ensimag.ima.pseudocode.DAddr;
import static fr.ensimag.ima.pseudocode.Register.GB;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.apache.commons.lang.Validate;

/**
 * Definition of a class.
 *
 * @author gl17
 * @date 01/01/2017
 */
public class ClassDefinition extends TypeDefinition {


    public void setNumberOfFields(int numberOfFields) {
        this.numberOfFields = numberOfFields;
    }

    public int getNumberOfFields() {
        return numberOfFields;
    }

    public void incNumberOfFields() {
        this.numberOfFields++;
    }

    public int getNumberOfMethods() {
        return numberOfMethods;
    }

    public void setNumberOfMethods(int n) {
        Validate.isTrue(n >= 0);
        numberOfMethods = n;
    }
    
    public int incNumberOfMethods() {
        numberOfMethods++;
        return numberOfMethods;
    }

    private int numberOfFields = 0;
    private int numberOfMethods = 0;
    private DAddr addrVT = new RegisterOffset(1, GB);
    private Map<Integer, String> vtable = new TreeMap<Integer, String>();

    
    public DAddr getAddressOfVTable() {
        return addrVT;
    }
    
    public void setAddressOfVTable(DAddr val) {
        addrVT = val;
    }
    
    public Map getVTable() {
        return vtable;
    }
    
    public void copyAllElements(Map<Integer, String> map) {
        vtable.putAll(map);
    }
    
    public void addLabelToVTable(int index, String s) {
        vtable.put(index, s);
    }
    
    public Iterator getIteratorIndex() {
        return vtable.keySet().iterator();
    }
    @Override
    public boolean isClass() {
        return true;
    }
    
    @Override
    public ClassType getType() {
        // Cast succeeds by construction because the type has been correctly set
        // in the constructor.
        return (ClassType) super.getType();
    };

    public ClassDefinition getSuperClass() {
        return superClass;
    }

    private final EnvironmentExp members;
    private final ClassDefinition superClass; 

    public EnvironmentExp getMembers() {
        return members;
    }
    /*
    @Override
    public String toString(){
     //cette fonction est utile pour le débogage, elle peut etre supprimé après
        if (superClass!=null){
            return "(class_"+this.getNature()+",superClass "+this.superClass.getType().toString() +")\n";
        }else{
            return "(class_"+this.getNature()+",superClass"+" 0)\n";
        }
    }*/
    public ClassDefinition(ClassType type, Location location, ClassDefinition superClass) {
        super(type, location);
        EnvironmentExp parent;
        if (superClass != null) {
            parent = superClass.getMembers();
            this.setNumberOfMethods(superClass.numberOfMethods);
            this.setNumberOfFields(superClass.numberOfFields);
        } else {
            parent = null;
        }
        members = new EnvironmentExp(parent);
        this.superClass = superClass;
        vtable.put(1, "code.Object.equals");
    }
    
}
