package symbol_table;


class Type {

    // ************ Static Constant Declarations *******************

    // ************ Kind codes *******************

    private static final int variableKind = 0;
    private static final int valueKind = 1;
    private static final int arrayKind = 2;

    // ************ Type codes *******************

    private static final int unknownType = -1;

    private static final int intType = 0;
    private static final int charType = 1;
    private static final int boolType = 2;
    private static final int stringType = 3;
    private static final int voidType = 4;
    private static final int floatType = 5;

    // ************ Builtin Types *******************

    public static final Type errorType = new Type(unknownType);

    public static final Type intValue = new Type(intType);
    public static final Type charValue = new Type(charType);
    public static final Type boolValue = new Type(boolType);
    public static final Type stringValue = new Type(stringType);
    public static final Type voidValue = new Type(voidType);
    public static final Type floatValue = new Type(floatType);

    public static final Type intVar = new Type(intType, variableKind);
    public static final Type charVar = new Type(charType, variableKind);
    public static final Type boolVar = new Type(boolType, variableKind);
    public static final Type stringVar = new Type(stringType, variableKind);
    public static final Type floatVar = new Type(floatType, variableKind);

    // ************ Instance Member Declarations *******************

    private int type_ = intType;
    private int kind_ = valueKind;
    private int arraySize_ = 0;

    public Type(int t) {
	if (isValidType(t)) {
            type_ = t;
	    kind_ = valueKind;
        } else {
	    System.err.println(t + " is an illegal type code");
	    System.exit(1);
        }
    }
    public Type(int t, int k) {
	this(t);
	if (isValidKind(k)) {
	    kind_ = k;
	} else {
	    System.err.println(t + " or "+ k + " is an illegal kind");
	    System.exit(1);
        }
    }
    public Type(int t, int k, String arraySize) {
	this(t);
	if (k != Type.arrayKind) {
	    System.err.println("The second argument must be an array kind in constructor for Type taking three arguments");
	    k = Type.arrayKind;
	}
	kind_ = k;
	try {
	    arraySize_ = Integer.parseInt(arraySize);
	} catch(NumberFormatException e) {
	    // Should not happen
	    System.err.println("The third argument must be a string representing an integer ");
	}
    }
    public boolean isIntType() {
	return this.type_ == intType;
    }
    public boolean isFloatType() {
	return this.type_ == floatType;
    }
    public boolean isCharType() {
	return this.type_ == charType;
    }
    public boolean isBoolType() {
	return this.type_ == boolType;
    }
    public boolean isStringType() {
	return this.type_ == stringType;
    }
    public boolean isVoidType() {
	return this.type_ == voidType;
    }
    public boolean isNumericType() {
	return (isIntType() || isCharType() || isFloatType());
    }
    public boolean isErrorType() {
	return this.type_ == unknownType;
    }
    public boolean isSameType(Type t) {
	return this.type_ == t.type_;
    }
    public boolean equals(Type t) { 
        return this.type_ == t.type_ && this.kind_ == t.kind_;
    }
    public boolean isArrayKind() {
	return kind_ == Type.arrayKind;
    }
    public boolean isVariableKind() {
	return kind_ == Type.variableKind;
    }
    public boolean isValueKind() {
	return kind_ == Type.valueKind;
    }
    public Type makeValueType() {
	if (kind_ == Type.valueKind) {
	    return this;
	}
        switch(type_){
	case intType:
	    return Type.intValue;
	case charType:
	    return Type.charValue;
	case boolType:
	    return Type.boolValue;
	case floatType:
	    return Type.floatValue;
	case stringType:
	    return Type.stringValue;
	case voidType:
	    return Type.voidValue;
	case unknownType:
	    return Type.errorType;
	default:
	    System.out.println("Illegal Type `"
			       + type_ + "' in `Type::makeValueType'");
	    System.exit(1);
	    return null;
        }
    }
    public Type makeVariableType() {
	if (kind_ == Type.variableKind) {
	    return this;
	}
        switch(type_){
	case intType:
	    return Type.intVar;
	case charType:
	    return Type.charVar;
	case boolType:
	    return Type.boolVar;
	case floatType:
	    return Type.floatVar;
	case stringType:
	    return Type.stringVar;
	case voidType:
	    return Type.voidValue; // cannot have a void variable
	case unknownType:
	    return Type.errorType;
	default:
	    System.out.println("Illegal Type `"
			       + type_ + "' in `Type::makeVariableType'");
	    System.exit(1);
	    return null;
        }
    }
    public Type makeArrayType(int size) {
	return new Type(type_, arrayKind, size);
    }
    public String toString() {
        switch(type_){
	case intType:
	    return ("int");
	case charType:
	    return ("char");
	case boolType:
	    return ("bool");
	case floatType:
	    return ("float");
	case stringType:
	    return ("string");
	case voidType:
	    return ("void");
	case unknownType:
	    return ("error");
	default:
	    System.out.println("Illegal Type `"
			       + type_ + "' in `Type::toString'");
	    System.exit(1);
	    return null;
        }
    }
    public int getType() {
        return type_;
    }
    public int getArraySize() {
	return arraySize_;
    }
    protected boolean isValidType(int t) {
	return (t == intType || t == boolType || t == charType
		|| t == voidType || t == floatType || t == stringType
		|| t == unknownType);
    }
    protected boolean isValidKind(int k) {
	// these are the valid kinds 
	return (k == variableKind || k == valueKind || k == arrayKind);
    }
    protected Type(int t, int k, int arraySize) {
	this(t, k);
	arraySize_ = arraySize;
    }
}              // End of class Type            
  
