package br.com.api.lista_produto.find_promo.service;

import br.com.api.lista_produto.find_promo.model.UpdateMonitor;
import br.com.api.lista_produto.find_promo.repository.UpdateMonitorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UpdateMonitorService {

    @Autowired
    private UpdateMonitorRepository updateMonitorRepository;


    @Transactional
    public UpdateMonitor criarData() {

        LocalDate data = LocalDate.now();
        UpdateMonitor updateData = new UpdateMonitor();

        List<UpdateMonitor> buscaAtualizacao = updateMonitorRepository.findAll();

        if(buscaAtualizacao.size() > 0 &&
                buscaAtualizacao.get(0).getDataAtualizacao().isEqual(data)){
            return null;
        }

        if(buscaAtualizacao.size() > 0 &&
                buscaAtualizacao.get(0).getDataAtualizacao().isBefore(data)){

            UpdateMonitor updateMonitor = buscaAtualizacao.get(0);
            updateMonitor.setDataAtualizacao(data);

            return updateMonitorRepository.save(updateMonitor);
        }


        updateData.setDataAtualizacao(data);
        return updateMonitorRepository.save(updateData);
    }

}
