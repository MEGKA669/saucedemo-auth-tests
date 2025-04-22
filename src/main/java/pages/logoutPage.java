package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class logoutPage {
   
	
	private WebDriver driver;
	private WebDriverWait wait;
	
	
private void clickElement(By locator) {
	wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
}

  private By menufield = By.id("react-burger-menu-btn");
  private By logoutfield = By.id("logout_sidebar_link");
  
 public logoutPage(WebDriver driver) {
	 this.driver = driver;
	 this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
 }

 
 public void logout () {
	 clickElement(menufield);
	 By menupagefield = By.className("bm-menu");
	 Assert.assertTrue(driver.findElement(menupagefield).isDisplayed(),"le menu pour se déconnecter ne fonctionne pas ");
	 
	 clickElement(logoutfield);
	By Loginmenufield = By.className("login_logo");
	Assert.assertTrue(driver.findElement(Loginmenufield).isDisplayed(), "Erreur lors de la déconnexion");
	 
 }
}
