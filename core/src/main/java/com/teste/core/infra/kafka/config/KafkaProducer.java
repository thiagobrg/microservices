package com.teste.core.infra.kafka.config;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class KafkaProducer {
	
	private final KafkaTemplate<String, String> template;
	
	public void send(String msg) {
		template.send("teste", msg);
	}

}
