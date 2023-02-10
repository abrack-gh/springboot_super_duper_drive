package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.time.Duration;
import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {
	private static String firstName = "checkfirstName";
	private static String lastName = "checklastName";
	private static String username = "user";
	private static String password = "password";
	private static String notetitle = "New Note Title";
	private static String notedescription = "New Note Description";
	private static String credURL = "google.com";

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void getSignupPage() {
		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}

	@Test
	public void getUnauthorizedHomePage() {
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void getUnauthorizedResultPage() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		driver.get("http://localhost:" + this.port + "/result");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("login")));
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void signupTestAndLogin() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		// signup
		driver.get("http://localhost:" + this.port + "/signup");
		WebElement inputfirstName = driver.findElement(By.id("inputfirstName"));
		inputfirstName.sendKeys(firstName);
		WebElement inputlastName = driver.findElement(By.id("inputlastName"));
		inputlastName.sendKeys(lastName);
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.sendKeys(username);
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.sendKeys(password);
		WebElement signUpButton = driver.findElement(By.id("signup"));
		signUpButton.click();

		driver.get("http://localhost:" + this.port + "/login");
		inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.sendKeys(username);
		inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.sendKeys(password);
		WebElement loginButton = driver.findElement(By.id("login"));
		loginButton.click();
		Assertions.assertEquals("Home", driver.getTitle());

		//Sign out
		WebElement logoutButton = driver.findElement(By.id("logout"));
		logoutButton.click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("login")));
		Assertions.assertEquals("Login", driver.getTitle());

		//attempt to access Home page...

		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void createNote() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		//login
		driver.get("http://localhost:" + this.port + "/login");
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.sendKeys(username);
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.sendKeys(password);
		WebElement loginButton = driver.findElement(By.id("login"));
		loginButton.click();
		Assertions.assertEquals("Home", driver.getTitle());

		WebElement notesTab = driver.findElement(By.id("nav-notes-tab"));
		jse.executeScript("arguments[0].click()", notesTab);
		wait.withTimeout(Duration.ofSeconds(30));
		WebElement newNote = driver.findElement(By.id("newnote"));
		wait.until(ExpectedConditions.elementToBeClickable(newNote)).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title"))).sendKeys(notetitle);
		WebElement notedescription = driver.findElement(By.id("note-description"));
		notedescription.sendKeys((CharSequence) notedescription);
		WebElement savechanges = driver.findElement(By.id("save-changes"));
		savechanges.click();
		Assertions.assertEquals("Result", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/home");
		notesTab = driver.findElement(By.id("nav-notes-tab"));
		jse.executeScript("arguments[0].click()", notesTab);
		WebElement notesTable = driver.findElement(By.id("userTable"));
		List<WebElement> notesList = notesTable.findElements(By.tagName("th"));
		boolean created = false;
		for (int i = 0; i < notesList.size(); i++) {
			WebElement element = notesList.get(i);
			if (element.getAttribute("innerHTML").equals(notetitle)) {
				created = true;
				break;
			}
		}
		Assertions.assertTrue(created);

	}

	private void deleteNote() {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		WebDriverWait delWait = new WebDriverWait(driver, 5);
		driver.get("http://localhost:" + this.port + "/login");
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.sendKeys(username);
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.sendKeys(password);
		WebElement loginButton = driver.findElement(By.id("login"));
		loginButton.click();
		Assertions.assertEquals("Home", driver.getTitle());

		WebElement noteTab = driver.findElement(By.id("nav-notes-tab"));
		jse.executeScript("arguments[0].click()", noteTab);
		WebElement notesTable = driver.findElement(By.id("userTable"));
		List<WebElement> notesList = notesTable.findElements(By.tagName("td"));
		WebElement deleteElement = null;
		for (int i = 0; i < notesList.size(); i++) {
			WebElement element = notesList.get(i);
			deleteElement = element.findElement(By.name("delete"));
			if (deleteElement != null) {
				break;
			}
			delWait.until(ExpectedConditions.elementToBeClickable(deleteElement)).click();
			Assertions.assertEquals("Result", driver.getTitle());
		}
	}
	@Test
	public void CredentialCreation(){
		WebDriverWait credWait = new WebDriverWait(driver, 5);
		JavascriptExecutor js_ex = (JavascriptExecutor) driver;

		driver.get("http://localhost:" + this.port + "/login");
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.sendKeys(username);
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.sendKeys(password);
		WebElement loginButton = driver.findElement(By.id("login"));
		loginButton.click();
		Assertions.assertEquals("Home", driver.getTitle());

		WebElement credTab = driver.findElement(By.id("nav-credentials-tab"));
		js_ex.executeScript("arguments[0].click()", credTab);
		credWait.withTimeout(Duration.ofSeconds(30));
		WebElement newCred = driver.findElement(By.id("newcred"));
		credWait.until(ExpectedConditions.elementToBeClickable(newCred)).click();
		credWait.until(ExpectedConditions.elementToBeClickable(By.id("credential-url"))).sendKeys(credURL);
		WebElement credUsername = driver.findElement(By.id("credential-username"));
		credUsername.sendKeys(username);
		WebElement credPassword = driver.findElement(By.id("credential-password"));
		credPassword.sendKeys(password);
		WebElement submit = driver.findElement(By.id("save-credential"));
		submit.click();
		Assertions.assertEquals("Result", driver.getTitle());

		driver.get("http://localhost" + this.port + "/home");
		credTab = driver.findElement(By.id("nav-credentials-tab"));
		js_ex.executeScript("arguments[0].click()", credTab);
		WebElement credsTable = driver.findElement(By.id("credentialTable"));
		List<WebElement> credsList = credsTable.findElements(By.tagName("td"));
		boolean created = false;
		for (int i=0; i < credsList.size(); i++) {
			WebElement element = credsList.get(i);
			if (element.getAttribute("innerHTML").equals(username)) {
				created = true;
				break;
			}
		}
		Assertions.assertTrue(created);
	}

	@Test
	public void credentialUpdate() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		String newCredUsername = "user1";

		driver.get("http://localhost:" + this.port + "/login");
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.sendKeys(username);
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.sendKeys(password);
		WebElement loginButton = driver.findElement(By.id("login"));
		loginButton.click();
		Assertions.assertEquals("Home", driver.getTitle());

		WebElement credTab = driver.findElement(By.id("nav-credentials-tab"));
		jse.executeScript("arguments[0].click()", credTab);
		WebElement credsTable = driver.findElement(By.id("credentialTable"));
		List<WebElement> credsList = credsTable.findElements(By.tagName("td"));
		WebElement editElement = null;
		for (int i = 0; i < credsList.size(); i++) {
			WebElement element = credsList.get(i);
			editElement = element.findElement(By.name("editCred"));
			if (editElement != null) {
				break;
			}
		}
		wait.until(ExpectedConditions.elementToBeClickable(editElement)).click();
		WebElement credUsername = driver.findElement(By.id("credential-username"));
		wait.until(ExpectedConditions.elementToBeClickable(credUsername));
		credUsername.clear();
		credUsername.sendKeys(newCredUsername);
		WebElement savechanges = driver.findElement(By.id("save-credential"));
		savechanges.click();
		Assertions.assertEquals("Result", driver.getTitle());
	}
	@Test
	public void credentialDeletionTest() {
		WebDriverWait wait = new WebDriverWait (driver, 30);
		JavascriptExecutor jse =(JavascriptExecutor) driver;
		//login
		driver.get("http://localhost:" + this.port + "/login");
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.sendKeys(username);
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.sendKeys(password);
		WebElement loginButton = driver.findElement(By.id("login"));
		loginButton.click();
		Assertions.assertEquals("Home", driver.getTitle());

		WebElement credTab = driver.findElement(By.id("nav-credentials-tab"));
		jse.executeScript("arguments[0].click()", credTab);
		WebElement credsTable = driver.findElement(By.id("credentialTable"));
		List<WebElement> credsList = credsTable.findElements(By.tagName("td"));
		WebElement deleteElement = null;
		for (int i = 0; i < credsList.size(); i++) {
			WebElement element = credsList.get(i);
			deleteElement = element.findElement(By.name("delete"));
			if (deleteElement != null){
				break;
			}
		}
		wait.until(ExpectedConditions.elementToBeClickable(deleteElement)).click();
		Assertions.assertEquals("Result", driver.getTitle());
	}
}
