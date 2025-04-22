package test;
import static org.testng.Assert.assertTrue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import pages.loginPage;
import utils.user;
import utils.userDataProvider;
@Epic("Tests d'authentification")
@Feature("Login feature")
public class authentificationTest extends baseTest {
private static final Logger logger = LoggerFactory.getLogger(authentificationTest.class);
private WebDriver driver;
public loginPage loginPage;
private user users;
private userDataProvider userDataProvider;
@Story("Accés au site web SauceDemo")
@Severity(SeverityLevel.CRITICAL)
@Description("Accéder au site Https://www.SauceDemo.com pour vérifier l'accés des différents comptes utilisateurs")
//@BeforeClass
@BeforeMethod
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
	logger.info("Initialisation et ouverture du navigature Chrome et le site SauceDemo.com");
	driver = new ChromeDriver(options);
	loginPage= new loginPage(driver);
	loginPage.goTo("https://www.saucedemo.com");
	Allure.addAttachment("Accés au site SauceDemo Réussi", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
	userDataProvider udp = new userDataProvider();
	//on vérifie qu'on a bien le site sur lequel on travaille 
	
	assertTrue(driver.getCurrentUrl().contains("saucedemo"), "L'accés au site a échoué");
	
}

@Story("Connexion utilisateur")
@Severity(SeverityLevel.CRITICAL)
@Description("Vérifie que les utilisateurs peuvent se connecter avec différents jeux de données")
@Test(dataProvider = "Datausers" , dataProviderClass = userDataProvider.class)
public void loginTest(user user) {
	
    test = extent.createTest("Login Test -" + user.username);
	loginPage.loginmeth(user.getUsername(),user.getPassword());
	loginPage.login();
	logger.debug("Accés a la page de connexion visibile");
	System.out.println("🔐 Test en cours pour l'utilisateur : " + user.getUsername());

	
	switch(user.getExpected()) {
	case "Success":
		logger.info("Connexion Réussi avec le user 1");
		Assert.assertTrue(loginPage.isLoggedIn(), "✅ Le login aurait dû réussir !");
		Allure.addAttachment("Utilisateur OK", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
		test.pass("Connexion Réussi avec les JDD");
		break;
	case "locked_out":
		Assert.assertTrue(loginPage.getErrorMessage().contains("locked out"), "❌ L'utilisateur est censé être verrouillé !");
		Allure.addAttachment("Utilisateur bloqué - screenshot", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
		logger.warn("Attention Utilisateur Bloqué");
		test.warning("Attention utilisateur verouillé");
		break;
	case "not found":
		Assert.assertTrue(loginPage.getErrorMessage().contains("Username and password do not match"), "❌ Utilisateur inconnu !");
		Allure.addAttachment("Utilisateur Inconnu", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
		logger.error("Erreur Utilisateur n'éxiste pas dans la base de donnée");
		test.pass("Utilisateur inconnu");
		break;
	default:
	    Assert.fail("⚠️ Cas non géré : " + user.getExpected());
	    logger.error("ERREUR:Veuillez Contacter le Service Client");
	}
	System.out.println("✅ Test terminé pour : " + user.getUsername() + " | Résultat attendu : " + user.getExpected());

}

@Test

//@AfterClass
@AfterMethod
public void quit() {
logger.info("Fermiture du Navigateur");
driver.quit();
}
}
