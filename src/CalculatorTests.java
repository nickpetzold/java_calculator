import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

public class CalculatorTests {
	@Test
	public void calculateFromInstructionsSuccess() throws InvalidInstructionsException {
		Calculator calculator = new Calculator();

		ArrayList<String> instructions1 = new ArrayList(Arrays.asList("divide 4", "multiply 2", "apply 3"));
		assertEquals(6, calculator.calculateInstructions(instructions1));

		ArrayList<String> instructions2 = new ArrayList(
				Arrays.asList("divide -4", "multiply 2", "subtract 10", "apply 3"));
		assertEquals(-23, calculator.calculateInstructions(instructions2));

		ArrayList<String> instructions3 = new ArrayList(
				Arrays.asList("divide 44", "multiply -4", "subtract -10", "add 100", "multiply 6", "apply 3"));
		assertEquals(48, calculator.calculateInstructions(instructions3));

		ArrayList<String> instructions4 = new ArrayList(
				Arrays.asList("add 100", "add 200", "add 300", "add 400", "add 500", "apply 3"));
		assertEquals(1503, calculator.calculateInstructions(instructions4));

		ArrayList<String> instructions5 = new ArrayList(Arrays.asList("add 2", "multiply 3", "apply 3"));
		assertEquals(15, calculator.calculateInstructions(instructions5));

	}

	@Test
	public void calculateApplyOnlySuccess() throws InvalidInstructionsException {
		Calculator calculator = new Calculator();

		ArrayList<String> instructions = new ArrayList(Arrays.asList("apply 3"));
		assertEquals(3, calculator.calculateInstructions(instructions));

	}

	@Test
	public void calculateWhitespaceSuccess() throws InvalidInstructionsException {
		Calculator calculator = new Calculator();

		ArrayList<String> instructions = new ArrayList(
				Arrays.asList("add 2       ", "multiply       3", "divide 6", "         apply 3"));
		assertEquals(10, calculator.calculateInstructions(instructions));

	}

	@Test
	public void calculateBlankLinesSuccess() throws InvalidInstructionsException {
		Calculator calculator = new Calculator();

		ArrayList<String> instructions = new ArrayList(Arrays.asList("add 100", "", "", "", "add 200", "", "add 300",
				"", "", "", "add 400", "", "add 500", "", "apply 3"));
		assertEquals(1503, calculator.calculateInstructions(instructions));

	}

	@Test(expected = InvalidInstructionsException.class)
	public void throwInvalidInstructionsExceptionEmptyFile() throws InvalidInstructionsException {
		Calculator calculator = new Calculator();

		ArrayList<String> instructions = new ArrayList(Arrays.asList());
		calculator.calculateInstructions(instructions);

	}

	@Test(expected = InvalidInstructionsException.class)
	public void throwInvalidInstructionsExceptionInvalidAction() throws InvalidInstructionsException {
		Calculator calculator = new Calculator();

		ArrayList<String> instructions = new ArrayList(
				Arrays.asList("add 100", "add 200", "add 300", "foo 400", "add 500", "apply 3"));
		calculator.calculateInstructions(instructions);

	}

	@Test(expected = InvalidInstructionsException.class)
	public void throwInvalidInstructionsExceptionInvalidValue() throws InvalidInstructionsException {
		Calculator calculator = new Calculator();

		ArrayList<String> instructions = new ArrayList(
				Arrays.asList("add 100", "add 200", "add 300", "foo 400", "add bar", "apply 3"));
		calculator.calculateInstructions(instructions);

	}

	@Test
	public void readFileSuccess() throws FileNotFoundException {
		Calculator calculator = new Calculator();

		ArrayList<String> instructions = new ArrayList(Arrays.asList("add 2", "multiply 5", "apply 2"));
		assertEquals(instructions, calculator.readFile("test.txt"));

	}

}
