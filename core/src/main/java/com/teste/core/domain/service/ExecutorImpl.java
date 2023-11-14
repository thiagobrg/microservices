package com.teste.core.domain.service;


import java.util.Random;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import lombok.extern.apachecommons.CommonsLog;

@Service
@CommonsLog
@EnableAsync
public class ExecutorImpl implements Executor {

	@Async("taskExecutorForHeavyTasks")
	public void execute(String msg) {
		
		try {
			log.info(String.format("PROCESSING %s ", msg));
			Random random = new Random();
			int nextInt = random.nextInt(2000) + 5000;
			Thread.sleep(nextInt);
			log.info(String.format("PROCESSED %s AFTER %d SECONDS", msg, nextInt/1000));
			
		}catch(Exception e) {
			
		}
	}
	
	@Async("taskExecutorForHeavyTasks")
	public void execute2(String msg) {
		
		try {
			log.info(String.format("PROCESSING 2 %s ", msg));
			Random random = new Random();
			int nextInt = random.nextInt(2000) + 5000;
			Thread.sleep(nextInt);
			log.info(String.format("PROCESSED %s AFTER %d SECONDS", msg, nextInt/1000));
			
		}catch(Exception e) {
			
		}
		
		
	}
}
