package testffnchallenge;

import agents.Agent;
import agents.AgentDefault;
import distribution.Distributor;
import distribution.DistributorDefault;
import org.junit.jupiter.api.Test;
import tasks.AgentTask;
import tasks.AgentTaskDefault;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestTaskDistributor {

    @Test
    public void givenEmptyDistributor_whenSentATask_thenAcceptTheTask() {
        Distributor dist = DistributorDefault.getInstance();

        Set<AgentDefault.Skills> skillsForTask = new HashSet<>();
        skillsForTask.add(AgentDefault.Skills.SKILL1);
        AgentTask task = new AgentTaskDefault(skillsForTask, AgentTaskDefault.Priority.HIGH);
        assertTrue(dist.assign(task));
    }

    @Test
    public void givenTask_whenSentATaskThatNoAgentCanFulfill_thenReturnFalse() {
        Distributor dist = DistributorDefault.getInstance();

        Set<AgentDefault.Skills> skillsForTask = new HashSet<>();
        skillsForTask.add(AgentDefault.Skills.SKILL1);

        List<Agent> qualifiedAgents = dist.agentsThatCanHandleThisTaskMix(AgentDefault.Skills.SKILL1);
        int numberOfAgents = qualifiedAgents.size();
        while (numberOfAgents > 0) {
            assertTrue(dist.assign(new AgentTaskDefault(skillsForTask, AgentTaskDefault.Priority.HIGH)));
            numberOfAgents--;
        }
        AgentTask task = new AgentTaskDefault(skillsForTask, AgentTaskDefault.Priority.HIGH);
        assertFalse(dist.assign(task));

        qualifiedAgents = dist.agentsThatCanHandleThisTaskMix(AgentDefault.Skills.SKILL1,
                AgentDefault.Skills.SKILL2, AgentDefault.Skills.SKILL3);
        assertEquals(0, qualifiedAgents.size());
    }
}
