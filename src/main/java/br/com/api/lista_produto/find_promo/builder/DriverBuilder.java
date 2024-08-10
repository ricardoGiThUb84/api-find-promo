package br.com.api.lista_produto.find_promo.builder;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverBuilder {

    private static ChromeOptions options = new ChromeOptions();


    public static WebDriver getDriver(){
        return new ChromeDriver(options);
    }

    public static WebDriver getDriverHeadLess(){
        options.addArguments("--headless");
        return new ChromeDriver(options);
    }
}
