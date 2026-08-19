package com.company.app.dto;

import com.company.app.enums.Estatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransaccionDto {

    private Long id;

    private String estatus;

    private String referencia;

    private String operacion;

    public static TransaccionDto toDto(Long id, Estatus estatus, String referencia, String operacion){
        return new TransaccionDto(id, estatus.getEstatus(), referencia, operacion);
    }

}
