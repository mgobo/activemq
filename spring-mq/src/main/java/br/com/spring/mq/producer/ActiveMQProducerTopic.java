package br.com.spring.mq.producer;

import javax.jms.JMSException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.spring.mq.api.ActiveMQApi;

@Component
public class ActiveMQProducerTopic {

	private Logger LOGGER = Logger.getLogger(ActiveMQProducerTopic.class);
	
	@Autowired
	private ActiveMQApi activeMQApi;
	
	@Autowired
	private JmsTemplate jmsTemplateTopic;
	
	private int count = 0;
	
	@Scheduled(initialDelay = 2000, fixedDelay = 5000)
	public void pushValueOnTopic() {
		try {
			LOGGER.info("SEND MESSAGES TO TOPIC");
			if(count == 0)this.activeMQApi.createTopic("TOPIC.NFE.LEG");
			
			int msgTot = 0;
			while(msgTot < 200) {
				this.jmsTemplateTopic.convertAndSend("TOPIC.NFE.LEG", "Checking...["+(++count)+"]");
//				this.activeMQApi.addMessageToDestination("TOPIC.NFE.LEG", "Checking...["+(++count)+"]", false);
				msgTot++;
			}
		}catch(JMSException ex) {
			LOGGER.error("Error on push value to queue, [ERROR] = "+ex.getMessage());
		}
	}
	
}
