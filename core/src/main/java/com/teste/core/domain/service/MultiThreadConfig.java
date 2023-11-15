package com.teste.core.domain.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class MultiThreadConfig {

	@Primary
	@Bean(name = "taskExecutorForHeavyTasks")
	public ThreadPoolTaskExecutor myThreadBean() {
	   ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
	   executor.setCorePoolSize(1);
	   executor.setMaxPoolSize(1);
	   executor.setQueueCapacity(0);
	   return executor;
	}
}
