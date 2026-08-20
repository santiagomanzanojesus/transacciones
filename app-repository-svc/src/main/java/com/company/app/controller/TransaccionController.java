package com.company.app.controller;

import com.company.app.dto.PagedResponse;
import com.company.app.dto.TransaccionDto;
import com.company.app.entity.Transaccion;
import com.company.app.service.TransaccionService;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/transacciones")
@AllArgsConstructor
@RestController
public class TransaccionController {

    private final TransaccionService service;

    @PostMapping
    public TransaccionDto guardarTransaccion(@RequestBody Transaccion tx){
        return service.save(tx);
    }


    @GetMapping
    public ResponseEntity<PagedResponse<Transaccion>> listar(
            @RequestParam(defaultValue = "1") @Min(1) int page,
            @RequestParam(defaultValue = "20") @Min(1) int limit,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        // Validar que sortBy sea un campo permitido (opcional)
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.fromString(sortDir), sortBy));
        Page<Transaccion> pageResult = service.obtenerTransacciones(search, pageable);

        // Convertir contenido a DTO
        List<Transaccion> content = pageResult.getContent().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());

        PagedResponse<Transaccion> response = new PagedResponse<>();
        response.setContent(content);
        response.setTotalPages(pageResult.getTotalPages());
        response.setTotalElements(pageResult.getTotalElements());
        response.setSize(pageResult.getSize());
        response.setNumber(pageResult.getNumber() + 1); // para que sea 1-based
        response.setSort(sortBy + "," + sortDir);

        return ResponseEntity.ok(response);
    }

    private Transaccion toResponse(Transaccion entity) {
        Transaccion dto = new Transaccion();
        dto.setId(entity.getId());
        dto.setOperacion(entity.getOperacion());
        dto.setEstatus(entity.getEstatus());
        dto.setReferencia(entity.getReferencia());
        // otros campos si los hay
        return dto;
    }

}
