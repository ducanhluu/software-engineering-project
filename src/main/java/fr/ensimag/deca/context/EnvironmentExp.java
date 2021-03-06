package fr.ensimag.deca.context;

import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.deca.tools.SymbolTable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
/**
 * Dictionary associating identifier's ExpDefinition to their names.
 * 
 * This is actually a linked list of dictionaries: each EnvironmentExp has a
 * pointer to a parentEnvironment, corresponding to superblock (eg superclass).
 * 
 * The dictionary at the head of this list thus corresponds to the "current" 
 * block (eg class).
 * 
 * Searching a definition (through method get) is done in the "current" 
 * dictionary and in the parentEnvironment if it fails. 
 * 
 * Insertion (through method declare) is always done in the "current" dictionary.
 * 
 * @author gl17
 * @date 01/01/2017
 */
public class EnvironmentExp {
    // A FAIRE : implémenter la structure de donnée représentant un
    // environnement (association nom -> définition, avec possibilité
    // d'empilement).
    private SymbolTable Dict;
    EnvironmentExp parentEnvironment;
    private HashMap<Symbol,ExpDefinition> map = new HashMap<Symbol,ExpDefinition>();
    
    public EnvironmentExp(EnvironmentExp parentEnvironment) {
        this.parentEnvironment = parentEnvironment;
    }
    public EnvironmentExp getParent(){
        return parentEnvironment;
    }
    public static class DoubleDefException extends Exception {
        private static final long serialVersionUID = -2733379901827316441L;
    }

    /**
     * Return the definition of the symbol in the environment, or null if the
     * symbol is undefined.
     */
    public ExpDefinition get(Symbol key) {
        return map.get(key);
    }

    /**
     * Add the definition def associated to the symbol name in the environment.
     * 
     * Adding a symbol which is already defined in the environment,
     * - throws DoubleDefException if the symbol is in the "current" dictionary 
     * - or, hides the previous declaration otherwise.
     * 
     * @param name
     *            Name of the symbol to define
     * @param def
     *            Definition of the symbol
     * @throws DoubleDefException
     *             if the symbol is already defined at the "current" dictionary
     *
     */
    public void declare(Symbol name, ExpDefinition def) throws DoubleDefException {
        if(this.map.containsKey(name)){
            throw new DoubleDefException();
        }
        else
        {
            map.put(name,def);
        }
    }
   
    public SymbolTable getSymTable(){
        return this.Dict;
    }
    /*
     @Override
    public String toString(){
        String s="";
        Set<Map.Entry<Symbol, ExpDefinition>> couples = map.entrySet();
        Iterator<Map.Entry<Symbol, ExpDefinition>> itCouples = couples.iterator();
    while (itCouples.hasNext()) {
      Map.Entry<Symbol, ExpDefinition> couple = itCouples.next();
      s=s+couple.getKey().toString()+" => "+ couple.getValue().toString()+"\n";
    }
        return s;
    }
    */
}
