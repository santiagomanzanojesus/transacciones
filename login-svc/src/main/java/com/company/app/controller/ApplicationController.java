package com.company.app.controller;
import com.company.app.dto.PagedResponse;
import com.company.app.dto.TransaccionDto;
import com.company.app.dto.TransaccionResponse;
import com.company.app.service.TransaccionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/transacciones")
public class ApplicationController {

    private final TransaccionService service;

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody TransaccionDto transaccion){
        return ResponseEntity.ok(service.procesar(transaccion));
    }

    @GetMapping
    public ResponseEntity<PagedResponse<TransaccionResponse>> listar(
            @RequestParam(defaultValue = "1") @Min(1) int page,
            @RequestParam(defaultValue = "20") @Min(1) int limit,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir
    ) {
        PagedResponse<TransaccionResponse> response = service.obtenerTransacciones(page, limit, search, sortBy, sortDir);
        return ResponseEntity.ok(response);

    }


}
