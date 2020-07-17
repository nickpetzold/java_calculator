import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Calculator {

	private static List<String> ACTIONS = Arrays.asList("add", "multiply", "subtract", "divide", "apply");

	public static ArrayList<String> readFile(String filePath) throws FileNotFoundException {
		ArrayList<String> instructions = new ArrayList<String>();

		Scanner s = new Scanner(new File(filePath));

		while (s.hasNextLine()) {
			String line = s.nextLine();
			instructions.add(line);
		}

		s.close();

		return instructions;
	}

	private static ArrayList<InstructionPair<String, Integer>> parseInstructions(ArrayList<String> instructions)
			throws InvalidInstructionsException {

		if (instructions.size() == 0) {
			throw new InvalidInstructionsException("No instructions provided.");
		}

		ArrayList<InstructionPair<String, Integer>> parsedInstructions = new ArrayList<InstructionPair<String, Integer>>();

		for (int i = 0; i < instructions.size(); i++) {
			try {
				String elem = instructions.get(i).trim();

				if (elem.equals("")) {
					continue;
				}

				String[] components = elem.split("\\s+");

				String action = components[0].toLowerCase();
				Integer value = Integer.parseInt(components[1]);

				if (ACTIONS.contains(action) == false) {
					throw new InvalidInstructionsException("Invalid action provided in instructions: " + action);
				}

				if (i == instructions.size() - 1 && action.equals("apply") == false) {
					throw new InvalidInstructionsException("Final action must be 'apply'");
				}

				InstructionPair<String, Integer> iP = new InstructionPair(action, value);

				parsedInstructions.add(iP);

			} catch (Exception e) {

				throw new InvalidInstructionsException(e.getMessage());

			}
		}

		return parsedInstructions;
	}

	private static int add(int a, int b) {
		return a + b;
	}

	private static int subtract(int a, int b) {
		return a - b;
	}

	private static int divide(int a, int b) {
		return a / b;
	}

	private static int multiply(int a, int b) {
		return a * b;
	}

	private static int calculate(ArrayList<InstructionPair<String, Integer>> parsedInstructions) {

		int result = 0;
		int cur_val = 0;
		int next_val = 0;

		String action;

		for (int i = 0; i < parsedInstructions.size(); i++) {
			action = parsedInstructions.get(i).action;

			if (i == 0) {
				cur_val = parsedInstructions.get(i).value;
			} else {
				cur_val = result;
			}

			if (action.equals("apply")) {
				result = cur_val;
				break;
			}

			next_val = parsedInstructions.get(i + 1).value;

			try {

				Class c = Class.forName("Calculator");
				Class[] parameterType = { int.class, int.class };
				Method method = c.getDeclaredMethod(action, parameterType);
				result = (int) method.invoke(c, cur_val, next_val);

			} catch (Exception e) {

				e.printStackTrace();

			}

		}
		return result;
	}

	public static int calculateInstructions(ArrayList<String> instructions) throws InvalidInstructionsException {

		ArrayList<InstructionPair<String, Integer>> parsedInstructions = parseInstructions(instructions);
		return calculate(parsedInstructions);

	}

	public static void main(String[] args) {

		try {

			ArrayList<String> instructions = readFile(args[0]);
			int result = calculateInstructions(instructions);
			System.out.println(result);

		} catch (FileNotFoundException | InvalidInstructionsException e) {

			e.printStackTrace();

		}

	}

}

class InvalidInstructionsException extends Exception {
	public InvalidInstructionsException(String errMsg) {
		super(errMsg);
	}
}

class InstructionPair<String, Integer> {
	public final String action;
	public final Integer value;

	public InstructionPair(String action, Integer value) {
		this.action = action;
		this.value = value;
	}
}
