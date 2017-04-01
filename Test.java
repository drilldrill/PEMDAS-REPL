import java.io.*;
import java.util.HashMap;
import java.util.Map;

/*Able to perform PEMDAS with the minimal alphabet with doubles or ints.
 * User defined functions, graphing functions, and trig functions will be implemented in version 2.
 */

public class Test {
	public static void main(String args[]) {
		System.out.println("Welcome to PEMDAS REPL. Feel free to type \"help\" for a list of commands.");
		Parser parser = new Parser(new Scanner(new BufferedReader(new InputStreamReader(System.in))));
		parser.parse();
		System.out.println("Exiting program elegantly.");
	}
}