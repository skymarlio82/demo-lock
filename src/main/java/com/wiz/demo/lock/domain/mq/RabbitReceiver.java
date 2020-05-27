
package com.wiz.demo.lock.domain.mq;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
import com.wiz.demo.lock.domain.service.SecKillService;

@Component
public class RabbitReceiver {

	@Autowired
	private SecKillService secKillService = null;

	@SuppressWarnings("rawtypes")
	@RabbitListener(bindings=@QueueBinding(
		value=@Queue(value="queue-1", durable="true"),
		exchange=@Exchange(value="exchange-1", durable="true", type="topic", ignoreDeclarationExceptions="true"),
		key="springboot.*")
	)
	@RabbitHandler
	public void onMessage(Message message, Channel channel) throws Exception {
		System.out.println("====>>>> consumer.onMessage : Payload=" + message.getPayload());
		String productId = (String) message.getHeaders().get("product_id");
		System.out.println("====>>>> consumer.onMessage : productId=" + productId);
		Long deliveryTag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
		System.out.println("====>>>> consumer.onMessage : deliveryTag=" + deliveryTag);
		String payload = (String)message.getPayload();
		secKillService.seckill1(Integer.valueOf(payload));
		// 手工ACK, 获取deliveryTag
		channel.basicAck(deliveryTag, false);
	}
}