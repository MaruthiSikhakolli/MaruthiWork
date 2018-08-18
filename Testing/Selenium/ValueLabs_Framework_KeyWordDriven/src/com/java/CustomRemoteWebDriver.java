package com.java;
import org.openqa.selenium.remote.*;
import org.openqa.selenium.*;
import java.net.*;


public class CustomRemoteWebDriver extends RemoteWebDriver implements TakesScreenshot {
	public CustomRemoteWebDriver(URL url, DesiredCapabilities dc) {
		super(url, dc);
	}

	public <X> X getScreenshotAs(OutputType<X> target)
			throws WebDriverException {
		if ((Boolean) getCapabilities().getCapability(CapabilityType.TAKES_SCREENSHOT)) {
			String base64Str = execute(DriverCommand.SCREENSHOT).getValue().toString();
			return target.convertFromBase64Png(base64Str);
		}
		return null;
	}

}
