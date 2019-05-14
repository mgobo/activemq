package br.com.spring.mq.consumer;

import java.io.IOException;

import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class QueueMQConsumer {

	@JmsListener(destination = "INTEGRA.NFE.TERC")
	public void readerNFETerc(Object msg) throws IOException, JMSException {
		ActiveMQMessage message = (ActiveMQMessage) msg;
//		if(message != null) {
//			System.out.println(message.getProperty("MSG"));
//		}
		if(msg != null) {
			System.out.println(((TextMessage)message.getMessage()).getText());
		}
	}
	
}
