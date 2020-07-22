package mavenendtoend.end2end;

import java.util.Iterator;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

public class ClientSign {
	static void clientSignSubform(WebDriver driver) throws InterruptedException {
		// Client sign
		driver.findElement(By.id("btnClientSign")).click();
		Set<String> handles1 = driver.getWindowHandles();

		Iterator<String> it1 = handles1.iterator();
		// iterate through your windows
		@SuppressWarnings("unused")
		String parent1 = null, newwin1 = null, clientsignwin = null;
		while (it1.hasNext()) {
			parent1 = it1.next();
			newwin1 = it1.next();
			clientsignwin = it1.next();
		}
		driver.switchTo().window(clientsignwin);
		WebElement element = driver.findElement(By.id("ctlSignature"));
		Actions builder = new Actions(driver);
		Action drawAction = builder.moveToElement(element, -100, 0) // start points x axis and y axis.
				.clickAndHold().moveByOffset(10, 30).moveByOffset(40, 35).moveByOffset(90, 30).moveByOffset(20, 35)
				.moveByOffset(35, 20).moveByOffset(-30, -90).moveByOffset(-35, -40).moveByOffset(-30, -10)
				.moveByOffset(-100, 0).moveByOffset(-20, 35).moveByOffset(-2, 45).moveByOffset(-2, 10).release()
				.moveToElement(element, -110, 30).clickAndHold().moveByOffset(10, 35).moveByOffset(10, 30)
				.moveByOffset(10, 35).moveByOffset(15, 20).moveByOffset(-10, -10).moveByOffset(-35, -40)
				.moveByOffset(-30, -10).moveByOffset(-10, 0).moveByOffset(-20, 35).moveByOffset(-2, 45)
				.moveByOffset(-2, 10).release().moveToElement(element, -200, 30).clickAndHold().moveByOffset(200, -180)
				.moveByOffset(-10, 0).moveByOffset(-20, 35).moveByOffset(-2, 45).moveByOffset(-2, 10)
				.moveByOffset(10, 35).moveByOffset(10, 30).moveByOffset(10, 35).moveByOffset(15, 20).release().build();
		drawAction.perform();
		System.out.println(element.getSize().getWidth());
		System.out.println(element.getSize().getHeight());
		driver.findElement(By.id("btn_SaveSignature")).click();
		Thread.sleep(2000);
		driver.switchTo().alert().accept();
		//driver.close();
		Thread.sleep(2000);
		driver.switchTo().window(newwin1);
		Thread.sleep(2000);
		//driver.navigate().refresh();
	}

}
