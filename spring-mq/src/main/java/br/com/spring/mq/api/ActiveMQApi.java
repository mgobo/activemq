package br.com.spring.mq.api;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActiveMQApi {

	@Autowired 
	private ActiveMQConnectionFactory activeMQConnectionFactory;
	private Logger LOGGER = Logger.getLogger(ActiveMQApi.class);
	
	public Queue createQueue(String destination) throws JMSException{
		try {
			LOGGER.info("CREATING QUEUE...");
			Connection connection = this.activeMQConnectionFactory.createConnection();
			connection.setClientID(destination);
			Session session		  = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue queue			  = session.createQueue(destination);
			
			session.close();
			connection.close();
			return queue;
		}catch(JMSException ex) {
			throw new JMSException("Error on queue create, [error] = "+ex.getMessage());
		}
	}
	
	public Topic createTopic(String destination) throws JMSException{
		try {
			LOGGER.info("CREATING TOPIC...");
			
			Connection connection = this.activeMQConnectionFactory.createConnection();
			connection.setClientID(destination);
			
			Session session		  = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Topic topic			  = session.createTopic(destination);
			session.createDurableSubscriber(topic, destination);
			
			session.close();
			connection.close();
			return topic;
		}catch(JMSException ex) {
			throw new JMSException("Error on queue create, [error] = "+ex.getMessage());
		}
	}
	
	public void addMessageToDestination(String destination, Object msg, boolean isQueue) throws JMSException {
		try {
			LOGGER.info("SEND A MESSAGE TO DESTINATION...");
			
			Connection connection = this.activeMQConnectionFactory.createConnection();
			Session session		  = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			Destination dest = isQueue ? session.createQueue(destination) : session.createTopic(destination);
			MessageProducer producer= session.createProducer(dest);
			Message message			= session.createMessage();
			message.setObjectProperty("MSG", msg);
			
			producer.send(message);
			
			producer.close();
			session.close();
			connection.close();
		}catch(JMSException ex) {
			throw new JMSException("Error on create message from queue, [error] = "+ex.getMessage());
		}
	}
}
