package br.com.spring.mq.config;

import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

@Configuration
@EnableJms
public class ActiveMQConfig {

	@Value("${spring.activemq.broker-url}")
	private String brokerURL;
	
	@Value("${spring.activemq.user}")
	private String user;
	
	@Value("${spring.activemq.password}")
	private String password;
	
	@Bean
	public ActiveMQConnectionFactory connectionFactory() {
		return "".equals(this.user) ? new ActiveMQConnectionFactory(this.brokerURL)
									: new ActiveMQConnectionFactory(this.user, this.password, this.brokerURL);
	}

	@Bean
	public JmsListenerContainerFactory<?> jmsFactoryTopic(DefaultJmsListenerContainerFactoryConfigurer configurer,
														  ConnectionFactory connectionFactory) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		configurer.configure(factory, connectionFactory);
		factory.setPubSubDomain(true);
		return factory;
	}
	
	@Bean
	public JmsTemplate jmsTemplateQueue() {
		return new JmsTemplate(this.connectionFactory());
	}
	
	@Bean
	public JmsTemplate jmsTemplateTopic() {
		JmsTemplate jmsTemplate = new JmsTemplate(this.connectionFactory());
		jmsTemplate.setDeliveryMode(DeliveryMode.PERSISTENT);
		jmsTemplate.setPubSubDomain(true);
		return jmsTemplate;
	}
}
