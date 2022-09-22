package com.alpha.postandcomments.application.adapters.bus;

import co.com.sofka.domain.generic.DomainEvent;
import com.google.gson.Gson;
import com.alpha.postandcomments.application.config.RabbitConfig;
import com.alpha.postandcomments.business.gateways.EventBus;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

//This class/buss is in charge to sent stuff to rabbitmq queue
@Service
public class RabbitMqEventBus implements EventBus {

    private final RabbitTemplate rabbitTemplate; //Helper class that simplifies synchronous RabbitMQ access (sending and receiving messages).
    private final Gson gson = new Gson(); //GSON is a Java API, developed by Google, used to convert Java objects to JSON (serialization) and JSON to Java objects (deserialization)

    public RabbitMqEventBus(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publish(DomainEvent event) {

        var notification = new Notification(
                event.getClass().getTypeName(),
                gson.toJson(event)
        );

        //Convert a Java object to an Amqp Message and send it to a default exchange with a default routing key.
        rabbitTemplate.convertAndSend(
                RabbitConfig.EXCHANGE, RabbitConfig.GENERAL_ROUTING_KEY, notification.serialize().getBytes()
        );
    }

    @Override
    public void publishError(Throwable errorEvent) {
        var event = new ErrorEvent(errorEvent.getClass().getTypeName(), errorEvent.getMessage());
        var notification = new Notification(
                event.getClass().getTypeName(),
                gson.toJson(event)
        );
        //Convert a Java object to an Amqp Message and send it to a default exchange with a default routing key.
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.GENERAL_ROUTING_KEY, notification.serialize().getBytes());
    }
}
