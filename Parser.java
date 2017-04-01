import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/* This class deals with the parsing of user input.
 * It recursively checks from
 * <Expression> -> <Terms> | # | epsilon 
 * <Terms> -> <MultDiv> + <MultDiv> | <MultDiv> - <MultDiv> | <MultDiv>
 * <MultDiv> -> <Raise> * <Raise> | <Raise> / <Raise> | <Raise>
 * <Raise> -> <Finish> ^ # | <Finish>
 * <Finish> - > # | ( <Expression> )
 */

public class Parser {
	
	private Scanner in;
	boolean show = true;
	boolean negative = false;
	Map<Character,BigDecimal> variables = new HashMap();
	boolean initialization = true;
	
	public Parser (Scanner input){
		in = input;
	}
	
	public void parse(){
		in.getToken();
		expression();
	}
	
	/* We're parsing continuously until we reach a . character in the tokens.
	 * Each of these functions follow a similar format.
	 * They call the next function in the series. 
	 * Once we get to the last one, we perform a check on whether or not we're looking at a number or a '('.
	 * The number then goes on through a variety of operations based on user input and is returned at the end.
	 * Furthermore, we save the token at every function stage as the token in the scanner will change overtime.
	 * If there's an invalid input, the program attempts to destroy everything and waits for the next user input.
	 * More specifics are within the functions themselves.
	 * 
	 * How I went about saving variables was creating a Map<Char,Double> in this class and if the program ran into a variable,
	 * it would check if the variable had been initialized, and if so, the variable would be treated as its double value.
	 * Everything is treated as a BigDecimal because this method of parsing char-by-char leads to IEEE floating point representation errors.
	 */
	private void expression(){
		while (in.token != Token.exit){
			double value = terms();
			if(in.buffer.variableDetected && initialization == true){
				variables.put(in.buffer.variable, new BigDecimal(value));
				in.buffer.variableDetected = false;
			}
			else
				if(initialization && !in.buffer.empty)
					System.out.println("= " + value);
			initialization = true;
			in.getToken(); 			//This command flushes the end of input marker ";" which allows for new expressions to be taken in.
		}
	}
	
	private double terms(){
		double value = multdiv();
		while(in.token == Token.plus || in.token == Token.minus){
			int saved = in.token;
			in.getToken();			//Flushing the + or - token.
			if(saved == Token.plus) value += multdiv();
			else if(saved == Token.minus) value -= multdiv();
		}
		return value;
	}
	
	private double multdiv(){
		double value = raise();
		while(in.token == Token.mult || in.token == Token.div){
			int saved = in.token;
			in.getToken(); 			//Flushing the * or / token.
			if(saved == Token.mult) value *= raise();
			else if(saved == Token.div) value /= raise();
		}
		return value;
	}
	
	private double raise(){
		double value = finish();
		while(in.token == Token.raised){
			in.getToken(); 			//Flushing the ^ token.
			value = Math.pow(value, finish());
		}
		return value;
	}
	
	private double finish(){
		double value = 0;
		if(in.token == Token.number){   //If we see a number, we send that number backwards through all the functions we've seen.	
			if(negative){			   	//This accounts for negative numbers by checking if it has seen a '-'
				value = -(in.number());	//If it has, the number we get next is negative.
				negative = false;
			}
			else
				value = in.number();
			in.getToken();
		}
		else if(in.token == Token.period){
			value = in.number();
			in.getToken();
		}
		else if(in.token == Token.lparen){
			in.getToken(); 				//Immediately flushing '('
			value = terms(); 			//If we see a '(', we perform another terms() call and repeat the previous steps to get a number.
			if(in.token != Token.rparen) 
				in.error("Invalid Expression. No ')' detected.");	//Checks for ')' that follows '('
			in.getToken(); 											//Flushing the ')' token.
		}
		else if(in.token == Token.minus){
			in.getToken();					
			if(negative)
				in.error("More than one '-' detected in succession.");
			negative = true;
			value = terms();
		}
		else if(in.token == Token.letter){
			if(variables.containsKey(in.letter)){
				value = variables.get(in.letter).doubleValue();
				initialization = true;
			}
			else{
				System.out.println(in.letter + " not initialized");
				initialization = false;
				value = 0;
			}
			in.getToken();
		}
		else if(in.token == Token.semicolon){
			value = 0;
		}
		else
			in.error("Bad character or commmand detected"); //Exits the program upon bad input.
		return value;
	}
	
	
}
