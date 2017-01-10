package fr.ensimag.deca.context;

import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.deca.tools.SymbolTable;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
public class EnvironmentType {
    private Map<Symbol,Type> map = new HashMap<Symbol,Type>();
    private SymbolTable Dict = new SymbolTable();
    public EnvironmentType()  {
        this.initialize();
    }
    public void initialize()  {
		

        VoidType voidT =  new VoidType(Dict.create("void"));
        StringType stringT =  new StringType(Dict.create("string"));
        FloatType floatT = new FloatType(Dict.create("float"));
        BooleanType boolt = new BooleanType(Dict.create("boolean"));
        IntType intT = new IntType(Dict.create("int"));
        NullType nullT = new NullType(Dict.create("null"));
        ClassType classObjT = new ClassType(Dict.create("Object"));

        declare(Dict.create("void"),voidT);
        declare(Dict.create("string"),stringT);
        declare(Dict.create("float"),floatT);
        declare(Dict.create("boolean"),boolt);
        declare(Dict.create("int"),intT);
        declare(Dict.create("null"),nullT);
        declare(Dict.create("Object"),classObjT);


    }
    public SymbolTable getDict(){
           return this.Dict;
    }
    /**
     * Return the definition of the symbol in the environment, or null if the
     * symbol is undefined.
     */
    public Type get(Symbol key) {
        return map.get(key);
    }
    public class SymbolNotContainedInEnvType extends Exception{
        public SymbolNotContainedInEnvType(){
            System.out.println("envronnementType ne contient pas le symbole");
        }
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
    public void declare(Symbol name, Type def){
        if (!this.map.containsKey(name)){
            map.put(name,def);
        }
    }
        
    

}
