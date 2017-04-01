class Token {

	public static final int semicolon = 0;
	public static final int period    = 1;
	public static final int plus      = 2;
	public static final int minus     = 3;
	public static final int mult      = 4;
	public static final int div       = 5;
	public static final int equal     = 6;
	public static final int lparen    = 7;
	public static final int rparen    = 8;
	public static final int letter    = 9;
	public static final int number    = 10;
	public static final int raised    = 11;
	public static final int exit      = 12;

	private static String[] tokens = {
			";", ".", "+", "-", "*", 
			"/", "=", "(", ")", "letter", 
			"number", "^", "@"};

	public static String toString (int i) {
		return tokens[i];
	}

}