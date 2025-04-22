package test;

import java.io.ByteArrayInputStream;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
@Listeners({io.qameta.allure.testng.AllureTestNg.class})
@Epic("Test de l'application Sauce Demo")
@Feature("Test de Login , test de tri de produit , test d'ajout de produit et de checkout, test de déconnexion")
public class sauceDemoTest extends baseTest {

@Story("Accés au Site web et connexion avec un compte valide")	
@Severity(SeverityLevel.CRITICAL)
@Description("On accéde au site Sauce Demo et on se connecte avec les bons identifiants")
@Test(groups = "login")
public void sauceDemoLogin() {
	test = extent.createTest("SauceDemo - Login");
	try {
		test.info("Tentative de connexion avec l'utilisateur " + config.getUsername());
		
		loginpage.loginmeth(config.getUsername(), config.getPassword());
		loginpage.login();
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		
		// Attente que la page d'inventaire s'affiche
		wait.until(ExpectedConditions.and(ExpectedConditions.urlContains("inventory.html"),ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Products']"))));
		
		Allure.step("Connexion réussie avec l'utilisateur " + config.getUsername());
		Allure.addAttachment("Connexion Réussie", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
		
		test.pass("Login réussi et la page produits est visible");

	} catch (Exception e) {
		Allure.step("Échec de l'accès au site avec identifiants valides.");
		test.fail("Le test a échoué : " + e.getMessage());
	}
}

@Story("Tri des produits")
@Severity(SeverityLevel.MINOR)
@Description("Aprés l'accés au site SauceDemo on vérifie que l'option de Tri elle fonctionne")
@Test(groups = "search" , dependsOnMethods = "sauceDemoLogin")
public void sauceDemosearch() {
   test= extent.createTest("SauceDemo - Search");
	
	try {
	Allure.step("Lancement du tri des produits par prix croissant");
	
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("product_sort_container")));
	
	search.searchBox();
	
	wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("inventory_item_price")));
	
	List<Double> prices = search.getProductPrices();
	
	for (int i=0 ; i< prices.size() - 1 ; i++) {
		Assert.assertTrue(prices.get(i) <= prices.get(i + 1), "Les prix ne sont pas triés correctement : " + prices.get(i) + " > " + prices.get(i + 1));
		
	}
	// Deuxième capture après vérification
    Allure.step("Capture après vérification du tri", () -> {
        Allure.addAttachment("Screenshot", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
    });
	test.pass("La recherche a été effectuée et les prix sont correctement triés.");
	
	}catch (Exception e) {
		test.fail("Echec de la méthode du Tri ! " +e.getMessage());
	}
	Allure.step("Capture après tri");
	Allure.addAttachment("Screenshot", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
}
@Story("Ajout d'un produit et de faire le checkout de la commande")
@Severity(SeverityLevel.BLOCKER)
@Description("On ajoute un produit dans le panier et on finalise la commande")
@Test(groups = "panier", dependsOnMethods = "sauceDemosearch")
public void sauceDemoAddProduct() {
	test = extent.createTest("SauceDemo - AjoutduProduit");
	try {
		test.info("Recherche du produit!!");
	
		adp.ajoutDesProduits();
		test.pass("Les produits ont été ajoutés au panier avec succès.");
	}catch(Exception e) {
			Allure.step("Erreur lors de l'ajout du produit et de la finalisation de la commande");
		test.fail("Echec de l'ajout du produit !" + e.getMessage());
	}
}
@Story("Deconnexion aprés la commande du produit")
@Severity(SeverityLevel.MINOR)
@Description("Aprés la finalisation de la commande on pourra se déconnecter du Site Sauce Demo")
@Test( groups = "logout", dependsOnMethods = "sauceDemoAddProduct")
public void sauceDemoLogout() {
	test = extent.createTest("SauceDemo - Logout");
	try {
	logt.logout();
	Allure.addAttachment("Deconnexion Réussi", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
	test.pass("Deconnexion réussi !!");
	}catch(Exception e) {
		test.fail("Echec de la deconnexion!" + e.getMessage());
	}
}




@AfterClass

public void logout() {
	driver.quit();
}
	
}
