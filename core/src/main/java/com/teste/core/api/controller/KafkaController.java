package com.teste.core.api.controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teste.core.infra.kafka.config.KafkaProducer;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("kafka")
@AllArgsConstructor
public class KafkaController {
	
	private final KafkaProducer producer;

	@PostMapping("/{qtd}")
	public void addMessages(@PathVariable Integer qtd) {
		UUID randomUUID = UUID.randomUUID();
		
		for (int i = 0; i < qtd; i++) {
			producer.send(randomUUID + " -- " + (i + 1));
		}
		
		
	}
}
