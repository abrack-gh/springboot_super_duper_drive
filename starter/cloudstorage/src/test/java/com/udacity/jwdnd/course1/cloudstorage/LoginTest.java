package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment =  SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginTest extends CloudStorageApplicationTests {

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

    public static final String defaultURL = "http://localhost:8449";
    public static final String homeURL = "http://localhost:8449/home";
    public static final String loginURL = "http://localhost:8449/login";
    public static final String registerURL = "http://localhost:8449/signup";

    public static final String firstName = "A";
    public static final String lastName = "B";
    public static final String USERNAME1 = "Java";
    public static final String PASSWORD1 = "java";


    @Test
    public void getLoginPage() {
        driver.get("http://localhost:" + this.port + "/login");
        Assertions.assertEquals("Login", driver.getTitle());
    }

    @Test
    public void getSignupPage() {
        driver.get(registerURL);
        Assertions.assertEquals("Signup", driver.getTitle());
    }

    @Test
    public void attemptAccess() {
        driver.get(homeURL);

        Assertions.assertEquals("Login", driver.getTitle());
    }

    @Test
    private void SignUp(String firstName, String lastName, String userName, String password) {

        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        driver.get("http://localhost:" + this.port + "/signup");
        webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));

        // Fill out credentials
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
        WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
        inputFirstName.click();
        inputFirstName.sendKeys(firstName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
        WebElement inputLastName = driver.findElement(By.id("inputLastName"));
        inputLastName.click();
        inputLastName.sendKeys(lastName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
        WebElement inputUsername = driver.findElement(By.id("inputUsername"));
        inputUsername.click();
        inputUsername.sendKeys(userName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
        WebElement inputPassword = driver.findElement(By.id("inputPassword"));
        inputPassword.click();
        inputPassword.sendKeys(password);

        // Attempt to sign up.
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
        WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
        buttonSignUp.click();

		/* Check that the sign up was successful.
		// You may have to modify the element "success-msg" and the sign-up
		// success message below depening on the rest of your code.
		*/
        Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));

        driver.get("http://localhost" + this.port + "/login");
        WebElement inputUsernameLogin = driver.findElement(By.id("inputUsername"));
        inputUsernameLogin.click();
        inputUsernameLogin.sendKeys(userName);

        WebElement inputPasswordLogin = driver.findElement(By.id("inputPassword"));
        inputPasswordLogin.click();
        inputPasswordLogin.sendKeys(password);

        WebElement submitLogin = driver.findElement(By.id("submit-button"));
        submitLogin.click();
        WebDriverWait webDriverWaitLogin = new WebDriverWait(driver, 2);

        Assertions.assertEquals("Home", driver.getTitle());

        //logout test

        WebElement logout = driver.findElement(By.id("logout-btn"));
        logout.click();
        WebDriverWait webDriverWaitLogout = new WebDriverWait(driver, 2);
        Assertions.assertEquals("Login", driver.getTitle());

    }

    //Login and create new note.

    @Test
    public WebElement RegisterLoginAndNewNote(String USERNAME1, String PASSWORD1) throws InterruptedException {
        driver.get("http://localhost" + this.port + "/signup");
        WebElement inputUsernameLogin = driver.findElement(By.id("inputUsername"));
        inputUsernameLogin.click();
        inputUsernameLogin.sendKeys(USERNAME1);

        WebElement inputPasswordLogin = driver.findElement(By.id("inputPassword"));
        inputPasswordLogin.click();
        inputPasswordLogin.sendKeys(PASSWORD1);

        WebElement submitLogin = driver.findElement(By.id("submit-button"));
        submitLogin.click();
        WebDriverWait webDriverWaitLogin = new WebDriverWait(driver, 2);

        Assertions.assertEquals("Home", driver.getTitle());

        WebElement newNote = driver.findElement(By.id("newNoteBtn"));
        newNote.click();
        WebElement noteTitle = driver.findElement(By.id("note-title"));
        noteTitle.sendKeys("testName");
        WebElement noteDescription = driver.findElement(By.id("note-description"));
        noteDescription.sendKeys("test");
        WebElement submitNote = driver.findElement(By.id("saveNote"));
        submitNote.click();
        WebDriverWait webDriverWaitSubmitNote = new WebDriverWait(driver, 5);

        //check note exists

        WebElement getNote = driver.findElement(By.id("note-title"));
        return noteTitle;
    }

    @Test
        public void editNote(){
        WebElement updateNote = driver.findElement(By.id("note-title"));
        updateNote.sendKeys("edit test");
        WebElement saveNoteButton = driver.findElement(By.id("saveNote"));
        saveNoteButton.click();
        WebDriverWait webDriverWaitEditNote = new WebDriverWait(driver, 5);
    }

    @Test
    public void deleteNote(){

    }
}


