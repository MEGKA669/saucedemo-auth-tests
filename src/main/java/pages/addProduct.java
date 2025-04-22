package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class addProduct {
	private WebDriver driver;
	private WebDriverWait wait;
	
	 private void clickElement(By locator) {
		 wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
	 }

	 private By inventairefield = By.className("inventory_list");
	 
	 private By itemfield = By.className("inventory_item_name");
	 
	 private By buttonfield = By.name("add-to-cart-sauce-labs-backpack");
	 
	 private By buttonremovefield = By.name("remove-sauce-labs-backpack");
	 
	 private By panierfield = By.className("shopping_cart_link");
	 
	 private By buttoncontinueshopingfield = By.name("continue-shopping");
	 
	 private By checkoutbuttonfield = By.name("checkout");
	 
	 private By firstnamefield = By.name("firstName");
	 
	 private By lastnamefield = By.name("lastName");
	 
	 private By cpfield = By.name("postalCode");
	 
	 private By continuefield = By.name("continue");
	 
	 private By checkoutOverviewfield = By.className("checkout_summary_container");
	 
	 private By finishfield = By.name("finish");
	 
	 private By checkoutCompletefield = By.className("checkout_complete_container");
	 
	 private By backToHomefield = By.name("back-to-products");
	 
public addProduct(WebDriver driver) {
	this.driver = driver ;
	this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));	
}
public void ajoutDesProduits() {
	
	//1-Vérifier la liste des produits est bien affiché
	wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(inventairefield));
	Assert.assertTrue(driver.findElement(inventairefield).isDisplayed(), "La liste des produits n'est pas affiché");
	
	clickElement(buttonfield); // ajout du sac au panier 
	
	clickElement(panierfield);
	//Vérifier que le produit est bien ajouté au panier 
	By produitDansPanier = By.xpath("//div[@class='inventory_item_name' and text()='Sauce Labs Backpack']");
	wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(produitDansPanier));
	Assert.assertTrue(driver.findElement(produitDansPanier).isDisplayed(), "Le produit n'est pas ajouté dans le panier");
	
	clickElement(checkoutbuttonfield);
	
	driver.findElement(firstnamefield).sendKeys("AIT IDIR");
	driver.findElement(lastnamefield).sendKeys("Ahcene");
	driver.findElement(cpfield).sendKeys("93170");
	
	clickElement(continuefield);
	//vérifier le résumé de la commande
	wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(checkoutOverviewfield));
	Assert.assertTrue(driver.findElement(checkoutOverviewfield).isDisplayed(), "Le résumé de la commande n'est pas affiché");
	clickElement(finishfield);
	
	
	//Vérifier si la commande est compléte
	wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(checkoutCompletefield));
	//celle-la c'est juste une assertion qui me permet de savoir si la page HTML de checkoutComplete est bien visibile 
	Assert.assertTrue(driver.findElement(checkoutCompletefield).isDisplayed(), "La commande n'est pas complété avec succés");
	
	//maintenant on va faire une assertion qui va me confirmer que le bon message aprés la finalisation de la commande est affiché a l'utilisateur 
	By messageTitre = By.className("complete-header");
	wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(messageTitre));
	String titre = driver.findElement(messageTitre).getText();
	Assert.assertEquals(titre, "Thank you for your order!", "Le titre de confirmation n'est pas correct");
	
	By messageTexte = By.className("complete-text");
	String message = driver.findElement(messageTexte).getText();
	Assert.assertEquals(message, "Your order has been dispatched, and will arrive just as fast as the pony can get there!", "La description de confirmation est incorrecte");
	clickElement(backToHomefield);
	
	Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"), "Echec lors du retour a la page d'acceuil");
	
}
	 
	 
}
