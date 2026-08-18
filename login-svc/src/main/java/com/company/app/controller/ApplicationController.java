package com.company.demo.controller;
import com.company.demo.dto.TransaccionDto;
import com.company.demo.service.TransaccionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/transaccion")
public class ApplicationController {

    private final TransaccionService service;

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody TransaccionDto transaccion){
        return ResponseEntity.ok(service.procesar(transaccion));
    }


}
