package symbol_table;


import java.util.LinkedHashMap;
import java.util.Iterator;

public abstract class ScopeEntry extends Entry {

    // The following data structure is needed to preserve the order 
    // that the bindings are recorded in this ScopeEntry.
    private LinkedHashMap<String, Entry> localSymtab = new LinkedHashMap<String, Entry>();
    // The following field is needed for methods reset(), hasNext(), 
    // and next().
    private Iterator<Entry> iterator;

    public ScopeEntry(String name) {
    	super(name);
    }
    public ScopeEntry(String name, Type t) {
    	super(name, t);
    }

    /**
     * Adds a binding for 'symTabEntry' into the local symbol 
     * table (localSymTab), i.e., it adds a mapping from the 'name' 
     * to the 'symTabEntry' parameter.
     *
     * If there is already a binding for that name, then return 
     * false and do not add a new binding.
     * If the insert is successful, return true. 
     *
     * Each inserted entry must also be stored in some data structure 
     * that preserves the order in which the bindings were first 
     * inserted into 'localSymTab'; this is so they can be retrieved 
     * in this order.
     * For example, method 'toString' needs to retrieve them in order.
     * Retrieval in order is done using the iterator methods below.
     *
     * The LinkedHashMap data structure is used so the lookup is fast 
     * and so the bindings can be retrieved in the proper order!
     */
    public boolean addBinding(String name, Entry symTabEntry) {
    	if(localSymtab.containsKey(name)){
    		return false;}
    	else{
    		this.localSymtab.put(name, symTabEntry);
    		return true;
    	}
    }

    /**
     * Look up 'name' in the local symbol table (localSymTab).
     * Return null if not found.
     */
    public Entry lookup(String name) {
    	return this.localSymtab.get(name);
    }

    // The purpose of the following iterator methods is to allow access to 
    // the bindings in 'localSymTab' in the order they were first inserted. 
    // The LinkedHashMap data structure is used so the bindings 
    // can be retrieved in the proper order!

    // This ordering is needed because we want to print and type check 
    // entries in the order they originally occured in the program 
    // (i.e., in the order they were inserted into the local symbol 
    // table)!!

    // You need to do this by writing the iterator methods, 'reset', 
    // 'next', and 'hasMore' below.

    /** Resets the iteration to the beginning, i.e., to the first element 
     *  in the iteration.
     */
    public void reset() {
    	this.iterator = localSymtab.values().iterator();
    }

    /** Returns the current element and advances the iteration.
     */
    public Entry next() {
    	 return this.iterator.next();
    }

    /** Returns whether or not there are more elements to be iterated.
     */
    public boolean hasMore() {
    	return this.iterator.hasNext();
    }

    /** Return a String representing all the entries in the local symbol table.
     *  For example, 
     *
     *  int i;
     *  int m(int x, int y);
     */
    public String toString() {
	// Use the iterator methods, reset(), next(), and hasMore() 
	// to get the entries; then for each entry call toString().
	// Add a semicolon after each VariableEntry.  
    	String output = "";
    	reset();
    	while(hasMore()) {
    		Entry entry = next();
    		output += entry.toString();
    		if (entry instanceof VariableEntry)
    			output += ";";
    		output += "\n";
    	}
    	//reset();
    	return output;
    }
}