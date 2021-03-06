package fr.ensimag.deca.context;

import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.deca.tools.SymbolTable;
import fr.ensimag.deca.tree.Location;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
public class EnvironmentType {
    private Map<Symbol,TypeDefinition> map = new HashMap<Symbol,TypeDefinition>();
    private SymbolTable Dict = new SymbolTable();
    
    public EnvironmentType()  {
        this.initialize();
    }
    public void initialize()  {
		
        //construction des type 
        VoidType voidT =  new VoidType(Dict.create("void"));
        StringType stringT =  new StringType(Dict.create("string"));
        FloatType floatT = new FloatType(Dict.create("float"));
        BooleanType boolT = new BooleanType(Dict.create("boolean"));
        IntType intT = new IntType(Dict.create("int"));
        NullType nullT = new NullType(Dict.create("null"));
        ClassType classObjT = new ClassType(Dict.create("Object"));
        
        declare(Dict.create("void"),new TypeDefinition(voidT,null));
        declare(Dict.create("string"),new TypeDefinition(stringT,null));
        declare(Dict.create("float"),new TypeDefinition(floatT,null));
        declare(Dict.create("boolean"),new TypeDefinition(boolT,null));
        declare(Dict.create("int"),new TypeDefinition(intT,null));
        declare(Dict.create("null"),new TypeDefinition(nullT,null));
        declare(Dict.create("Object"),new ClassDefinition(classObjT,null,null));
        ClassDefinition objectClass=(ClassDefinition) get(Dict.create("Object"));
        //ClassDefinition objectClass=classObjT.getDefinition();
        Signature signatureEquals=new Signature();
        signatureEquals.add(classObjT);
        objectClass.setNumberOfFields(0);
       
        try {
             objectClass.getMembers().declare(Dict.create("equals"), new MethodDefinition(boolT,Location.BUILTIN,signatureEquals,1));
             objectClass.setNumberOfMethods(1);
        } catch (EnvironmentExp.DoubleDefException ex) {
            Logger.getLogger(EnvironmentType.class.getName()).log(Level.SEVERE, null, ex);
        }
       // System.out.println(this.toString());

    }
    public SymbolTable getDict(){
           return this.Dict;
    }
    /**
     * Return the definition of the symbol in the environment, or null if the
     * symbol is undefined.
     */
    public TypeDefinition get(Symbol key) {
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
     */
    public void declare(Symbol name, TypeDefinition def){
        if (!this.map.containsKey(name)){
            map.put(name,def);
        }
    }
    public boolean find(Symbol name){
        if (!this.map.containsKey(name)){
            return false;
        }else{
            return  true;
        }
    }

}
