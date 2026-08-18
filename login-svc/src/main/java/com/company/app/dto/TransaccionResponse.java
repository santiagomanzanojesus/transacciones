package com.company.app.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransaccionResponse {
    private Long id;

    private String operacion;

    private String estatus;

    private String referencia;

}
