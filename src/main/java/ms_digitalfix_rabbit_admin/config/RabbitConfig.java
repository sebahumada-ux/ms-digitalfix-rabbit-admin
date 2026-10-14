package ms_digitalfix_rabbit_admin.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public AmqpAdmin rabbitAdmin(ConnectionFactory connectionFactory) {

        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);

        // Permite recuperar declaraciones realizadas manualmente
        // si la conexión con RabbitMQ se restablece.
        rabbitAdmin.setRedeclareManualDeclarations(true);

        return rabbitAdmin;
    }
}