package Subforms;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class vn558 extends SubForm {
	public static void vn_558(WebDriver driver) {
		driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
		int j = driver.findElements(By.cssSelector("span.checkmark")).size();
		System.out.println(j);
		for (int i = 0; i <= 4; i++) {
			driver.findElements(By.cssSelector("span.checkmark")).get(i).click();
		}
		WebDriverWait wait1 = new WebDriverWait(driver, 20);
		wait1.until(ExpectedConditions.elementToBeClickable(By.id("txt_PartyPhone")));
		driver.findElement(By.id("txt_PartyPhone")).sendKeys("123467895");

	}
}