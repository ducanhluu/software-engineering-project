package fr.ensimag.deca.context;

import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.deca.tools.SymbolTable;
import java.util.HashMap;
import java.util.Map;
public class EnvironmentType {
    private Map<Symbol,Type> map = new HashMap<Symbol,Type>();
    private SymbolTable Dict = new SymbolTable();
    //hashmap symbol,type symbol
    public EnvironmentType() throws DoubleDefException {
        this.initialize();
    }
    public void initialize() throws DoubleDefException {
		

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
    

    public class DoubleDefException extends Exception {
        private static final long serialVersionUID = -2733379901827316441L;
    }

    /**
     * Return the definition of the symbol in the environment, or null if the
     * symbol is undefined.
     */
    public Type get(Symbol key) {
        if(map.containsKey(key))
            throw new UnsupportedOperationException("not yet implemented");
        return map.get(key);
    }
    public class SymbolNotContainedInEnvType extends Exception{
        public SymbolNotContainedInEnvType(){
            System.out.println("envronnementType ne contient pas le symbol");
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
     * @throws DoubleDefException
     *             if the symbol is already defined at the "current" dictionary
     *
     */
    public void declare(Symbol name, Type def) throws DoubleDefException{
        if(map.containsKey(name))
            throw new DoubleDefException();
        map.put(name,def);
    
    }

}
