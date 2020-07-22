package Subforms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class vn564 {
	public static void vn_564(WebDriver driver) {
	driver.findElement(By.id("txt_PhysicianName")).sendKeys("Test");
	driver.findElement(By.id("txt_PhysicianPhone")).sendKeys("234567891");
	driver.findElement(By.id("txt_AgencyTime")).sendKeys("24");
	driver.findElement(By.id("txt_AgencyPhone")).clear();
	driver.findElement(By.id("txt_AgencyPhone")).sendKeys("234567891");
	driver.findElement(By.id("txt_Emergency")).sendKeys("Test_Emergency");
	driver.findElement(By.id("txt_EmergencyPhone")).sendKeys("234567891");

	WebDriverWait wait1 = new WebDriverWait(driver, 20);
	wait1.until(ExpectedConditions.elementToBeClickable(
			By.xpath("//*[@id=\"divCustomFormBodyContent\"]/div/section[5]/div[2]/div/div[5]/label[1]/span")));
	driver.findElement(
			By.xpath("//*[@id=\"divCustomFormBodyContent\"]/div/section[5]/div[2]/div/div[5]/label[1]/span"))
			.click();
	Assert.assertTrue(driver.findElement(By.id("chk_RiskLevel2")).isEnabled(), "RislLevel_Selected");
	}
}
