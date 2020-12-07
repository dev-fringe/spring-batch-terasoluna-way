package dev.fringe.test.partitioner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.stereotype.Component;

@Component
public class SamplePartitioner implements Partitioner {
    
	private static final Logger log = LogManager.getLogger(SamplePartitioner.class.getName());

    public Map<String, ExecutionContext> partition(int gridSize) {
        List<Map<String, Object>> projects = new ArrayList<>();
        try {
        	Map<String, Object> map = new HashMap<>();
        	map.put("projectCode", "a");
        	map.put("sapCode", "a");
        	projects.add(map);
        	Map<String, Object> map2 = new HashMap<>();
        	map2.put("projectCode", "b");
        	map2.put("sapCode", "b");
        	projects.add(map2);
        } catch (Exception e) {
            log.error("partition(int)", e); 
            e.printStackTrace();
        }
        
        Map<String, ExecutionContext> result = new HashMap<String, ExecutionContext>();
        for(Map<String, Object> project  : projects){
            ExecutionContext value = new ExecutionContext();
            result.put("partition" + projects.indexOf(project), value);
            try {
                value.putString("projectCode", (String)project.get("projectCode"));
                value.putString("sapCode", (String)project.get("sapCode"));
                value.putString("name", "Thread " + projects.indexOf(project));
            } catch (Exception e) {
                log.error("partition(int)", e); //$NON-NLS-1$
                e.printStackTrace();
            }
        }
        
        return result;
        
    }

}
