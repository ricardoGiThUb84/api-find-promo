package br.com.api.lista_produto.find_promo.service;


import br.com.api.lista_produto.find_promo.builder.DriverBuilder;
import br.com.api.lista_produto.find_promo.model.Produto;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.NoSuchElementException;



@Service
public class Atacadao
{

    private static Integer DURATION_SEC = 5;
    private static String MAIN_URL = "https://www.atacadao.com.br/catalogo";
    private static String URL = "https://www.atacadao.com.br/catalogo?productClusterIds=138&facets=productClusterIds&sort=score_desc&page=1";

    private static String PATH_POLITICA_PRIVACIDADE = "//a[@role='button' and text()='Aceitar todos']";
    private static String PATH_ZIP_CODE = "//input[@id='zipcode']";
    private static String ZIP_CODE = "40365510";
    private static String PATH_STORE_LACATION_CONFIRMATION = "//button[text()='Confirmar']";
    private static String PATH_SELECAO_LOJA = "//button[contains(@class, 'flex') " +
            "and contains(@class, 'border') " +
            "and contains(@class, 'text-left') " +
            "and contains(@class, 'w-full') " +
            "and contains(@class, 'gap-2') " +
            "and contains(@class, 'py-2') and contains(@class, 'rounded-lg')]";
    private static String PATH_DIV_PAGINA_OFERTAS = "//div[@class='flex justify-center md:justify-flex-start']";
    private static String STORE_BUTTON = "//div[@class='flex justify-center md:justify-flex-start']" +
            "//button[@data-testid='store-button']";

    private List<Produto> listaProdutos = new ArrayList<>();


    public List<Produto> getPromocoes( ) {

        WebDriver driver = DriverBuilder.getDriver();

        driver.get(MAIN_URL);

        WebElement privacidade =
                getVisibleElements(driver,PATH_POLITICA_PRIVACIDADE);

        privacidade.click();

        WebElement cep =
                getVisibleElements(driver,PATH_ZIP_CODE);

        cep.clear();
        cep.sendKeys(ZIP_CODE);


        WebElement selecionaLoja = getClickableElements(driver,PATH_SELECAO_LOJA);

        selecionaLoja.click();

        WebElement loja = getClickableElements(driver,PATH_STORE_LACATION_CONFIRMATION);
        loja.click();

        WebElement paginas = getVisibleElements(driver, PATH_DIV_PAGINA_OFERTAS);


        Integer quantidadePaginas = 1;
        Integer maximoPaginas = paginas.findElements(By.xpath(STORE_BUTTON)).stream()
                .filter(number -> number.getText().matches("[0-9]+"))
                .mapToInt(valor -> Integer.parseInt(valor.getText())).max().getAsInt();



        while(quantidadePaginas <= maximoPaginas){

            String locate = URL.replaceAll("[0-9]+$", String.valueOf(quantidadePaginas));

            driver.navigate().to(locate);


            WebElement ulProdutos = getVisibleElements(driver,
                    "//ul[@class='c-genRlA grid grid-cols-2 xl:grid-cols-5 " +
                            "lg:grid-cols-4 gap-2']");




                for (WebElement s : ulProdutos.findElements(By.tagName("li"))){

                    try {

                        String caminhoRaiz = "//li[@style='" + s.getAttribute("style");
                        String descricao = s.findElement(By.tagName("h3")).getText();
                        String preco = s.findElement(By.xpath(caminhoRaiz + "']//p[@class='text-sm text-neutral-500 font-bold']"))
                                .getText().replaceAll("[R$ ]", "").replace(",", ".");


                        String detalhePromocao = s.findElement(By.xpath(caminhoRaiz + "']//div[@class='flex text-[10px] text-neutral-500 text-center items-center']")).getText();
                        String precoDesconto = s.findElement(
                                        By.xpath(caminhoRaiz + "']//p[@class='text-lg xl:text-xl text-neutral-500 font-bold']"))
                                .getText().replaceAll("[R$ ]", "").replace(",", ".");
                        String taxaDesconto = s.findElement(By.xpath(caminhoRaiz + "']//div[@data-test='discount-badge']"))
                                .getText().replaceAll("[-%]" , "");



                        listaProdutos.add(
                                conversorProduto(descricao, preco, detalhePromocao, precoDesconto, taxaDesconto));



                    } catch (NoSuchElementException e) {
                        String descricao = "Sem desconto!";

                        System.out.println(descricao);
                    }
                }


            quantidadePaginas++;
        }


        driver.quit();

        return listaProdutos;

    }

    private Produto conversorProduto(
            String descricao, String preco, String detalhePromocao, String precoDesconto, String taxaDesconto) {

        return Produto.builder()
                .descricao(descricao)
                .preco(new BigDecimal(preco))
                .condicao(detalhePromocao)
                .precoDesconto(new BigDecimal(precoDesconto))
                .porcentagem(new BigDecimal(taxaDesconto))
                .build();
    }




    private static String formatadorCSV(String descricao, String preco, String detalhePromocao, String precoDesconto, String taxaDesconto) {
        return String.format("%s,%s,%s,%s,%s",
                descricao, preco, detalhePromocao,
                precoDesconto, taxaDesconto);
    }



    private static WebElement getVisibleElements(WebDriver driver, String expressao) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DURATION_SEC));

        return wait.until(ExpectedConditions
                .visibilityOfElementLocated(By
                        .xpath(expressao)));
    }

    private static WebElement getClickableElements(WebDriver driver, String expressao) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DURATION_SEC));

        return wait.until(ExpectedConditions
                .elementToBeClickable(By
                        .xpath(expressao)));
    }
}
