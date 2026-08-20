package com.company.app.service;


import com.company.app.dto.TransaccionDto;
import com.company.app.entity.Transaccion;
import com.company.app.enums.Estatus;
import com.company.app.repository.TransaccionRepository;
import com.company.app.util.Utils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TransaccionService {
    private final TransaccionRepository repository;

    public TransaccionDto save(Transaccion tx){
        tx.setReferencia(Utils.generadorReferencia());
        tx.setEstatus(Estatus.APROBADA);
        Transaccion tmp = repository.save(tx);
        //Long id, Estatus estatus, String referencia, String operacion
        return TransaccionDto.toDto(tmp.getId(), Estatus.valueOf(String.valueOf(tmp.getEstatus())), tmp.getReferencia(), tmp.getOperacion());

    }


    public Page<TransaccionDto> findAll(Pageable pageable) {
        Page<Transaccion> pokemonPage = repository.findAll(pageable);

        if (pokemonPage.isEmpty() && pageable.getPageNumber() == 0) {
            pokemonPage = repository.findAll(pageable);
        }

        return pokemonPage.map(this::convertToDTO);
    }

    public Page<Transaccion> obtenerTransacciones(String search, Pageable pageable) {
        return repository.buscarConFiltro(search, pageable);
    }

    protected  TransaccionDto convertToDTO(Transaccion tx){
        return TransaccionDto.builder().id(tx.getId())
                .estatus(String.valueOf(Estatus.valueOf(String.valueOf(tx.getEstatus()))))
                .referencia(tx.getReferencia())
                .operacion(tx.getOperacion())
                .build();
    }
}
