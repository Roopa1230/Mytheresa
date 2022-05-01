package testcases;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import base.BaseClass;

public class BrokenLinks extends BaseClass {

	String url = "";
	String homePage = "http://www.amazon.in";
	HttpURLConnection huc = null;
	int responseCode = 200;

	@Test
	public void broken() throws  InterruptedException, MalformedURLException {
		driver=launchBrowser("Chrome");
		Thread.sleep(5000);
		driver.manage().window().maximize();
		driver.get(homePage);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		List<WebElement> links = driver.findElements(By.tagName("a"));
		System.out.println(links.size());

		for (int i = 0; i < links.size(); i++) {
			WebElement ele = links.get(i);
			String url = ele.getAttribute("href");
			try {
				huc = (HttpURLConnection) (new URL(url).openConnection());
				Thread.sleep(2000);
				huc.connect();
				responseCode = huc.getResponseCode();
				if (responseCode >= 400) {
					System.out.println(url + " is a broken link");
				} else {
					System.out.println(url + " is a valid link");
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.getStackTrace();
			}

		}
		driver.quit();

	}

}