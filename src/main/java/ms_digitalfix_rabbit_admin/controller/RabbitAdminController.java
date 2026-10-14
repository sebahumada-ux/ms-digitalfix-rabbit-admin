package ms_digitalfix_rabbit_admin.controller;

import jakarta.validation.Valid;
import ms_digitalfix_rabbit_admin.dto.BindingRequest;
import ms_digitalfix_rabbit_admin.dto.ExchangeRequest;
import ms_digitalfix_rabbit_admin.dto.QueueRequest;
import ms_digitalfix_rabbit_admin.service.RabbitAdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/rabbit")
public class RabbitAdminController {

    private final RabbitAdminService rabbitAdminService;

    public RabbitAdminController(
            RabbitAdminService rabbitAdminService) {

        this.rabbitAdminService = rabbitAdminService;
    }

    @PostMapping("/queues")
    public ResponseEntity<Map<String, String>> crearQueue(
            @Valid @RequestBody QueueRequest request) {

        rabbitAdminService.crearQueue(request);

        return ResponseEntity.ok(
                Map.of(
                        "mensaje", "Cola creada correctamente",
                        "queue", request.name()
                )
        );
    }

    @DeleteMapping("/queues/{name}")
    public ResponseEntity<Map<String, Object>> eliminarQueue(
            @PathVariable String name) {

        boolean eliminada =
                rabbitAdminService.eliminarQueue(name);

        return ResponseEntity.ok(
                Map.of(
                        "mensaje", "Solicitud de eliminación procesada",
                        "queue", name,
                        "eliminada", eliminada
                )
        );
    }


    @PostMapping("/exchanges")
    public ResponseEntity<Map<String, String>> crearExchange(
            @Valid @RequestBody ExchangeRequest request) {

        rabbitAdminService.crearExchange(request);

        return ResponseEntity.ok(
                Map.of(
                        "mensaje", "Exchange creado correctamente",
                        "exchange", request.name(),
                        "tipo", request.type()
                )
        );
    }

    @DeleteMapping("/exchanges/{name}")
    public ResponseEntity<Map<String, String>> eliminarExchange(
            @PathVariable String name) {

        rabbitAdminService.eliminarExchange(name);

        return ResponseEntity.ok(
                Map.of(
                        "mensaje", "Exchange eliminado correctamente",
                        "exchange", name
                )
        );
    }

    @PostMapping("/bindings")
    public ResponseEntity<Map<String, String>> crearBinding(
            @Valid @RequestBody BindingRequest request) {

        rabbitAdminService.crearBinding(request);

        return ResponseEntity.ok(
                Map.of(
                        "mensaje", "Binding creado correctamente",
                        "queue", request.queue(),
                        "exchange", request.exchange(),
                        "routingKey", request.routingKey()
                )
        );
    }

    @DeleteMapping("/bindings")
    public ResponseEntity<Map<String, String>> eliminarBinding(
            @Valid @RequestBody BindingRequest request) {

        rabbitAdminService.eliminarBinding(request);

        return ResponseEntity.ok(
                Map.of(
                        "mensaje", "Binding eliminado correctamente",
                        "queue", request.queue(),
                        "exchange", request.exchange(),
                        "routingKey", request.routingKey()
                )
        );
    }
}