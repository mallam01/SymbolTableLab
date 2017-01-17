package symbol_table;

public class Yytoken {
    // ***************** Static Token constants ***************************

  public static final int t_new_scope = 0
		 , t_end_scope = 1
		 , t_lookup1 = 2
		 , t_lookup2 = 3
		 , t_int = 4
		 , t_char = 5
		 , t_string = 6
		 , t_float = 7
		 , t_class = 8
		 , t_method = 9
		 , t_method1 = 10
		 , t_method2 = 11
		 , t_variable = 12
		 , t_method_scope = 13
		 , t_new_block = 14
		 , t_end_block = 15
		 , t_print_symtab = 16
		 , t_ident = 17
		 , t_l_par = 18
		 , t_r_par = 19
		 , t_comma = 20
		 , t_dot = 21
		 , t_void = 22
		 ;
  
    // ***************** Static Error Members ***************************

  private static final String errorMsg[] = {
    "Error: Unmatched end-of-comment punctuation.",
    "Error: Unmatched start-of-comment punctuation.",
    "Error: Unclosed string.",
    "Error: Illegal character."
  };
  
  public static final int E_ENDCOMMENT = 0; 
  public static final int E_STARTCOMMENT = 1; 
  public static final int E_UNCLOSEDSTR = 2; 
  public static final int E_UNMATCHED = 3; 

  public static void error(int code) {
	  System.out.println(errorMsg[code]);
  }
  
    // ************ Non-static Member Declarations ***********************

  public int m_index;
  public String m_text;
  public int m_line;
  public int m_charBegin;
  public int m_charEnd;
  
  Yytoken (int index, String text, int line, int charBegin, int charEnd) {
     m_index = index;
    m_text = text;
    m_line = line;
    m_charBegin = charBegin;
    m_charEnd = charEnd;
  }

  public String toString() {
    return "Text   : "+m_text+
           "\nindex : "+m_index+
           "\nline  : "+m_line+
           "\ncBeg. : "+m_charBegin+
           "\ncEnd. : "+m_charEnd;
  }
}              // End of Yytoken.java           

