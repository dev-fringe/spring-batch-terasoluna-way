package dev.fringe.test.tasklet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Component
@Scope("step")
public class SampleTasklet implements Tasklet {

	private static final Logger log = LogManager.getLogger(SampleTasklet.class.getName());
	@Autowired ItemStreamReader<Object> sampleReader;
	@Autowired PlatformTransactionManager jobTransactionManager;
	@Value("#{stepExecutionContext[name]}") private String threadName;

	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
		TransactionStatus status = null;
		sampleReader.open(chunkContext.getStepContext().getStepExecution().getExecutionContext());
		Object item = null;
		int count = 0;
		try {
			while ((item = sampleReader.read()) != null) {
				log.info("item = " + item + ", count = " + count);
				if (count == 0) {
					status = jobTransactionManager.getTransaction(definition); // (3)
				}
				count++;
			}
			jobTransactionManager.commit(status); // (4)
		} catch (Exception e) {
			jobTransactionManager.rollback(status);
			throw e;
		} finally {
			if (status != null && !status.isCompleted()) {
				jobTransactionManager.commit(status); // (6)
			}
			sampleReader.close();
		}
		log.info(threadName + " is sample done");
		return RepeatStatus.FINISHED;
	}
}