package com.company.app.entity;
import com.company.app.enums.Estatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Table
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Transaccion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String operacion;

    private BigDecimal importe;

    private String cliente;

    private String secreto;

    private String referencia;

    private Estatus estatus;
}
