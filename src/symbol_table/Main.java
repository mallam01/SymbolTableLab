package symbol_table;


import java.util.*;

public class Main {
    static SymbolTable symtab;

    static Yytoken nextToken(Yylex lexer) throws java.io.IOException {
	Yytoken tok = lexer.yylex();
	if (tok != null) {
	    tok.m_line += 1; // for some reason the line number is off by one
	}
	return tok;
    }
    static Type matchVarType(int tok) {
	switch (tok) {
	case Yytoken.t_int:
	    return Type.intVar;
	case Yytoken.t_char:
	    return Type.charVar;
	case Yytoken.t_string:
	    return Type.stringVar;
	case Yytoken.t_float:
	    return Type.floatVar;
	default:
	    return Type.errorType;
	}
    }
    static Type matchValueType(int tok) {
	switch (tok) {
	case Yytoken.t_int:
	    return Type.intValue;
	case Yytoken.t_char:
	    return Type.charValue;
	case Yytoken.t_void:
	    return Type.voidValue;
	case Yytoken.t_string:
	    return Type.stringValue;
	case Yytoken.t_float:
	    return Type.floatValue;
	default:
	    return null;
	}
    }
    static VariableEntry makeVariable(Type t, Yytoken tok2) {
	if (tok2.m_index == Yytoken.t_ident) {
	    String name = tok2.m_text;
	    return new VariableEntry(name, t);
	} else {
	    return null;
	}
    }
    static String insertErrorMessage(Entry ent, String name) {
	if (ent == null) {
	    return (" -- redeclaration of identifier " + name);
	} else if (ent instanceof MethodEntry) {
	 return (" -- method declarations are not allowed in this scope");
	} else if (ent instanceof ClassEntry) {
	    return (" -- class declarations are not allowed in this scope");
	}
	return " -- binding for " + name + " was not inserted ";
    }
    static void enterScope(String name, int line) {
	Entry se1 = symtab.lookup(name);
	if (se1 != null) {
	    if (se1 instanceof ScopeEntry) {
		symtab.enterScope((ScopeEntry)se1);
		System.out.println("Successful command "
				   + line + ": new_scope " + name);
	    } else {
		System.out.println("Unsuccessful command "
				   + line + ": new_scope " + name
				   + " -- not a class or method");
	    }
	} else {
	    System.out.println("Unsuccessful command "
			       + line + ": new_scope " + name
			       + " -- not a class or method");
	}
    }
    static void successMessage(Yytoken tok[], int length) {
	System.out.print("Successful command " + tok[0].m_line + ": ");
	System.out.print(tok[0].m_text + " ");
	for (int i=1; i<length; i++) {
	    System.out.print(" " + tok[i].m_text);
	}
	System.out.println();
    }
    static void errorMessage(Yytoken tok[], int length, String msg) {
	System.out.print("Unsuccessful command "
			 + tok[0].m_line + ": ");
	System.out.print(tok[0].m_text + " ");
	for (int i=1; i<length; i++) {
	    System.out.print(" " + tok[i].m_text);
	}
	System.out.println(msg);
    }
    static void invalidCmdMessage(Yytoken tok[], int length, String msg) {
	System.out.print("Invalid command "
			 + tok[0].m_line + ": ");
	System.out.print(tok[0].m_text);
	for (int i=1; i<length; i++) {
	    System.out.print(" " + tok[i].m_text);
	}
	System.out.println(msg);
    }


    public static void main(String[] args) {
	if (args.length == 0) {
	    System.out.println("No input file has been specified");
	    System.exit(-1);
	}
	for (int i=0; i<args.length; i++) {
	    try {
	      //  System.out.println("Processing " + args[i]);
		processFile(args[i]);
	    }
	    catch (java.io.IOException e) {
		System.out.println(e);
	    }
	}
    }

    public static void matchNewScopeId(Yytoken tok[]) {
	if (tok[1] == null) {
	    // EOF
	    invalidCmdMessage(tok, 1, "");
	} else if (tok[1].m_index == Yytoken.t_ident) {
	    enterScope(tok[1].m_text, tok[0].m_line);
	    System.out.println("The new scope is ");
	    System.out.println(symtab.currentScope());
	} else {
	    errorMessage(tok, 2, "");
	}
    }


