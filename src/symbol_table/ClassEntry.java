package symbol_table;


public class ClassEntry extends ScopeEntry {
    public ClassEntry(String name) {
    	super(name);
    }

    /** 
     * Only instances of VariableEntry, MethodEntry, or ClassEntry can be 
     * inserted into a class scope.
     * If symTabEntry is a VariableEntry, MethodEntry, or ClassEntry,
     * then insert it into the local symbol table declared in the 
     * superclass (ScopeEntry), otherwise return false.  
     */
    public boolean addBinding(String name, Entry symTabEntry) {
    	if(symTabEntry instanceof VariableEntry ||
    			symTabEntry instanceof MethodEntry ||
    			symTabEntry instanceof ClassEntry)
    	{
    		return super.addBinding(name, symTabEntry);
    	}
    	else
    	{
    		return false;
    	}
    }

    /** 
     * Return a String representation of the class.
     */
    public String toString() {
    	return "class "+this.name()+" {\n"+ super.toString()+"}";
    }
}              // End of class ClassEntry            

