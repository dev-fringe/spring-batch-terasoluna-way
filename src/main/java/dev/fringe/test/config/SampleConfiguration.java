package dev.fringe.test.config;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.scope.StepScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@Configuration
@EnableBatchProcessing
public class SampleConfiguration {
	
	@Bean
	public SimpleAsyncTaskExecutor simpleTaskExecutor() {
	    return new SimpleAsyncTaskExecutor();
	}
	
	@Bean
	public StepScope stepScope() {
	    return new StepScope();
	}
}