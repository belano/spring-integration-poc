package com.acme.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

	final static Logger logger = LoggerFactory
			.getLogger(Receiver.class);
	
	/**
	 * Get a copy of the application context
	 */
	@Autowired
	ConfigurableApplicationContext context;

	/**
	 * When you receive a message, print it out, then shut down the application.
	 * Finally, clean up any ActiveMQ server stuff.
	 */
	@JmsListener(destination = "mailbox-destination")	
	public void receiveMessage(String message) {
		logger.debug("Received <{}>", message);
	}

}
