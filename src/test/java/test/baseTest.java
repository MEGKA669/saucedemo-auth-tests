	package test;
	
	import static org.testng.Assert.assertTrue;
	
	import java.io.ByteArrayInputStream;
	import java.util.HashMap;
	import java.util.Map;
	
	import org.openqa.selenium.OutputType;
	import org.openqa.selenium.TakesScreenshot;
	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.chrome.ChromeDriver;
	import org.openqa.selenium.chrome.ChromeOptions;
	import org.testng.annotations.AfterSuite;
	import org.testng.annotations.BeforeClass;
	import org.testng.annotations.BeforeSuite;
	
	import com.aventstack.extentreports.ExtentReports;
	import com.aventstack.extentreports.ExtentTest;
	import com.aventstack.extentreports.reporter.ExtentSparkReporter;
	
	import io.github.bonigarcia.wdm.WebDriverManager;
	import io.qameta.allure.Allure;
	import io.qameta.allure.Description;
	import io.qameta.allure.Epic;
	import io.qameta.allure.Feature;
	import io.qameta.allure.Severity;
	import io.qameta.allure.SeverityLevel;
	import io.qameta.allure.Story;
	import pages.addProduct;
	import pages.loginPage;
	import pages.logoutPage;
	import pages.searchPage;
	import utils.Config;
	import utils.ReaderConfig;
	@Epic("Initialisation et login du site Sauce Demo")
	@Feature("Initialisation du navigateur , login avec des identifiants valide")
	public class baseTest {
		
		protected WebDriver driver ;
		protected loginPage loginpage;
		protected Config config;
		protected searchPage search;
		protected addProduct adp;
		protected logoutPage logt;
		protected static ExtentReports extent;
		protected static ExtentTest test;
		protected static ExtentSparkReporter htmlReporter;
	
	@BeforeSuite
	public void SetUpReporter() {
		
		//crée le fichier HTML
		htmlReporter = new ExtentSparkReporter("test.output/ExtentReport.html");
		
		extent = new ExtentReports();
		
		extent.attachReporter(htmlReporter);
		//infos facultative mais utiles dans le rapport
		extent.setSystemInfo("Projet", "SauceDemo");
		
		extent.setSystemInfo("Testeur", "AIT IDIR");
		
		extent.setSystemInfo("Navigateur", "Chrome");
	}
	@AfterSuite 
	public void fermetureReporter() {
		//génere le rapport a la fin
		extent.flush();
	}
	@Story("initialisation du navigateur et accés au site SauceDemo")
	@Severity(SeverityLevel.BLOCKER)
	@Description("Initialisation du driver pour accéder au navigateur et au site web SauceDemo")
	@BeforeClass
	public void setUp() {
		
		WebDriverManager.chromedriver().setup();
		//mettre les options google qu'on veut
		ChromeOptions options = new ChromeOptions();
		// Évite toutes les interruptions
		options.addArguments("--disable-Notification");
		
		options.addArguments("--disable-infobars");
		
		options.addArguments("--disable-extensions");
		
		options.addArguments("--start-maximized");
		
		options.addArguments("--incognito");
		//pour java 8 on désactive le gestionnaire des mdp 
		Map<String, Object> prefs = new HashMap<>();
		prefs.put("credentials_enable_service", false);
		
		prefs.put("profile.password_manager_enabled", false);
		
		options.setExperimentalOption("prefs", prefs);
		//initialiser le driver avec les options
		
		driver = new ChromeDriver(options);
		
		config = ReaderConfig.getConfig();
		
		driver.get(config.getUrl());
		//on vérifie qu'on a bien le site sur lequel on travaille 
		assertTrue(driver.getCurrentUrl().contains("saucedemo"), "L'accés au site a échoué");
	
		loginpage = new loginPage(driver);
		search = new searchPage(driver);
		adp = new addProduct(driver);
		logt = new logoutPage(driver);
		
	}
	
	
	}
