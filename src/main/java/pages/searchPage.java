package pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class searchPage {
	
	private WebDriver driver;
	private WebDriverWait wait;
private void clickElement(By locator) {
	wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
}
	
//localisateur 
private By menufield = By.className("product_sort_container");

//constructeur 

public searchPage(WebDriver driver) {
	this.driver = driver;
	this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
}

public void searchBox() throws InterruptedException {
	Select sort = new Select(driver.findElement(menufield));
   
    sort.selectByValue("lohi");
    
    //System.out.println("Option sélectionnée : " + sort.getFirstSelectedOption().getText());
     sort.selectByValue("lohi");
    
    wait.until(ExpectedConditions.stalenessOf(driver.findElements(By.className("inventory_item_price")).get(0)));
    
    wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("inventory_item_price")));
    
    List<Double> prices = getProductPrices();
    for (int i = 0; i < prices.size() - 1; i++) {
        if (prices.get(i) > prices.get(i + 1)) {
            throw new AssertionError("Produits pas triés correctement : " + prices.get(i) + " > " + prices.get(i + 1));
        }
    }
}

public List<Double> getProductPrices(){
	List<WebElement> elementsprices = driver.findElements(By.className("inventory_item_price"));
	List<Double> prices = new ArrayList<>();
	
	for (WebElement el : elementsprices) {
		String priceText = el.getText().replace("$", "").trim(); //exemple $7.99 --> 7.99
		prices.add(Double.parseDouble(priceText));
	}
	return prices;
}


}
