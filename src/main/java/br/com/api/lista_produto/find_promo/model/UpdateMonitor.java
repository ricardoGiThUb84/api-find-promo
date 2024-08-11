package br.com.api.lista_produto.find_promo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;

@Entity
@Table(name = "update_monitor")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
public class UpdateMonitor {


    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "data_atualizacao", nullable = false)
    private LocalDate dataAtualizacao;
}