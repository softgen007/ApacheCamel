package com.rks.apache.camel.hello;

import java.util.Scanner;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.spring.SpringCamelContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CamelSpringHelloWorld {

	public static void main(String[] args) throws Exception {
		ApplicationContext appContext = new ClassPathXmlApplicationContext("META-INF/spring/SpringContext.xml");
		CamelContext camelContext = SpringCamelContext.springCamelContext(appContext, false);
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter your message:");
		String inMessage = sc.nextLine();
		try {
			ProducerTemplate template = camelContext.createProducerTemplate();
			camelContext.start();
			template.sendBody("activemq:test.queue", inMessage);
			Thread.sleep(2000);
		}catch (Exception e) {
			
			e.printStackTrace();
		}finally {
			sc.close();
			camelContext.stop();
		}

	}

}
