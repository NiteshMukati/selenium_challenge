package challenge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.Assert;

public class challenege_part3 {

	public static void main(String[] args) {

		System.setProperty("webdriver.edge.driver", System.getProperty("user.dir") + "\\webDrivers\\msedgedriver.exe");

		WebDriver driver = new EdgeDriver();

		driver.get("https://www.saucedemo.com/");
		driver.manage().window().maximize();

		String userName = driver.findElement(By.xpath("//div[@id='login_credentials']")).getText().split(":")[1]
				.split("l")[0].trim();
		System.out.println(userName);

		String passWord = driver.findElement(By.xpath("//div[@class='login_password']")).getText().split(":")[1];
		System.out.println(passWord);
		driver.findElement(By.id("user-name")).sendKeys(userName);

		driver.findElement(By.id("password")).sendKeys(passWord);
		driver.findElement(By.id("login-button")).click();

		List<WebElement> itemCards = driver
				.findElements(By.xpath("//div[@class='inventory_list']//div[@class='inventory_item']"));

		List<Float> priceList = new ArrayList<Float>();
		for (WebElement e : itemCards) {

			priceList.add(Float.parseFloat(
					e.findElement(By.cssSelector("div.inventory_item_price")).getText().split("\\$")[1].trim()));
		}

		float addToCart = Collections.max(priceList);

		for (WebElement a : itemCards) {

			float price = Float.parseFloat(
					a.findElement(By.cssSelector("div.inventory_item_price")).getText().split("\\$")[1].trim());
			if (price == addToCart) {
				a.findElement(By.xpath("//button[@name='add-to-cart-sauce-labs-fleece-jacket']")).click();
				break;
			}
		}
	
	
		driver.findElement(By.className("shopping_cart_link")).click();
		float cartItemPrice = Float.parseFloat(driver.findElement(By.xpath("//div[@class='cart_item_label']//div[@class='inventory_item_price']")).getText().split("\\$")[1].trim());
		
		Assert.assertEquals(addToCart, cartItemPrice);
	
	
		driver.quit();
	
	
	
	
	
	
	
	
	
	
	
	
	
	}
}
