package tatoc;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

//import okhttp3.Cookie;

public class TatocTasks {

	static WebDriver driver;

	public static void launchTatoc() {
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\kubraabbas\\Downloads\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("http://10.0.1.86/tatoc");
		driver.findElement(By.xpath("//a[text() = 'Basic Course']")).click();
	}

	public static void gridGate() {
		driver.findElement(By.cssSelector("div[class = 'greenbox']")).click();
		driver.switchTo().frame("main");

	}

	public static void frameDungeon() {
		String colorofBox1 = driver.findElement(By.id("answer")).getAttribute("class");
		System.out.println(colorofBox1);
		boolean color = true;
		while (color) {
			driver.switchTo().frame("child");
			driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
			String colorofBox2 = driver.findElement(By.id("answer")).getAttribute("class");
			System.out.println(colorofBox2);
			if (colorofBox1.equals(colorofBox2)) {
				color = false;
			} else {
				driver.switchTo().parentFrame();
				driver.findElement(By.xpath("//a[text() = 'Repaint Box 2']")).click();
			}
		}
		driver.switchTo().parentFrame();
		driver.findElement(By.xpath("//a[text() = 'Proceed']")).click();
	}

	public static void dragAround() {
		WebElement dropBox = driver.findElement(By.id("dropbox"));
		WebElement dragMeBtn = driver.findElement(By.id("dragbox"));
		Actions dragDrop = new Actions(driver);
		Action action = dragDrop.dragAndDrop(dragMeBtn, dropBox).build();
		action.perform();
		driver.findElement(By.xpath("//a[text() = 'Proceed']")).click();
	}

	public static void popupWindows() {
		driver.findElement(By.xpath("//a[text() = 'Launch Popup Window']")).click();
		String mainWindow = driver.getWindowHandle();
		Set<String> s1 = driver.getWindowHandles();
		Iterator<String> i1 = s1.iterator();
		while (i1.hasNext()) {
			String childWindow = i1.next();
			if (!mainWindow.equalsIgnoreCase(childWindow)) {
				driver.switchTo().window(childWindow);
				driver.findElement(By.id("name")).sendKeys("Kubra");
				driver.findElement(By.id("submit")).click();
				driver.switchTo().window(mainWindow);
				driver.findElement(By.xpath("//a[text() = 'Proceed']")).click();
			}
		}
	}

	public static void cookieHandling() {
		driver.findElement(By.xpath("//a[text() = 'Generate Token']")).click();
		String token = driver.findElement(By.id("token")).getText();
		String value = token.substring(7);
		Cookie cookie = new Cookie("Token", value);
		driver.manage().addCookie(cookie);
		driver.findElement(By.xpath("//a[text() = 'Proceed']")).click();
	}

	/*
	 * public static void closeTatoc() { driver.close(); }
	 */
}