package com.teste.core.infra.kafka.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.teste.core.domain.service.Executor;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor	
public class KafkaListeners {
	
	private final Executor executor;
	
	@Qualifier("taskExecutorForHeavyTasks")
	private final ThreadPoolTaskExecutor taskExecutor;

	@KafkaListener(id = "teste-cosumer", topics = "teste", groupId = "group-1")
	public void listen(String in) {
		System.out.println(taskExecutor.getActiveCount() +"/"+ taskExecutor.getMaxPoolSize());
		while((taskExecutor.getActiveCount() + 1) >= taskExecutor.getMaxPoolSize()) { try {Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}}
		
		if(in.contains("11")) {
			executor.execute2(in);
		}else {
			executor.execute(in);
		}
	}
}
