package symbol_table;


import java.util.*;

public class BlockEntry extends ScopeEntry {
    private static int blockCount = 0;
    
    public BlockEntry() {
        super(new String("##Block" + blockCount + "##"));
	blockCount++;  // ensures that there are no name conflicts among blocks
    }

    /** 
     * Only instances of VariableEntry can be inserted into a block scope, 
     * i.e., local variables.
     * If symTabEntry is a VariableEntry, then insert it into the local 
     * symbol table declared in the superclass.  
     */
    public boolean addBinding(String name, Entry symTabEntry) {
		return false;
    }

    /** 
     * Return a String representation of the block.
     */
    public String toString() {
		return null;
	// If the toString() method has been properly defined in the 
	// superclass, then that superclass method can be reused here!
	// For example, super.toString() should print all of the 
	// bindings declared in this block, i.e., this 
	// method should be written in one line (see GlobalScope).

    }
}              // End of class BlockEntry            

