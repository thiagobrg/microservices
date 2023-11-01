package com.teste.auth.infra.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("core")
public interface CoreClient {

	
	@GetMapping
	String get();
	
}
