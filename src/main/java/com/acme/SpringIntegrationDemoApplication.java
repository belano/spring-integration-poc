package com.acme;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

@SpringBootApplication // same as @Configuration @EnableAutoConfiguration @ComponentScan
@EnableJms
public class SpringIntegrationDemoApplication {
	
	final static Logger logger = LoggerFactory
			.getLogger(SpringIntegrationDemoApplication.class);
	
	public static void main(String[] args) throws Exception {				
		ConfigurableApplicationContext context = SpringApplication.run(
				SpringIntegrationDemoApplication.class, args);

		// Send a message to queue
		MessageCreator messageCreator = new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage("ping!");
			}
		};
		JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
		logger.debug("Sending a new message.");
		jmsTemplate.send("mailbox-destination", messageCreator);
	}
}
