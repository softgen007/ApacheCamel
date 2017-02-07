package com.rks.apache.camel.hello;

import java.util.Scanner;

import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class CamelActiveMQHelloWorld {

	public static void main(String[] args) throws Exception {
		CamelContext camelCtx = new DefaultCamelContext();
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter your message:");
		String inMessage = sc.nextLine();
		try {
			camelCtx.addComponent("activemq",
					ActiveMQComponent.activeMQComponent("vm://localhost?broker.persistent=false"));
			camelCtx.addRoutes(new RouteBuilder() {
					@Override
					public void configure() throws Exception {
							from("activemq:queue:test.queue")
							.to("stream:out");
					}

			});
			ProducerTemplate template = camelCtx.createProducerTemplate();
			camelCtx.start();
			template.sendBody("activemq:test.queue", inMessage);
			Thread.sleep(2000);

		} catch (Exception e) {
			
			e.printStackTrace();
		}finally{
			camelCtx.stop();
			sc.close();
		}

	}

}
