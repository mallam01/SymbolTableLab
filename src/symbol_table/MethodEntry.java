package symbol_table;



public class MethodEntry extends ScopeEntry {

    public MethodEntry(String name, Type returnType) {
        super(name, returnType);
    }

    /** 
     * Only instances of VariableEntry can be inserted 
     * into the scope of a MethodEntry, i.e., only the 
     * formal parameters can be inserted.
     */
    public boolean addBinding(String name, Entry symTabEntry) {
    	if(symTabEntry instanceof VariableEntry)
    	{
    		return super.addBinding(name, symTabEntry);
    	}
    	else
    	{
    		return false;
    	}
    }

    /** 
     * Returns a String representation of the method signature.
     * E.g., int m(int i, char c);
     */
    public String toString() {
	// The formal parameters are recorded in the super class, 
	// i.e. 'ScopeEntry'.
	// Use the iterator methods (reset(), next(), hasMore())
	// Use them to retrieve the parameters in the proper order.
	// Note that the superclass method toString() cannot be used 
	// here because the delimiters are different (comma here 
	// versus semicolon in the superclass method).
    	String output = this.type() + " "+this.name() + "(";
    	reset();
    	while(hasMore()) {
    		output += next();
    		if(hasMore())
    				output += " ,";
    		//next();
    	}
    	return output+");";
    }
}              // End of class MethodEntry            

