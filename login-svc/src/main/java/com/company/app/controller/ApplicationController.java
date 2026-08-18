package com.company.app.controller;
import com.company.app.dto.TransaccionDto;
import com.company.app.service.TransaccionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

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


}
