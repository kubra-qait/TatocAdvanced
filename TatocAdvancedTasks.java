package tatoc;

import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class TatocAdvancedTasks {

	static WebDriver driver;
	String symbol = null;
	int id = 0;
	String name = null;
	String passkey = null;

	public void launchTatocAdvancedCourse() {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\kubraabbas\\Downloads\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("http://10.0.1.86/tatoc");
		driver.findElement(By.xpath("//a[text() = 'Advanced Course']")).click();
	}

	public void hoverMenu() {
		Actions hoverMenu2 = new Actions(driver);
		hoverMenu2.moveToElement(driver.findElement(By.className("menutitle"))).perform();
		driver.findElement(By.xpath("//span[text() = 'Go Next']")).click();
	}

	public void createConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); // register JDBC Driver
			Connection con = DriverManager.getConnection("jdbc:mysql://10.0.1.86/tatoc", "tatocuser", "tatoc01"); // open
																													// Connection
			Statement stmt = con.createStatement(); // execute statement
			ResultSet rs = stmt.executeQuery("select * from credentials");
			while (rs.next()) {
				//System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3));
			}
			rs = stmt.executeQuery("select * from identity");
			while (rs.next()) {
				//System.out.println(rs.getInt(1) + "  " + rs.getString(2));
			}
			rs = stmt.executeQuery("select id from identity where symbol = '"+symbol+"'");
			while (rs.next()) {
			id = rs.getInt(1);
			//System.out.println(id);
			}			
			rs = stmt.executeQuery("select name , passkey from credentials where id = '"+id+"'");
			while (rs.next()) {
				name = rs.getString(1);
				passkey = rs.getString(2);
				//System.out.println(name);
				//System.out.println(passkey);		
			}
		}
		catch (Exception e) {
			System.out.println(e);
		}	
		driver.findElement(By.id("name")).sendKeys(name);
		driver.findElement(By.id("passkey")).sendKeys(passkey);
		driver.findElement(By.id("submit")).click();
	}
		public void automateDatabaseTask() {
			symbol = driver.findElement(By.id("symboldisplay")).getText();
			//System.out.println(symbol);
			symbol = symbol.toLowerCase();
			//System.out.println(symbol);
	}
		public void videoPlayer() {
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("window.played = true;");
			driver.findElement(By.xpath("//a[text() = 'Proceed']")).click();
		}
		
		@SuppressWarnings("unchecked")
		public void restAssuredTask() {
			String sessionID = driver.findElement(By.id("session_id")).getText();
			String[] sessionIDText = sessionID.split(": ");
			String ID = sessionIDText[1];
			System.out.println(sessionIDText[0]);
			System.out.println("'"+ID+"'");
			RestAssured.baseURI = "http://10.0.1.86/tatoc/advanced/rest/service/token";
			RequestSpecification httpRequest = RestAssured.given();
			Response response = httpRequest.request(Method.GET, ID);
			JsonPath responseBody = response.getBody().jsonPath();
			String token=responseBody.getString("token");
			System.out.println("Response Body is:  " + responseBody);
			System.out.println("'"+token+"'");
			int statusCode = response.getStatusCode();
			System.out.println(statusCode);
			

RestAssured.baseURI ="http://10.0.1.86/tatoc/advanced/rest/service";
RequestSpecification request = RestAssured.given();
JSONObject requestParams = new JSONObject();
requestParams.put("id", ID); 
requestParams.put("signature", token);
requestParams.put("allow_access", "1");
Response res = RestAssured.given().param("id", ID).param("signature", token).param("allow_access", "1").when().post("/register").then().extract().response();
request.header("Content-Type", "application/json");

//request.body(requestParams);

//Response response1 = request.post("/register");
//Response response1 = request.request(Method.POST,"/register");
//System.out.println(response1.asString());
int status_code = res.getStatusCode();
System.out.println(status_code);

			
//			RestAssured.baseURI = "http://10.0.1.86/tatoc/advanced/rest/service";
//			RequestSpecification request = RestAssured.given();
//			HashMap<String,Object> postBody = new HashMap<String,Object>();
//			postBody.put("id", ID);
//			postBody.put("signature", token);
//			postBody.put("allow_access", "1");
//			for (String name : postBody.keySet()) {
//			    System.out.println(postBody.get(name).toString());
//			}
//			JSONObject requestParams = new JSONObject(postBody);
//			request.header("Content-Type", "application/json");
//			request.body(requestParams.toJSONString());
//			Response response1 = request.post("/register");
//			int statusCode1 = response1.getStatusCode();
//			System.out.println(statusCode1);
			driver.findElement(By.xpath("//a[text() = 'Proceed']")).click();

	}
}
