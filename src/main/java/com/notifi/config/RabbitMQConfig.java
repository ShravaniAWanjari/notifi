package com.notifi.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String NOTIFICATION_EXCHANGE = "notification.exchange";
    
    public static final String NOTIFICATION_QUEUE = "notification.queue";
    public static final String RETRY_QUEUE = "notification.retry.queue";
    public static final String DLQ_QUEUE = "notification.dlq.queue";
    
    public static final String NOTIFICATION_ROUTING_KEY = "notification.routing.key";
    public static final String RETRY_ROUTING_KEY = "notification.retry.routing.key";
    public static final String DLQ_ROUTING_KEY = "notification.dlq.routing.key";

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public DirectExchange notificationExchange() {
        return new DirectExchange(NOTIFICATION_EXCHANGE);
    }

    @Bean
    public Queue notificationQueue() {
        return QueueBuilder.durable(NOTIFICATION_QUEUE)
                .withArgument("x-dead-letter-exchange", NOTIFICATION_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DLQ_ROUTING_KEY)
                .build();
    }

    @Bean
    public Queue retryQueue() {
        return QueueBuilder.durable(RETRY_QUEUE)
                .withArgument("x-dead-letter-exchange", NOTIFICATION_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", NOTIFICATION_ROUTING_KEY)
                .withArgument("x-message-ttl", 30000) // 30s delay for retry
                .build();
    }

    @Bean
    public Queue dlqQueue() {
        return QueueBuilder.durable(DLQ_QUEUE).build();
    }

    @Bean
    public Binding notificationBinding(Queue notificationQueue, DirectExchange notificationExchange) {
        return BindingBuilder.bind(notificationQueue).to(notificationExchange).with(NOTIFICATION_ROUTING_KEY);
    }

    @Bean
    public Binding retryBinding(Queue retryQueue, DirectExchange notificationExchange) {
        return BindingBuilder.bind(retryQueue).to(notificationExchange).with(RETRY_ROUTING_KEY);
    }

    @Bean
    public Binding dlqBinding(Queue dlqQueue, DirectExchange notificationExchange) {
        return BindingBuilder.bind(dlqQueue).to(notificationExchange).with(DLQ_ROUTING_KEY);
    }
}
