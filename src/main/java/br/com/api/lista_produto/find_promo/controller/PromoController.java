package br.com.api.lista_produto.find_promo.controller;


import br.com.api.lista_produto.find_promo.service.Atacadao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("promocoes")
public class PromoController {

    @Autowired
    private Atacadao atacadao;


    @GetMapping
    public ResponseEntity buscarPromocoes(){

        atacadao.getPromocoes();

        return ResponseEntity.ok().body("Lista foi enviada à fila com sucesso!");
    }
}
