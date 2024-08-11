package br.com.api.lista_produto.find_promo.repository;

import br.com.api.lista_produto.find_promo.model.UpdateMonitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpdateMonitorRepository extends JpaRepository<UpdateMonitor, Long> {
}
