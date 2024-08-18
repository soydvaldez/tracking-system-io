package io.tracksystem.sensor.config.events;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.MessageConverter;

@Configuration
@Profile({"default", "test", "dev", "prod"})
public class NewSensorAddedConfig {

    // @Value("${tracksystem.rabbitmq.queue}")
    public String exchangeName;
    
    // @Value("${tracksystem.rabbitmq.queue}")
    public String bindingName;
    
    @Value("${io.tracksystem.rabbitmq.queue.name}")
    private String queueName;

    public NewSensorAddedConfig() {
        exchangeName = "exchange";
        bindingName = "routing-key";
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchangeName);
    }

    @Bean
    public Binding binding(Queue newSensorAddedQueue, TopicExchange exchangeName) {
        return BindingBuilder.bind(newSensorAddedQueue).to(exchangeName).with(bindingName);
    }

    @Bean
    public Queue newSensorAddedQueue() {
        return new Queue(queueName, true);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
