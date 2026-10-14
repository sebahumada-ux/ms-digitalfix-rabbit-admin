package ms_digitalfix_rabbit_admin.service;

import ms_digitalfix_rabbit_admin.dto.BindingRequest;
import ms_digitalfix_rabbit_admin.dto.ExchangeRequest;
import ms_digitalfix_rabbit_admin.dto.QueueRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.stereotype.Service;

@Service
public class RabbitAdminService {

    private static final Logger log =
            LoggerFactory.getLogger(RabbitAdminService.class);

    private final AmqpAdmin rabbitAdmin;

    public RabbitAdminService(AmqpAdmin rabbitAdmin) {
        this.rabbitAdmin = rabbitAdmin;
    }

    public void crearQueue(QueueRequest request) {

        Queue queue = new Queue(
                request.name(),
                request.durable(),
                false,
                false
        );

        rabbitAdmin.declareQueue(queue);

        log.info(
                "Cola RabbitMQ creada: {}",
                request.name()
        );
    }

    public boolean eliminarQueue(String name) {

        boolean eliminada =
                rabbitAdmin.deleteQueue(name);

        log.info(
                "Solicitud eliminación cola: {} - resultado: {}",
                name,
                eliminada
        );

        return eliminada;
    }


    public void crearExchange(ExchangeRequest request) {

        Exchange exchange;

        if ("direct".equalsIgnoreCase(request.type())) {

            exchange = new DirectExchange(
                    request.name(),
                    request.durable(),
                    false
            );

        } else if ("topic".equalsIgnoreCase(request.type())) {

            exchange = new TopicExchange(
                    request.name(),
                    request.durable(),
                    false
            );

        } else {
            throw new IllegalArgumentException(
                    "Tipo de exchange no soportado"
            );
        }

        rabbitAdmin.declareExchange(exchange);

        log.info(
                "Exchange RabbitMQ creado: {} tipo={}",
                request.name(),
                request.type()
        );
    }

    public void eliminarExchange(String name) {

        rabbitAdmin.deleteExchange(name);

        log.info(
                "Exchange RabbitMQ eliminado: {}",
                name
        );
    }


    public void crearBinding(BindingRequest request) {

        Binding binding = crearObjetoBinding(request);

        rabbitAdmin.declareBinding(binding);

        log.info(
                "Binding creado: queue={}, exchange={}, routingKey={}",
                request.queue(),
                request.exchange(),
                request.routingKey()
        );
    }

    public void eliminarBinding(BindingRequest request) {

        Binding binding = crearObjetoBinding(request);

        rabbitAdmin.removeBinding(binding);

        log.info(
                "Binding eliminado: queue={}, exchange={}, routingKey={}",
                request.queue(),
                request.exchange(),
                request.routingKey()
        );
    }

    private Binding crearObjetoBinding(
            BindingRequest request) {

        return new Binding(
                request.queue(),
                Binding.DestinationType.QUEUE,
                request.exchange(),
                request.routingKey(),
                null
        );
    }
}