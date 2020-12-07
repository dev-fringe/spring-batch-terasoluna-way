package dev.fringe.test.config;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@Configuration
@EnableBatchProcessing
@ImportResource("classpath:sample-job.xml")
@ComponentScan("dev.fringe.sample")
public class SampleConfiguration {
	@Bean
	public SimpleAsyncTaskExecutor simpleTaskExecutor() {
	    return new SimpleAsyncTaskExecutor();
	}
}