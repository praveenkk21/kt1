package mavenendtoend.end2end;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import Subforms.SubForm;

@SuppressWarnings("unused")
public class KanTest {
	static WebDriver driver;
	static String username, password, Client, intakeClient, subformname, subformfrom, site;
	static String subformlibraryid,filePath;

	@Test
	public static void browser() throws Exception {

		ReadExcelFile objExcelFile = new ReadExcelFile();
		filePath = System.getProperty("user.dir");
		System.out.println(filePath);
		Sheet MySheet = ReadExcelFile.readExcel(filePath, "Data.xlsx", "Data");

		String browsername = objExcelFile.cellValueString(0, 1, MySheet);
		System.out.println(browsername);
		ReadExcelFile.writeExcel(filePath, "Data.xlsx", "Data", 0, 2, "Done");
		username = objExcelFile.cellValueString(2, 1, MySheet);
		password = objExcelFile.cellValueString(3, 1, MySheet);
		intakeClient = objExcelFile.cellValueString(4, 1, MySheet);
		Client = objExcelFile.cellValueString(5, 1, MySheet);
		subformname = objExcelFile.cellValueString(6, 1, MySheet);
		subformlibraryid = String.valueOf(objExcelFile.cellValueInt(7, 1, MySheet));
		subformfrom = objExcelFile.cellValueString(8, 1, MySheet);
		site = objExcelFile.cellValueString(1, 1, MySheet);

		/*
		 * Below code is for get the data from properties file File file = new
		 * File("C:\\Users\\praveenkumar\\git\\repository\\end2end\\file.properties");
		 * FileInputStream fi = new FileInputStream(file); Properties fi1 = new
		 * Properties(); fi1.load(fi); String browsername = fi1.getProperty("Browser");
		 * username = fi1.getProperty("username"); password =
		 * fi1.getProperty("password"); intakeClient = fi1.getProperty("intakeClient");
		 * Client = fi1.getProperty("Client"); subformname =
		 * fi1.getProperty("subformname"); subformlibraryid =
		 * fi1.getProperty("subformlibraryid"); subformfrom =
		 * fi1.getProperty("subformfrom"); site=fi1.getProperty("site");
		 */

		if (browsername.equals("Chrome")) {
			System.out.println(browsername);
			driver = Driver.chrome();
		} else if (browsername.equals("internet")) {
			System.out.println(browsername);
			driver = Driver.internet();
		} else {
			driver = Driver.firefox();
		}

		if (site.equals("working")) {
			UserLogin.workingUserLogin(driver, username, password);
		} else if (site.equals("prod")) {
			UserLogin.prodUserLogin(driver, username, password);
		} else {
			UserLogin.netUserLogin(driver, username, password);
		}

		if (subformfrom.equals("Episodes")) {
			fromEpisode(driver, username, password, Client, subformname, subformlibraryid);
		} else {
			fromIntake(driver, username, password, intakeClient, subformname, subformlibraryid);
		}
	}

