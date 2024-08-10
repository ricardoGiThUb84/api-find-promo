package br.com.api.lista_produto.find_promo.model;


import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;


@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Builder
public class Produto {

    private Long id;

    private String descricao;

    private String condicao;

    private BigDecimal preco;

    private BigDecimal precoDesconto;

    private BigDecimal porcentagem;


}
