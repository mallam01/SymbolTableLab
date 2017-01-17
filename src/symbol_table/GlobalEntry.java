package symbol_table;

// Make no changes to this class.
// There is nothing to be done in this class; it serves as an example of 
// how to define the toString() method.

public class GlobalEntry extends ScopeEntry {
    public GlobalEntry() {
        super("**GlobalScope**");
    }

    /** 
     * Return a String representation of the Global Scope.
     *  For example, 
     *
     *  Global Scope: {
     *  class N1 {
     *      int i;
     *      int m(int x, int y);
     *  }
     *  }
     */
    public String toString() {
	// If the toString() method has been properly defined in the 
	// superclass, then that superclass method can be reused here!

	return "Global Scope: {\n" + super.toString() + "\n}\n";
    }
}              // End of GlobalEntry            

