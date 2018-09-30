package testNG_Others;

import org.testng.annotations.Test;

public class Sequencing_Prioritizing {
	@Test(priority = 0)
	public void One() {
		System.out.println("This is the Test Case number One");
	}

	@Test(priority = 1, enabled = false)
	public void Two() {
		System.out.println("This is the Test Case number Two");
	}

	@Test(priority = 2, enabled = true)
	public void Three() {
		System.out.println("This is the Test Case number Three");
	}

	@Test(priority = 3)
	public void Four() {
		System.out.println("This is the Test Case number Four");
	}
}
