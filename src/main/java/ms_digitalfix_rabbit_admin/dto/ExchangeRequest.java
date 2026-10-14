package ms_digitalfix_rabbit_admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ExchangeRequest(

        @NotBlank(message = "El nombre del exchange es obligatorio")
        @Size(max = 100, message = "El nombre del exchange no puede superar 100 caracteres")
        String name,

        @NotBlank(message = "El tipo de exchange es obligatorio")
        @Pattern(
                regexp = "(?i)direct|topic",
                message = "El tipo debe ser direct o topic"
        )
        String type,

        boolean durable
) {
}