    public static void processFile(String fileName) throws java.io.IOException {
	Entry se1, se2;
	String name, name2;
	Type rt;
	VariableEntry parm1, parm2;
	Vector parms;
	Yytoken token[] = new Yytoken[10];

	symtab = new SymbolTable();
	Yylex lexer = new Yylex(new java.io.FileInputStream(fileName));
	token[0] = nextToken(lexer);

	// the lexer returns null on end of file.
	while (token[0] != null) {
	    switch (token[0].m_index) {
	    case Yytoken.t_new_scope:
	    {
		token[1] = nextToken(lexer);
		matchNewScopeId(token);
		break;
	    }
	    case Yytoken.t_end_scope:
	    {
		ScopeEntry se = symtab.leaveScope();
		if (se != null) {
		    successMessage(token, 1);
		    System.out.println("Leaving scope ");
		    System.out.println(se);
		    System.out.println("The new scope is ");
		    System.out.println(symtab.currentScope());
		} else {
		    errorMessage(token, 1, " -- cannot leave Global Scope");
		}
		break;
	    }
	    case Yytoken.t_lookup1:
	    {
		token[1] = nextToken(lexer);
		if (token[1].m_index == Yytoken.t_ident) {
		    name = token[1].m_text;
		    se1 = symtab.lookup(name);
		    if (se1 != null) {
			successMessage(token, 2);
			System.out.println(se1);
		    } else {
			errorMessage(token, 2, " -- unknown identifier ");
		    }
		} else {
		    invalidCmdMessage(token, 2, "");
		}
		break;
	    }
	    case Yytoken.t_lookup2:
	    {
		token[1] = nextToken(lexer);
		if (token[1].m_index != Yytoken.t_ident) {
		    invalidCmdMessage(token, 2, "");
		    break;
		}
		token[2] = nextToken(lexer);
		if(token[2].m_index != Yytoken.t_dot) {
		    invalidCmdMessage(token, 3, "");
		    break;
		}
		token[3] = nextToken(lexer);
		if (token[3].m_index != Yytoken.t_ident) {
		    invalidCmdMessage(token, 4, "");
		} else {
		    name = token[1].m_text;
		    name2 = token[3].m_text;
		    se1 = symtab.lookup(name, name2);
		    if (se1 != null) {
			successMessage(token, 4);
			System.out.println(se1);
		    } else {
			errorMessage(token, 4, " -- unknown identifier " + name2);
		    }
		}
		break;
	    }
	    case Yytoken.t_method:
	    {
		token[1] = nextToken(lexer);
		rt = matchValueType(token[1].m_index);
		if (rt == null) {
		    errorMessage(token, 2, " -- invalid return type");
		    break;
		}
		// match identifier (method name)
		token[2] = nextToken(lexer);
		if (token[2].m_index != Yytoken.t_ident) {
		    errorMessage(token, 3, " -- invalid method name");
		    break;
		}
		token[3] = nextToken(lexer);
		if (token[3].m_index != Yytoken.t_l_par) {
		    errorMessage(token, 4, 
				 "expecting '(' but found " + token[3].m_text);
		    break;
		}
		// insert MethodEntry into symbol table
		// enter method scope
		name = token[2].m_text;
		MethodEntry me = new MethodEntry(name, rt);
		symtab.enterScope(me);
		// match the formal parameters
		token[4] = nextToken(lexer);

		while (token[4].m_index != Yytoken.t_r_par) {
		    Type parmtype = matchVarType(token[4].m_index);
		    if (parmtype == null) {
			errorMessage(token, 5, " -- invalid parameter type"
				     + "expecting a type but found " 
				     + token[4].m_text);
			break;
		    }
		    token[5] = nextToken(lexer);
		    parm1 = makeVariable(parmtype, token[5]);
		    if (parm1 == null) {
			errorMessage(token, 6, " -- invalid parameter name"
				     + "expecting an identifier but found " 
				     + token[5].m_text);
			break;
		    }
		    if (! symtab.insertBinding(parm1) ) {
			errorMessage(token, 6, 
				     " -- duplicate parameter names "
				     + token[5].m_text);
		    }
		    token[4] = nextToken(lexer);
		    if (token[4].m_index == Yytoken.t_comma) {
			token[4] = nextToken(lexer); // skip ','
		    }
		}
		// symtab.endScope();
		symtab.leaveScope();
		se1 = symtab.lookup(name);
		if (se1 != null) {
		    errorMessage(token, 4, 
				 ") -- redeclaration of identifier " 
				 + name);
		} else if (! symtab.insertBinding(me)) {
		    String msg = insertErrorMessage(me, me.name());
		    errorMessage(token, 4, ")" + msg);
		} else {
		    System.out.print("Successful command "
				     + token[0].m_line + ": ");
		    System.out.println("method " + me);
		}
		break;
	    }
	    case Yytoken.t_variable:
	    {
		token[1] = nextToken(lexer);
		Type t = matchValueType(token[1].m_index);
		if (t == null) {
		    errorMessage(token, 2, " invalid type");
		    break;
		}
		token[2] = nextToken(lexer);
		se1 = makeVariable(t, token[2]);
		if (se1 != null) {
		    if (symtab.insertBinding(se1)) {
			successMessage(token, 3);
		    } else {
			String msg = insertErrorMessage(null, se1.name());
			errorMessage(token, 3, msg);
		    }
		} else {
		    errorMessage(token, 3, " -- not a valid variable name");
		}
		break;
	    }
	    case Yytoken.t_class:
	    {
		token[1] = nextToken(lexer);
		if (token[1].m_index == Yytoken.t_ident) {
		    name = token[1].m_text;
		    se1 = new ClassEntry(name);
		    if (symtab.insertBinding(se1)) {
			successMessage(token, 2);
		    } else {
			String msg = insertErrorMessage(se1, se1.name());
			errorMessage(token, 2, msg);
		    }
		} else {
		    invalidCmdMessage(token, 2, "");
		}
		break;
	    }
	    case Yytoken.t_new_block:
	    {
		if (symtab.enterNewBlock()) {
		    successMessage(token, 1);
		} else {
		    errorMessage(token, 2, " -- unable to enter block scope");
		}
		break;
	    }
	    case Yytoken.t_method_scope:
	    {
		se1 = symtab.enclosingMethod();
		if (se1 != null) {
		    successMessage(token, 1);
		    System.out.println(se1);
		} else {
		    errorMessage(token, 1, " -- no enclosing method");
		}
		break;
	    }
	    case Yytoken.t_print_symtab:
	    {
		successMessage(token, 1);
		System.out.println(symtab);
		break;
	    }
	    default:
	    {
		System.out.print("Unknown command at line "
				 + token[0].m_line + ": ");
		System.out.println(token[0].m_text);
		break;
	    }
	    }
	    token[0] = nextToken(lexer);
	    int j = 1;
	    while (token[j] != null) {
		token[j] = null;
		j++;
	    }
	}
    }
}              // End of Main.java            

