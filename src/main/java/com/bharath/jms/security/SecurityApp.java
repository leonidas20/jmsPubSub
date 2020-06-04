package com.bharath.jms.security;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import com.bharath.jsm.hr.Employee;

public class SecurityApp {

	public static void main(String[] args) throws NamingException, JMSException, InterruptedException {

		InitialContext context = new InitialContext();
		Topic topic = (Topic) context.lookup("topic/empTopic");
		
		try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmscontext = cf.createContext();) {
			
			jmscontext.setClientID("securityApp");
			JMSConsumer consumer = jmscontext.createDurableConsumer(topic, "subscription1");
			consumer.close();
			
			Thread.sleep(10000);
			/*
			 * If they are some message between the close and the open of the consumer
			 * will still be delivered
			 */
			
			consumer = jmscontext.createDurableConsumer(topic, "subscription1");

			
			Message message = consumer.receive();
			Employee employee = message.getBody(Employee.class);
			
			System.out.println(employee.getFirstName());
			consumer.close();
			jmscontext.unsubscribe("subscription1");
		} 
	}
}
