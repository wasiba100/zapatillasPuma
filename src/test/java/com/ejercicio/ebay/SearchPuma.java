package com.ejercicio.ebay;

import static org.junit.Assert.assertEquals;

import java.awt.font.NumericShaper.Range;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.w3c.dom.css.Counter;

public class SearchPuma {
	private WebDriver driver;
	
	private List<Product> products = new ArrayList<Product>();

	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "./src/test/resources/ChromeDriver/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().getSize();
		driver.get("https://www.ebay.com/");
	}

	@Test
	public void testEbayPage() {

		Select selectcategoria = new Select(driver.findElement(By.id("gh-cat")));
		selectcategoria.selectByValue("11450");

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		WebElement searchbox = driver.findElement(By.name("_nkw"));
		searchbox.clear();
		searchbox.sendKeys("Puma");
		searchbox.submit();

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		assertEquals("Puma | eBay", driver.getTitle());

		WebElement selechekbox = driver
				.findElement(By.xpath("//*[@id=\"x-refine__group_1__0\"]/ul/li[5]/div/a/div/input"));
		selechekbox.click();
		String resultadobusqueda = driver
				.findElement(By.xpath("//*[@id=\"mainContent\"]/div[1]/div/div[2]/div/div[1]/h1")).getText();
		System.out.println(resultadobusqueda);

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		Actions hover = new Actions(driver);
		hover.moveToElement(driver.findElement(By.xpath("//*[@id=\"w7\"]/button/div"))).perform();// perform de la
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);																						// accion
		driver.findElement(By.xpath("//*[@id=\"w7\"]/div/div/ul/li[4]/a")).click();


		
		List<WebElement> products_html = driver.findElements(
			By.className("s-item__wrapper")
		).subList(0, 5); // Throws IndexOutOfBounds
		
		System.out.println("obtengo elementos consultados "+products_html.size());
		
		for (WebElement elm: products_html) {
			this.products.add(new Product(
				elm.findElements(By.className("s-item__title")).get(0).getText(),
				Float.parseFloat(elm.findElements(By.className("s-item__price")).get(0).getText().replace("S/. ", ""))
			));
		}
		
		Comparator<Product> compareByPrice = new Comparator<Product>() {
		    @Override
		    public int compare(Product p1, Product p2) {
		        return Float.compare(p1.getPrice(), p2.getPrice());
		    }
		};
		
		Comparator<Product> compareByName = new Comparator<Product>() {
		    @Override
		    public int compare(Product p1, Product p2) {
		        return p1.getName().compareTo(p2.getName());
		    }
		};
		// this.products;
		List<Product> productsByName = new ArrayList<Product>(this.products),
		productsByPrice = new ArrayList<Product>(this.products),
		productsByPriceReversed = new ArrayList<Product>(this.products);
		Collections.sort(productsByName, compareByName);
		Collections.sort(productsByPrice, compareByPrice);
		Collections.sort(productsByPriceReversed, compareByPrice.reversed());
		
		System.out.println("Ordenar por nombre");
		
		for (Product p: productsByName) {
			System.out.println(p);
		}
		
		System.out.println("Ordenar por precio");
		
		for (Product p: productsByPriceReversed) {
			System.out.println(p);
		}
		


	}

	@After
	public void tearDown() {
		 driver.quit();
	}

}
