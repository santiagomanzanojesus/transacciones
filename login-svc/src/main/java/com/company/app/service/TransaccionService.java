package com.company.app.service;

import com.company.app.config.PersistenceClient;
import com.company.app.dto.PagedResponse;
import com.company.app.dto.TransaccionDto;
import com.company.app.dto.TransaccionResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

  /*  public Page<TransaccionResponse> obtenerTransacciones(int page, int limit, String search, String sortBy, String sortDir) {
        return persistenceClient.obtenerTransacciones(page, limit, search, sortBy, sortDir);
    }
*/
  public PagedResponse<TransaccionResponse> obtenerTransacciones(
          int page, int limit, String search, String sortBy, String sortDir) {
      return persistenceClient.obtenerTransacciones(page, limit, search, sortBy, sortDir);
  }
}