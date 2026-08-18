package com.company.demo.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class TransaccionDto {

    @NotBlank(message = "La operacion es un campo obligatorio")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Operación solo debe contener letras")
    private String operacion;

    @NotBlank(message = "Importe es obligatorio")
    @Pattern(regexp = "^\\d+(\\.\\d{1,2})?$", message = "Importe debe ser un número con hasta dos decimales")
    private String importe;

    @NotBlank(message = "Cliente es obligatorio")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Cliente solo debe contener letras y espacios")
    private String cliente;

    @NotBlank(message = "Favor de proveer un secreto")
    private String secreto;

    public static TransaccionDto toTransaccionDto(){
        return new TransaccionDto();
    }
}
