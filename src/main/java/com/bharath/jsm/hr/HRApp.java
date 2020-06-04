package com.bharath.jsm.hr;

import javax.jms.JMSContext;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class HRApp {

	public static void main(String[] args) throws NamingException {

		InitialContext context = new InitialContext();
		Topic topic = (Topic) context.lookup("topic/empTopic");
		
		try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmscontext = cf.createContext();) {
			
			Employee employee = new Employee();
			employee.setId(123);
			employee.setFirstName("John");
			employee.setLastName("Doe");
			employee.setDesignation("Civil Engineer");
			employee.setEmail("doe@gmail.com");
			employee.setPhone("2310415656");
			
			for(int i=1;i<=10;i++) {
				jmscontext.createProducer().send(topic, employee);
				System.out.println("i=" +i);

			}
			
			System.out.println("Message sent");
		} 
	}
}
