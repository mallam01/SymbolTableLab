package symbol_table;

// Make no changes to this class.
// There is nothing to be done in this abstract superclass.

public abstract class Entry {
    private String name_ = "<Anonymous>";
    private Type type_ = Type.voidValue;

    public Entry() {
    }
    public Entry(String name) {
	this(name, null);
    }
    public Entry(String name, Type t) {
	if (name != null) {
	    name_ = name;
	}
	if (t != null) {
	    type_ = t;
	}
    }
    public String name() {
	return name_;
    }
    public Type type() {
	return type_;
    }

    /** 
     * Returns a String representation of this entry.
     */
    public abstract String toString();
}              // End of Entry            

