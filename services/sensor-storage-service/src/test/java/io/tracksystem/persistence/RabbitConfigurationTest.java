package io.tracksystem.persistence;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.RabbitMQContainer;

// Esta clase representa la abstraccion de un servidor rabbitMQ

@TestConfiguration
@EnableRabbit
public class RabbitConfigurationTest {

    public String nameExchange;
    public String bindingName;

    @Value("${tracksystem.queue.name}")
    private String queueName;

    public RabbitConfigurationTest() {
        nameExchange = "exchange";
        bindingName = "routing-key";
    }

    // Utilidad para convierte el mensaje recibido del RabbitMQ en un DTO
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // Declara la cola y el enrutamiento para el envio y la recepcion de mensajes
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(nameExchange);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(bindingName);
    }

    @Bean
    public Queue temperatureData() {
        return new Queue(queueName, true);
    }

    // Crea la infraestructura servidor rabbitMQ en un contenedor docker
    @Bean
    public RabbitMQContainer rabbitMQContainer() {
        RabbitMQContainer container = new RabbitMQContainer("rabbitmq:3-management");
        container.withExposedPorts(5672, 15672);
        container.start();
        return container;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public ConnectionFactory connectionFactory(RabbitMQContainer rabbitMQContainer) {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(rabbitMQContainer.getHost());
        connectionFactory.setPort(rabbitMQContainer.getAmqpPort());
        return connectionFactory;
    }
}