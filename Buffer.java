import java.io.BufferedReader;
import java.io.IOException;

/* The class handles the buffer for the user input.
 * It receives user input and attempts to handle any possible errors that may arise.
 * Most of the code is to handle various possibilities in which a nasty user may try to break the program. 
 */


class Buffer {
	private String line = "";
	private int position = 0;
	private BufferedReader in;
	public char variable;
	public boolean variableDetected = false; 
	public boolean empty = true;
	
	public Buffer(BufferedReader in){
		this.in = in;
	}

	/* Reading user input was an especially tricky task.
	 * The program reads in the user input as as a normal scanner input.
	 * The input is then handled by a few conditionals that deal with various input cases for variable assignment.
	 * From here, input is fed character-by-character into the scanner class.
	 * The multiple try-catches are due to how BufferedReader.readLine() requires an IOexception
	 * There was most likely a better way of going about this, but I couldn't figure it out.
	 */

	public char get(){
		position++;
		if (position >= line.length()){
			try {
				System.out.print(">");
				line = in.readLine();
			}catch (IOException e) {
				System.exit(1);
			}
			while(line.equals("help")){
				System.out.println(help());
				try{
					System.out.print(">");
					line = in.readLine( );
				}
				catch (IOException e){
					System.exit(1);
				}
			}
			if (line.equals("exit")){
				System.out.println("Program exiting.");
				System.exit(0);
			}
			//variable assignment handling and error handling
			if(line.equals(""))
				empty = true;
			else empty = false;
			if(line.length() > 1 && (line.substring(0, 2).matches("[A-Za-z]="))){
				variable = line.charAt(0);
				line = line.substring(2);
				variableDetected = true;
			}
			else if(line.length() > 2 && (line.substring(0, 3).matches("[A-Za-z] ="))){
				variable = line.charAt(0);
				line = line.substring(3);
				variableDetected = true;
			}
			else if(line.matches("[a-z]")){
				variable = line.charAt(0);
			}
			position = 0;
			line = line + ";\n";
		}
		return line.charAt(position);
	}
	
	public String help(){
		String s = "Enter any integer expression (Do not include '=') - Ex: 2 + 2^2 + 2 * 2 + (2/2) + 2\n"
				+ "Enter any variable assignment - Ex: a = 5\n"
				+ "Enter any integer expression with declared variables - Ex: a = 5 then a^a \n"
				+ "Call any declared variable to get its value\n"
				+ "Commands:\n"
				+ "help - This help dialog\n"
				+ "exit - Exit program.\n"
				+ "@ - Exit program elegantly.";
		return s;
	}
}