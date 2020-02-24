package testffnchallenge;

import agents.AgentDefault;
import distribution.DistributionController;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import tasks.AgentTask;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TaskDistributorTest {

    @Test
    public void testDistributionController() {
        DistributionController dc = new DistributionController();

        Set<AgentDefault.Skills> requiredSkills = new HashSet<>();
        requiredSkills.add(AgentDefault.Skills.SKILL1);
        AgentTask task = new AgentTask(requiredSkills, AgentTask.Priority.LOW);
        ResponseEntity<AgentTask> response = dc.assignTask(task);
        assertEquals(response.getStatusCodeValue(), 202);
        assertTrue(response.getBody().getAgentId() != null);
    }

}
