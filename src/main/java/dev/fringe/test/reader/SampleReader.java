package dev.fringe.test.reader;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("step")
public class SampleReader implements ItemStreamReader<Object> {
    
	private static final Logger log = LogManager.getLogger(SampleReader.class.getName());

    protected volatile List<Object> objects;
    @Value("#{stepExecutionContext[name]}") private String threadName;
    
    private int index = 0;

	public Object read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        Object objs = new Object();
        if (objects == null) { 
        	log.info("threadName = " + threadName);
            objects = new ArrayList<>();
            objects.add(new Object());
            objects.add(new Object());            
            objects.add(new Object());
        }
        if (objects == null) {
            objs = null;
        }
        if (index < objects.size()) {
            objs = objects.get(index);
            index++;
        } else {
            objs = null;
        }
        return objs;
	}
	
	public void open(ExecutionContext executionContext) throws ItemStreamException {}
	
	public void update(ExecutionContext executionContext) throws ItemStreamException {}
	
	public void close() throws ItemStreamException {}
}
