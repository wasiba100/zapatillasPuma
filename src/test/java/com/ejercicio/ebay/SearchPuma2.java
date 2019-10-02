package com.ejercicio.ebay;

import static org.junit.Assert.assertEquals;

import java.awt.font.NumericShaper.Range;
import java.util.ArrayList;
import java.util.Collections;
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
import java.util.HashMap;

public class SearchPuma2 {
	private WebDriver driver;

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
																									// accion
		driver.findElement(By.xpath("//*[@id=\"w7\"]/div/div/ul/li[4]/a")).click();

		System.out.println("5 items Precios");

		// --------------------
		int i = 0;
		List<WebElement> precios = driver.findElements(By.className("s-item__price"));
		List<WebElement> NomProduct = driver.findElements(By.className("s-item__title"));
		List<String> ordenarAcendente = new ArrayList<>(); // prueba
		List<String> ordenarDecendente = new ArrayList<>(); // prueba
		
		
		for (WebElement preproduct : precios) {
			if (i==5) {
		//		
				break;
			}
			
			System.out.println("Producto = "+i);
			System.out.println(preproduct.getText());

			i++;
			
		}
		
		//System.out.println("jaskdhsakdsfaduys");
		
		HashMap<String, Double> listaprecioproducto = new HashMap<String, Double>();
		List<String> productosonly = new ArrayList<>();
		List<Double> preciosonly = new ArrayList<>();
		
		for (int j=0; j<5; j++) {
			WebElement preciazo = precios.get(j);
			WebElement nombreproducto = NomProduct.get(j);
			//System.out.println("El precio del producto " + nombreproducto.getText() + " es de " + preciazo.getText());
			listaprecioproducto.put(nombreproducto.getText(), Double.parseDouble(preciazo.getText().replace("S/. ", "")));
			productosonly.add(nombreproducto.getText());
			preciosonly.add(Double.parseDouble(preciazo.getText().replace("S/. ", "")));
		}
		
		Collections.sort(productosonly);
		Collections.sort(preciosonly, Collections.reverseOrder());
		
		System.out.println("a ver que imprime esto");
		for (String name: listaprecioproducto.keySet()) {
			String key = name.toString();
			String value = listaprecioproducto.get(name).toString();
			System.out.println(key + ": " + value);
		}
		

	}

	@After
	public void tearDown() {
		// driver.quit();
	}

}
