package com.griddynamics.czechowiczk.rabbitmqpublisher;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final String exchange;
    private final String routingKey;

    public RabbitMqPublisher(RabbitTemplate rabbitTemplate,
        @Value("${rabbitmq.exchange}") String exchange,
        @Value("${rabbitmq.routing-key}") String routingKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
        this.routingKey = routingKey;
    }

    @Scheduled(fixedRateString = "${rabbitmq.message-rate}")
    public void sentMessage() {
        String message = "Hello RabbitMQ! " + System.currentTimeMillis();
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
        System.out.println("Sent message: " + message);
    }

}
