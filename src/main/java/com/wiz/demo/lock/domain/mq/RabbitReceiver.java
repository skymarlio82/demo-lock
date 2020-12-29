package com.wiz.demo.lock.domain.mq;

import com.rabbitmq.client.Channel;
import com.wiz.demo.lock.domain.service.SecKillService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RabbitReceiver {
    private final Log logger = LogFactory.getLog(RabbitReceiver.class);
    private final SecKillService secKillService;

    public RabbitReceiver(SecKillService secKillService) {
        this.secKillService = secKillService;
    }

    @SuppressWarnings("rawtypes")
    @RabbitListener(bindings = @QueueBinding(
        value = @Queue(value = "queue-1", durable = "true"),
        exchange = @Exchange(value = "exchange-1", durable = "true", type = "topic", ignoreDeclarationExceptions = "true"),
        key = "springboot.*")
    )
    @RabbitHandler
    public void onMessage(Message message, Channel channel) throws IOException {
        logger.info("====>>>> consumer.onMessage : Payload=" + message.getPayload());
        String productId = (String) message.getHeaders().get("product_id");
        logger.info("====>>>> consumer.onMessage : productId=" + productId);
        Long deliveryTag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
        logger.info("====>>>> consumer.onMessage : deliveryTag=" + deliveryTag);
        String payload = (String) message.getPayload();
        secKillService.seckill1(Integer.valueOf(payload));
        // 手工ACK, 获取deliveryTag
        channel.basicAck(deliveryTag, false);
    }
}