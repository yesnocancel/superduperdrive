package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.List;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private String baseUrl;

	private static final String USERNAME = "seleniumtest";
	private static final String PASSWORD = "$eleniumpa$$word";
	private static final String FIRSTNAME = "Selenium";
	private static final String LASTNAME = "Test";
	private static final String NOTE_TITLE = "Test Note Title";
	private static final String NOTE_DESCRIPTION = "Test Note Description";
	private static final String CREDENTIAL_URL = "http://testurl";
	private static final String CREDENTIAL_USERNAME = "TestUser";
	private static final String CREDENTIAL_PASSWORD = "TestPassword";
	private static final String CREDENTIAL_KEY = "OPkyjIDDpUgnzwrWdErCpw==";

	
	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		baseUrl = "http://localhost:" + port;
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
		driver.get(baseUrl + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void getSignupPage() {
		driver.get(baseUrl + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}

	@Test
	public void signupAndLoginUserSuccess() {
		signup(driver);
		login(driver);
		Assertions.assertEquals("Home", driver.getTitle());
	}

	@Test
	public void loginFailsIfNotSignedup() {
		final String username = "hacker";
		final String password = "badpassword";

		driver.get(baseUrl + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		WebElement errorMessage = driver.findElement(By.id("containerLoginError"));

		Assertions.assertEquals("Login", driver.getTitle());
		Assertions.assertEquals("Invalid username or password", errorMessage.getText());
	}

	@Test
	public void redirectToLoginIfNotSignedIn() {
		driver.get(baseUrl + "/home");

		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void createAndReadAndDeleteNewNote() {
		signup(driver);
		login(driver);
		HomePage homePage = new HomePage(driver);

		// Create
		driver.get(baseUrl + "/home");
		homePage.createNewNote(NOTE_TITLE, NOTE_DESCRIPTION);

		// Read
		driver.get(baseUrl + "/home");
		List<String> noteTitles = homePage.getNoteTitles();
		List<String> noteDescriptions = homePage.getNoteDescriptions();
		Assertions.assertEquals(NOTE_TITLE, noteTitles.get(0));
		Assertions.assertEquals(NOTE_DESCRIPTION, noteDescriptions.get(0));

		// Delete
		homePage.deleteNote(0);
		driver.get(baseUrl + "/home");
		noteTitles = homePage.getNoteTitles();
		noteDescriptions = homePage.getNoteDescriptions();
		Assertions.assertEquals(0, noteTitles.size());
		Assertions.assertEquals(0, noteDescriptions.size());
	}

	@Test
	public void createAndReadAndDeleteNewCredential() {
		signup(driver);
		login(driver);
		HomePage homePage = new HomePage(driver);

		// Create
		driver.get(baseUrl + "/home");
		homePage.createNewCredential(CREDENTIAL_URL, CREDENTIAL_USERNAME, CREDENTIAL_PASSWORD);

		// Read
		driver.get(baseUrl + "/home");
		List<String> credentialUrls = homePage.getCredentialUrls();
		List<String> credentialUsernames = homePage.getCredentialUsernames();
		List<String> credentialKeys = homePage.getCredentialKeys();
		Assertions.assertEquals(CREDENTIAL_URL, credentialUrls.get(0));
		Assertions.assertEquals(CREDENTIAL_USERNAME, credentialUsernames.get(0));
		Assertions.assertEquals(CREDENTIAL_KEY, credentialKeys.get(0));

		// Delete
		homePage.deleteCredential(0);
		driver.get(baseUrl + "/home");
		credentialUrls = homePage.getCredentialUrls();
		credentialUsernames = homePage.getCredentialUsernames();
		credentialKeys = homePage.getCredentialKeys();
		Assertions.assertEquals(0, credentialUrls.size());
		Assertions.assertEquals(0, credentialUsernames.size());
		Assertions.assertEquals(0, credentialKeys.size());
	}

	private void signup(WebDriver driver) {
		driver.get(baseUrl + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup(FIRSTNAME, LASTNAME, USERNAME, PASSWORD);
	}

	private void login(WebDriver driver) {
		driver.get(baseUrl + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(USERNAME, PASSWORD);
	}
}
