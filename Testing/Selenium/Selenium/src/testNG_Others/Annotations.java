package testNG_Others;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Annotations {
	@Test
	public void TestNGTestCase() {

	}

	@BeforeMethod
	public void TestNG_BeforeMethod() {

	}

	@AfterMethod
	public void TestNG_AfterMethod() {

	}

	@BeforeClass
	public void TestNG_BeforeClass() {

	}

	@AfterClass
	public void TestNG_AfterClass() {

	}

	@BeforeSuite
	public void TestNG_BeforeSuite() {

	}

	@AfterSuite
	public void TestNG_AfterSuite() {

	}

	@BeforeTest
	public void TestNG_BeforeTest() {

	}

	@AfterTest
	public void TestNG_AfterTest() {

	}
}
