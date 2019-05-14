package br.com.spring.mq.consumer.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

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
		return "".equals(user) ? new ActiveMQConnectionFactory(this.brokerURL)
							   : new ActiveMQConnectionFactory(this.user, this.password, this.brokerURL);
	}
}
