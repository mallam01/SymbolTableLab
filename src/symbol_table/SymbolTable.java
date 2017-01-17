package symbol_table;

import java.util.*;


public class SymbolTable {

	Deque<ScopeEntry> scopesStack;
	ScopeEntry globalScopes;
    /**
     *  Create a new global scope object.  Initialize the scope 
     *  stack so this new global scope entry is the current scope,
     *  i.e., this global scope entry must be the top entry of the scope 
     *  stack.  Also, note that from now on, this global scope will always be 
     *  at the bottom of the stack.  In this case there is only one entry 
     *  on the scope stack.  
     */
    public SymbolTable() {
    	scopesStack = new ArrayDeque<ScopeEntry>();
    	globalScopes = new GlobalEntry();
    	scopesStack.push(globalScopes);
    }

    /**  
     *  Insert a binding for parameter 'symTabEntry' in the current scope, 
     *  i.e., in the top entry of the scope stack.  
     *  No ScopeEntry is pushed onto or popped off the scope stack 
     *  during this operation.  This method only adds a binding for 
     *  'symTabEntry'.  
     */
    public boolean insertBinding(Entry symTabEntry) {
    	ScopeEntry currentScope = scopesStack.peek();
    	return currentScope.addBinding(symTabEntry.name(), symTabEntry);
    }

    /**  
     *  Return a String representing the current scope, 
     *  i.e., call toString() on the top entry.
     */
    public String currentScope() {
    	return scopesStack.peek().toString();
    }

    /**  
     *  Lookup 'name' in each ScopeEntry on the stack until found, 
     *  starting at the top (the current scope) and ending with the 
     *  global scope (the entry at the bottom of the stack). 
     *  Return null if not found.
     *
     *  This is done to handle nested scopes, i.e., so the proper binding 
     *  in the nearest enclosing scope is returned in cases when the same 
     *  name denotes two different entities.  
     */
    public Entry lookup(String name) {
    	Iterator<ScopeEntry> it = scopesStack.iterator();
    	while(it.hasNext())
    	{
    		ScopeEntry currentScope = it.next();
    		Entry tmpEntry = currentScope.lookup(name);
    		if(tmpEntry !=null){
    			 return tmpEntry;
    		}
    	}
    	return null;
    }

    /**  
     *  Look up the qualified name 'name1.name2'
     *  
     *  To do this, first look up 'name1' as in the above 'lookup' method.  
     *  If found, make sure it is an instance of ClassEntry.
     *  If not a ClassEntry (or null), return null. 
     *  If it is, then look up name2 in this ClassEntry. 
     *  
     *  Return null if 'name1.name2' is not found.
     */
    public Entry lookup(String name1, String name2) {
    	Entry baseEntry = this.lookup(name1);
    	if(baseEntry instanceof ClassEntry)
    		return ((ClassEntry)baseEntry).lookup(name2);
    	else
    		return null;
    }

    /**  
     *  Return the MethodEntry instance nearest the top of the scope stack. 
     *  Return null if no such object is found in the scope stack.
     */
    public MethodEntry enclosingMethod() {
    	Iterator<ScopeEntry> it = scopesStack.iterator();
    	while(it.hasNext()){
    		ScopeEntry scopeEntry = it.next();
    		if(scopeEntry instanceof MethodEntry)
    			return (MethodEntry)scopeEntry;
    	}
    	return null;
    }

    /**  
     *  Make the parameter 'scopeEntry' the current scope, i.e., push 
     *  'scopeEntry' onto the scope stack.
     *
     *  Return true if the operation is successful, otherwise return false.
     */
    public boolean enterScope(ScopeEntry scopeEntry) {
    	try{
    		scopesStack.push(scopeEntry);
    		return true;
    	}
    	catch(Exception e){
    		return false;
    	}
    }

    /**  
     *  Leave the current scope by popping the current scope entry 
     *  (the top entry) off the scope stack.
     *  Return the ScopeEntry that is being popped off the stack.
     *
     *  Return null if there is an attempt to leave the global scope, 
     *  that is, do not allow the bottom entry (the global scope) 
     *  to be popped off the stack.  Thus, in such cases, return 
     *  null and do not leave the global scope.  
     */
    public ScopeEntry leaveScope() {
    	ScopeEntry scopeEntry = scopesStack.pop();
    	if(scopeEntry instanceof GlobalEntry){
    		scopesStack.push(scopeEntry);
    		return null;
    	}
    	return scopeEntry;
    }

    /**  
     *  Create a new instance of 'BlockEntry' and make it the current scope, 
     *  i.e., push the newly created 'BlockEntry' onto the top of the 
     *  scope stack.
     *
     *  Return true if the operation is successful, otherwise return false.
     */
    public boolean enterNewBlock() {
    	try{
    		scopesStack.push(new BlockEntry());
    		return true;
    	}
    	catch(Exception e)
    	{
    		return false;
    	}
    }

    /**  
     *  Return a String representation of the global scope entry 
     *  (the entry at the bottom of scope stack)
     *  i.e., call toString() on the bottom entry of the stack.
     */
    public String toString() {
    	return scopesStack.getLast().toString();
    }
}              // End of class SymbolTable            
