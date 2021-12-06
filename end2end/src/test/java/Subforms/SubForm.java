package Subforms;

import java.util.Iterator;
import java.util.Set;
import org.openqa.selenium.WebDriver;

public class SubForm {

	public static void subForm(WebDriver driver, String subformlibraryid) throws InterruptedException {
		Set<String> handles = driver.getWindowHandles();
		Iterator<String> it = handles.iterator();
		// iterate through your windows
		@SuppressWarnings("unused")
		String parent = null, newwin = null;
		while (it.hasNext()) {
			parent = it.next();
			newwin = it.next();
		}
		driver.switchTo().window(newwin);

		if (subformlibraryid.equalsIgnoreCase("562")) {
			vn558.vn_558(driver); System.out.println("test");
		}

		else if (subformlibraryid.equalsIgnoreCase("564")) {
			vn564.vn_564(driver);
			
		}

	}
}
