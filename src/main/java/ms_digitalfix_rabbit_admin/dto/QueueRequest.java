package ms_digitalfix_rabbit_admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record QueueRequest(

        @NotBlank(message = "El nombre de la cola es obligatorio")
        @Size(max = 100, message = "El nombre de la cola no puede superar 100 caracteres")
        String name,

        boolean durable
) {
}