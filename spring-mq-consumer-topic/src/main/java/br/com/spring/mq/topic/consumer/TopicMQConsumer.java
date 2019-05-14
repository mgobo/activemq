package br.com.spring.mq.topic.consumer;

import java.io.IOException;

import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class TopicMQConsumer {

	@JmsListener(destination = "TOPIC.NFE.LEG", subscription = "TOPIC.NFE.LEG", containerFactory = "jmsFactoryTopic")
	public void topicConsumer(Object msg) throws IOException, JMSException {
		ActiveMQMessage message = (ActiveMQMessage) msg;
//		if(message != null) {
//			System.out.println(message.getProperty("MSG"));
//		}
		if(msg != null) {
			System.out.println(((TextMessage)message.getMessage()).getText());
		}
	}
	
}
