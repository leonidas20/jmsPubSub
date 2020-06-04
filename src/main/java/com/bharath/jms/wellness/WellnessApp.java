package com.bharath.jms.wellness;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import com.bharath.jsm.hr.Employee;

public class WellnessApp {

	public static void main(String[] args) throws NamingException, JMSException {

		InitialContext context = new InitialContext();
		Topic topic = (Topic) context.lookup("topic/empTopic");
		
		try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmscontext = cf.createContext();) {
			
			JMSConsumer consumer = jmscontext.createSharedConsumer(topic, "SharedConsumer");
			JMSConsumer consumer2 = jmscontext.createSharedConsumer(topic, "SharedConsumer");

			for(int i = 1; i<=10; i+=2) {
				Message message = consumer.receive();
				Employee employee = message.getBody(Employee.class);
				System.out.println("Consumer1" + employee.getFirstName());
				
				Message message2 = consumer2.receive();
				Employee employee2 = message2.getBody(Employee.class);
				System.out.println("Consumer2:" + employee2.getFirstName());
				
				//System.out.println("i=" +i);
			}
		} 
	}
}
