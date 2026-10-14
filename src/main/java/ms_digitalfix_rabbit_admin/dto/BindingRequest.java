package ms_digitalfix_rabbit_admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BindingRequest(

        @NotBlank(message = "El nombre de la cola es obligatorio")
        @Size(max = 100)
        String queue,

        @NotBlank(message = "El nombre del exchange es obligatorio")
        @Size(max = 100)
        String exchange,

        @NotBlank(message = "La routing key es obligatoria")
        @Size(max = 150)
        String routingKey
) {
}