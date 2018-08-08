package testNG_retryFailedTestCases;

import org.testng.Assert;
import org.testng.annotations.Test;

public class RetryAnalyzer_With_IAnnotationTransformer {
	@Test
	public void Test1()
	{
		Assert.assertEquals(false, true);
	}

	@Test
	public void Test2()
	{
		Assert.assertEquals(false, true);
	}
}
