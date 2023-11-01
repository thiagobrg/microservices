package com.teste.core.infra.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;


@Component
@FeignClient("auth")
public interface AuthClient {

	
	@GetMapping
	String get();
	
}
