package ru.kramskoi.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, Jackson2JsonMessageConverter converter) {
        return new RabbitTemplate(connectionFactory).setMessageConverter(converter);
    }

    @Bean
    public Jackson2JsonMessageConverter Jackson2MessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public Queue QueueCatAdd() {
        return new Queue("QueueCatAdd");
    }

    @Bean
    public Queue QueueCatDelete() {
        return new Queue("QueueCatDelete");
    }

    @Bean
    public Queue QueueCatUpdate() {
        return new Queue("QueueCatUpdate");
    }

    @Bean
    public Queue QueueCatAddFriend() {
        return new Queue("QueueCatAddFriend");
    }

    @Bean
    public Queue QueueOwnerAdd() {
        return new Queue("QueueOwnerAdd");
    }

    @Bean
    public Queue QueueOwnerDelete() {
        return new Queue("QueueOwnerDelete");
    }

    @Bean
    public Queue QueueUserAdd() {
        return new Queue("QueueUserAdd");
    }
}
