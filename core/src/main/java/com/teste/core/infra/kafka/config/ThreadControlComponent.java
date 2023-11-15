package com.teste.core.infra.kafka.config;

import java.util.Map;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ThreadControlComponent {
	
	@Autowired
	private Map<String, ThreadPoolTaskExecutor> pools;
	
	@Before("@annotation(threadControl) && @annotation(org.springframework.kafka.annotation.KafkaListener)")
	public synchronized void beforeConditionalMessageListener(ThreadControl threadControl) {
		String condition = threadControl.name();
		ThreadPoolTaskExecutor taskExecutor = pools.get(condition);
		
		
		while (taskExecutor.getActiveCount() >= taskExecutor.getMaxPoolSize()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