	public static void fromIntake(WebDriver driver, String username, String password, String intakeClient,
			String subformname, String subformlibraryid) throws Exception {
		driver.findElement(By.id("1")).click();
		driver.findElement(By.id("td_2")).click();
		driver.findElement(By.linkText("" + intakeClient + "")).click();
		driver.findElement(By.id("MainContent_li_SubForms")).click();
		driver.findElement(By.id("btnAddNew")).click();
		driver.findElement(By.xpath("//*[@class='search_bx searchbox']")).sendKeys("" + subformname + "");
		driver.manage().timeouts().implicitlyWait(500, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//*[@id=\"table_subform_" + subformlibraryid + "\"]/tbody/tr/td")).click();
		// String k=driver.getWindowHandle();

		SubForm.subForm(driver, subformlibraryid);

		clinicianSign();

		ClientSign.clientSignSubform(driver);

		subFormSubmitAndApprove();

	}

	public static void fromEpisode(WebDriver driver, String username, String password, String Client,
			String subformname, String subformlibraryid) throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		String url = driver.getCurrentUrl();

		String[] splitt = url.split("UI");
		String EpisodeManagement = "UI/EpisodeManagement/Episode.aspx?MenuItemID=88";
		url = splitt[0] + EpisodeManagement;
		driver.get(url);

		// wait.until(ExpectedConditions.elementToBeClickable(By.id("66")));
		// driver.findElement(By.id("66")).click();
		// driver.findElement(By.id("td_7")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("txtautoClient")));
		driver.findElement(By.id("txtautoClient")).click();
		driver.findElement(By.id("txtautoClient")).sendKeys("" + Client + "");
		Thread.sleep(2000);
		driver.findElement(By.id("txtautoClient")).sendKeys(Keys.ARROW_DOWN, Keys.ENTER);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("divClientHeaderOverlay")));
		wait.until(ExpectedConditions.elementToBeClickable(By.id("btnAddNew")));

		driver.findElement(By.xpath("//*[@id=\'btnAddNew\']")).click();
		driver.findElement(By.xpath("//*[@class='search_bx searchbox']")).sendKeys("" + subformname + "");
		driver.manage().timeouts().implicitlyWait(500, TimeUnit.SECONDS);
		WebElement sfid = driver
				.findElement(By.xpath("//*[@id=\"table_subform_" + subformlibraryid + "\"]/tbody/tr/td"));
		sfid.click();
		// sfid.sendKeys(Keys.ARROW_DOWN, Keys.ENTER);
		// //*[@id="table_subform_562"]/tbody/tr/td
		// String k=driver.getWindowHandle();//*[@id="table_subform_101"]/tbody/tr/td

		SubForm.subForm(driver, subformlibraryid);

		clinicianSign();

		ClientSign.clientSignSubform(driver);

		subFormSubmitAndApprove();
	}

	public static void clinicianSign() throws InterruptedException {
		driver.findElement(By.id("btnSave")).click();
		driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
		WebElement c_sign = driver.findElement(By.id("txt_ClinicianStaffSign"));

		if (c_sign.isEnabled()) {
			c_sign.click();
			driver.findElement(By.id("ui-id-5")).click();// sendKeys(Keys.ARROW_UP, Keys.ARROW_UP, Keys.ENTER);
		}
		driver.findElement(By.id("txt_ClinicianStaffSignDate")).click();
		driver.findElement(By.id("txt_ClinicianStaffSignDate")).sendKeys("02/12/2020");
		driver.findElement(By.id("txt_EnterTime")).click();
		driver.findElement(By.id("txt_EnterTime")).sendKeys("2");
//		WebDriverWait wait = new WebDriverWait(driver, 20);
//		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("span_sanckbar")));
		driver.findElement(By.id("btnSave")).click();
		Thread.sleep(5000);
	}

	public static void takeSnapShot(WebDriver webdriver, String fileWithPath) throws Exception {
		// Convert web driver object to TakeScreenshot
		TakesScreenshot scrShot = ((TakesScreenshot) webdriver);
		// Call getScreenshotAs method to create image file
		File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
		// Move image file to new destination
		File DestFile = new File(fileWithPath);
		// Copy file at destination
		FileUtils.copyFile(SrcFile, DestFile);
	}

	public static void subFormSubmitAndApprove() throws Exception {
		
		driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(driver, 50);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("btnSubmit")));
		driver.findElement(By.id("btnSubmit")).click();
		ReadExcelFile.writeExcel(filePath, "Data.xlsx", "Data", 6, 2, "Done");
		driver.switchTo().alert().accept();
		Thread.sleep(2000);
		//wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id=\"btnApprove\"]")));
		try
		{wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@id,'btnApprove')]")));
		driver.findElement(By.xpath("//*[contains(@id,'btnApprove')]")).click();
		driver.switchTo().alert().accept();
		}
		catch(Exception e) 
		{e.getMessage();}
		finally
		{wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@id,'btnApprove')]")));
		driver.findElement(By.xpath("//*[contains(@id,'btnApprove')]")).click();
		driver.switchTo().alert().accept();}
		//driver.quit();
		//driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
		//wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@id,'btnPrint')]")));
		//driver.findElement(By.id("btnPrint")).click();
		//driver.findElement(By.id("download")).click();
		takeSnapShot(driver, "C:\\Users\\praveenkumar\\Downloads\\MyAutomation\\selenium-server-3.141.59\\Screnshots\\1.jpg");
	}

	
	
}