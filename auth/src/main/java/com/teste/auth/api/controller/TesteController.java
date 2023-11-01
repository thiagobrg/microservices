package com.teste.auth.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teste.auth.infra.client.CoreClient;

@RestController
public class TesteController {
	
	@Autowired
	private CoreClient coreClient;
	
	@GetMapping
	public String get() {
		return "AUTH";
	}
	
	@GetMapping("core")
	public String getCore() {
		return coreClient.get();
	}

}
