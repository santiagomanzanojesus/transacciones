package com.company.app.controller;

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

@RequestMapping("/api/transaccion")
@AllArgsConstructor
@RestController
public class TransaccionController {

    private final TransaccionService service;

    @PostMapping
    public TransaccionDto guardarTransaccion(@RequestBody Transaccion tx){
        return service.save(tx);
    }


    @GetMapping
    public ResponseEntity<Page<TransaccionDto>> getPokemons(
            @RequestParam(defaultValue = "1") @Min(1) int page,
            @RequestParam(defaultValue = "20") @Min(1) int limit,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Sort.Direction direction = Sort.Direction.fromString(sortDir);
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(direction, sortBy));

        Page<TransaccionDto> result;
        /*if (search != null && !search.trim().isEmpty()) {
            result = service.searchPokemons(search.trim(), pageable);
        } else {*/
            result = service.findAll(pageable);
       // }

        return ResponseEntity.ok(result);
    }

}
