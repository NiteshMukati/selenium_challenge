package challenge;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class part3 {

	static WebDriver driver = null;

	@BeforeTest
	public void setDriver() {
		System.setProperty("webdriver.edge.driver", System.getProperty("user.dir") + "\\webDrivers\\msedgedriver.exe");

		driver = new EdgeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

	}

	@Test
	public void addProduct() {

		driver.get("https://www.saucedemo.com/");
		driver.manage().window().maximize();

		String userName = driver.findElement(By.xpath("//div[@id='login_credentials']")).getText().split(":")[1]
				.split("l")[0].trim();

		String passWord = driver.findElement(By.xpath("//div[@class='login_password']")).getText().split(":")[1];
		driver.findElement(By.id("user-name")).sendKeys(userName);

		driver.findElement(By.id("password")).sendKeys(passWord);
		driver.findElement(By.id("login-button")).click();

		// Grabbing all the item cards
		List<WebElement> itemCards = driver
				.findElements(By.xpath("//div[@class='inventory_list']//div[@class='inventory_item']"));

		List<Float> priceList = new ArrayList<Float>();

		// Iterating all the item cards and then grabbing their prices and adding it to
		// a list
		for (WebElement e : itemCards) {

			priceList.add(Float.parseFloat(
					e.findElement(By.cssSelector("div.inventory_item_price")).getText().split("\\$")[1].trim()));
		}

		// Getting the max price from the items list
		float addToCart = Collections.max(priceList);
		System.out.println(addToCart);
		// This loop will now match the max price with the respective card and then add
		// it to cart
		for (WebElement a : itemCards) {

			float price = Float.parseFloat(
					a.findElement(By.cssSelector("div.inventory_item_price")).getText().split("\\$")[1].trim());
			if (price == addToCart) {
				a.findElement(By.cssSelector("button[class='btn btn_primary btn_small btn_inventory']")).click();
				break;
			}
		}

		// Navigating to cart to verify whether the added items prices is max or not
		driver.findElement(By.className("shopping_cart_link")).click();
		float cartItemPrice = Float.parseFloat(
				driver.findElement(By.xpath("//div[@class='cart_item_label']//div[@class='inventory_item_price']"))
						.getText().split("\\$")[1].trim());

		// using assertion we are verifying added items price
		Assert.assertEquals(addToCart, cartItemPrice);

	}

	@AfterTest
	public void tearDown() {
		driver.quit();

	}

}
