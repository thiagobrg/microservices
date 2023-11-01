package com.teste.core.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teste.core.infra.client.AuthClient;

@RestController
public class TesteController {
	
	@Autowired
	private AuthClient authClient;
	
	@GetMapping
	public String get() {
		return "CORE";
	}
	
	@GetMapping("auth")
	public String getAuth() {
		return authClient.get();
	}

}
