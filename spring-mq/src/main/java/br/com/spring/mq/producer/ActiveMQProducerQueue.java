package br.com.spring.mq.producer;

import javax.jms.JMSException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.spring.mq.api.ActiveMQApi;

@Component
public class ActiveMQProducerQueue {

	private Logger LOGGER = Logger.getLogger(ActiveMQProducerQueue.class);
	
	@Autowired
	private ActiveMQApi activeMQApi;
	
	@Autowired
	private JmsTemplate jmsTemplateQueue;
	
	private int count = 0;
	
	@Scheduled(initialDelay = 2000, fixedDelay = 5000)
	public void pushValueOnQueue() {
		try {
			LOGGER.info("SEND MESSAGES TO QUEUE");
			
			if(count == 0)this.activeMQApi.createQueue("INTEGRA.NFE.TERC");
			int msgTot = 0;
			while(msgTot < 200) {
//				this.activeMQApi.addMessageToDestination("INTEGRA.NFE.TERC", "Checking...["+(++count)+"]", true);
				this.jmsTemplateQueue.convertAndSend("INTEGRA.NFE.TERC", "Checking...["+(++count)+"]");
				msgTot++;
			}
		}catch(JMSException ex) {
			LOGGER.error("Error on push value to queue, [ERROR] = "+ex.getMessage());
		}
	}
	
}
