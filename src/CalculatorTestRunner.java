import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class CalculatorTestRunner {
	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(CalculatorTests.class);

		if (result.getFailures().size() == 0) {
			System.out.println("All tests have passed.");
		} else {
			for (Failure failure : result.getFailures()) {
				System.out.println(failure.toString());
			}
		}
	}
}