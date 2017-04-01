import java.io.*;

/* This Scanner class handles the buffer. 
 * We have simple procedural calls like getting the token to see what operation needs to be performed.
 * 
 */


public class Scanner {
	private char ch = ' ';
	public char letter;
	private double value = 0;
	public Buffer buffer;
	public int token;

	public Scanner (BufferedReader in) {
		buffer = new Buffer(in);
		token = Token.semicolon;
	} 


	public int getToken(){
		while (Character.isWhitespace(ch))
			ch = buffer.get();
		if (Character.isLetter(ch)){
			letter = ch;
			ch = buffer.get();
			token = Token.letter;
		}
		else if (Character.isDigit(ch)){
			value = getNumber();
			token = Token.number;
		} 
		else {
			switch (ch) {
			case ';' : ch = buffer.get();
			token = Token.semicolon;
			break;

			case '.' : ch = buffer.get();
			token = Token.period;
			break;

			case '+' : ch = buffer.get();
			token = Token.plus;
			break;

			case '-' : ch = buffer.get();
			token = Token.minus;
			break;

			case '*' : ch = buffer.get();
			token = Token.mult;
			break;

			case '/' : ch = buffer.get();
			token = Token.div;
			break;

			case '=' : ch = buffer.get();
			token = Token.equal;
			break;

			case '(' : ch = buffer.get();
			token = Token.lparen;
			break;

			case ')' : ch = buffer.get();
			token = Token.rparen;
			break;

			case '^' : ch = buffer.get();
			token = Token.raised;
			break;
			
			case '@' : ch = buffer.get();
			token = Token.exit;
			break;
			
			default : error ("Illegal Character: " + ch );
			break;
			}
		}
		return token;
	}


	public double number(){
		return value;
	}

	public void error (String msg){
		System.err.println(msg);
		System.exit(1);
	}

	/* Because the program has to read char-by-char, this function works by grabbing a char, converting it to a number,
	 * and adding it to the value. If it sees a period, all future values added are divided by 10 to account for decimals.
	 */

	private double getNumber(){
		boolean decimal = false;
		double value = 0;
		do{
			if(ch == '.'){
				decimal = true;
				ch = buffer.get();		
			}
			if(decimal)
				value += ((double) Character.digit(ch, 10))/10;
			else
				value = value * 10 + Character.digit(ch, 10);
			ch = buffer.get();
		}while (Character.isDigit(ch) || ch == '.');
		return value;
	} 

}
