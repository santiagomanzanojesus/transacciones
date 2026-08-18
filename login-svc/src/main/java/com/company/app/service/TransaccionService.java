package com.company.demo.service;

import com.company.demo.config.PersistenceClient;
import com.company.demo.dto.TransaccionDto;
import com.company.demo.dto.TransaccionResponse;
import com.company.demo.service.AesService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class TransaccionService {


    private final AesService aesService;

    private final PersistenceClient persistenceClient;

    public TransaccionResponse procesar(TransaccionDto request) {
        try {
            String secretoDescifrado = aesService.decrypt(request.getSecreto());
            TransaccionDto dto = new TransaccionDto();
            dto.setOperacion(request.getOperacion());
            dto.setImporte(request.getImporte());
            dto.setCliente(request.getCliente());
            dto.setSecreto(secretoDescifrado);

            TransaccionResponse test =  persistenceClient.guardar(dto);
            return test;
        } catch (Exception e) {
            throw new RuntimeException("Error al descifrar o procesar", e);
        }
    }
}