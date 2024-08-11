package br.com.api.lista_produto.find_promo.controller;


import br.com.api.lista_produto.find_promo.model.Produto;
import br.com.api.lista_produto.find_promo.model.UpdateMonitor;
import br.com.api.lista_produto.find_promo.service.Atacadao;
import br.com.api.lista_produto.find_promo.service.UpdateMonitorService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("promocoes")
public class PromoController {

    @Autowired
    private Atacadao atacadao;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private UpdateMonitorService updateMonitorService;

    private final String NOME_FILA_PROMO_ATACADAO = "lista.promo.atacadao";


    @GetMapping
    public ResponseEntity buscarPromocoes(){

        UpdateMonitor updateMonitor = updateMonitorService.criarData();

        if(updateMonitor == null) return ResponseEntity.ok()
                .body("Lista já atualizada!");

        List<Produto> promocoes =
                atacadao.getPromocoes();

        rabbitTemplate.convertAndSend(NOME_FILA_PROMO_ATACADAO, promocoes);

        return ResponseEntity.ok().body("Lista foi enviada à fila com sucesso!");
    }
}
