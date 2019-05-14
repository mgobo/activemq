package br.com.spring.mq.topic.consumer.config;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;

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
		ActiveMQConnectionFactory activeMQConnectionFactory = null;
		activeMQConnectionFactory =  "".equals(user) ? new ActiveMQConnectionFactory(this.brokerURL)
							   						 : new ActiveMQConnectionFactory(this.user, this.password, this.brokerURL);
		return activeMQConnectionFactory;
	}

	@Bean
	public JmsListenerContainerFactory<?> jmsFactoryTopic(DefaultJmsListenerContainerFactoryConfigurer configurer,
														  ConnectionFactory connectionFactory) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		configurer.configure(factory, connectionFactory);
		factory.setPubSubDomain(true);
		return factory;
	}
}
