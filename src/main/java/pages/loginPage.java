package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class loginPage {

	private WebDriver driver;
	private WebDriverWait wait;
	
//localisateur
	private void clickElement(By locator) {
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
	}
	
private By usernamefield = By.name("user-name");
private By passwordfield = By.name("password");
private By loginfield = By.name("login-button");
private By menufield = By.className("product_sort_container");
//constructeur 
public loginPage(WebDriver driver){
	this.driver = driver ;
	this.wait= new WebDriverWait(driver, Duration.ofSeconds(10));
}
public void loginmeth(String username , String password) {
	try {
		wait.until(ExpectedConditions.visibilityOfElementLocated(usernamefield)).sendKeys(username);
		wait.until(ExpectedConditions.visibilityOfElementLocated(passwordfield)).sendKeys(password);
	}catch (Exception e) {
		System.out.println("erreur lors de la connexion!!" +e.getMessage());
		e.printStackTrace();
	}
}
public void login () {
	clickElement(loginfield);
}
public void goTo(String url) {
	driver.get(url);
}

public boolean isLoggedIn() {
    return driver.getCurrentUrl().contains("inventory.html");
}

public String getErrorMessage() {
    return driver.findElement(By.cssSelector("h3")).getText();
}

}
