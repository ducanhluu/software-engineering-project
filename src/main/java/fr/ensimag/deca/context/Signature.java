package fr.ensimag.deca.context;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Signature of a method (i.e. list of arguments)
 *
 * @author gl17
 * @date 01/01/2017
 */
public class Signature {
    List<Type> args = new ArrayList<Type>();

    public void add(Type t) {
        args.add(t);
    }
    
    public Type paramNumber(int n) {
        return args.get(n);
    }
    
    public int size() {
        return args.size();
    }
    public boolean sameSignature(Signature otherSign){
        if (this.size() != otherSign.size()){
            return false;
        }else{
             Iterator it = this.args.iterator();
             Iterator it2=otherSign.args.iterator();
            while(it.hasNext()){
                Type cour1= (Type) it.next();
                Type cour2= (Type) it2.next();
                if (!cour1.sameType(cour2)){
                    return false;
                }
            }
        }
        return true; 
    }

}
