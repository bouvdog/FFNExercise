package testffnchallenge;

import agents.Agent;
import agents.AgentDefault;
import distribution.Distributor;
import distribution.DistributorDefault;
import org.junit.jupiter.api.Test;
import tasks.AgentTask;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestTaskDistributor {

    @Test
    public void givenEmptyDistributor_whenSentATask_thenAcceptTheTask() {
        Distributor dist = new DistributorDefault();
        Set<AgentDefault.Skills> skillsForTask = new HashSet<>();
        skillsForTask.add(AgentDefault.Skills.SKILL1);
        AgentTask task = new AgentTask(skillsForTask, AgentTask.Priority.HIGH);
        assertTrue(dist.assign(task));
    }

    @Test
    public void givenTask_whenSentATaskThatNoAgentCanFulfill_thenReturnFalse() {
        Distributor dist = new DistributorDefault();

        Set<AgentDefault.Skills> skillsForTask = new HashSet<>();
        skillsForTask.add(AgentDefault.Skills.SKILL1);

        List<Agent> qualifiedAgents = dist.agentsThatCanHandleThisTaskMix(AgentDefault.Skills.SKILL1);
        int numberOfAgents = qualifiedAgents.size();
        while (numberOfAgents > 0) {
            assertTrue(dist.assign(new AgentTask(skillsForTask, AgentTask.Priority.HIGH)));
            numberOfAgents--;
        }

        AgentTask task = new AgentTask(skillsForTask, AgentTask.Priority.HIGH);
        assertFalse(dist.assign(task));

        // No agent has all three skills
        qualifiedAgents = dist.agentsThatCanHandleThisTaskMix(AgentDefault.Skills.SKILL1,
                AgentDefault.Skills.SKILL2, AgentDefault.Skills.SKILL3);
        assertEquals(0, qualifiedAgents.size());
    }

    @Test
    public void givenAllAgentsBusy_whenSentALowPriorityTask_thenDoNotAcceptTask() {
        Distributor dist = new DistributorDefault();

        Set<AgentDefault.Skills> skillsForTask1 = new HashSet<>();
        skillsForTask1.add(AgentDefault.Skills.SKILL1);
        AgentTask t1 = new AgentTask(skillsForTask1, AgentTask.Priority.LOW);

        Set<AgentDefault.Skills> skillsForTask2 = new HashSet<>();
        skillsForTask2.add(AgentDefault.Skills.SKILL2);
        AgentTask t2 = new AgentTask(skillsForTask2, AgentTask.Priority.LOW);

        Set<AgentDefault.Skills> skillsForTask3 = new HashSet<>();
        skillsForTask3.add(AgentDefault.Skills.SKILL3);
        AgentTask t3 = new AgentTask(skillsForTask3, AgentTask.Priority.LOW);

        Set<AgentDefault.Skills> skillsForTask4 = new HashSet<>();
        skillsForTask4.add(AgentDefault.Skills.SKILL1);
        skillsForTask4.add(AgentDefault.Skills.SKILL2);
        AgentTask t4 = new AgentTask(skillsForTask4, AgentTask.Priority.LOW);

        Set<AgentDefault.Skills> skillsForTask5 = new HashSet<>();
        skillsForTask5.add(AgentDefault.Skills.SKILL1);
        skillsForTask5.add(AgentDefault.Skills.SKILL3);
        AgentTask t5 = new AgentTask(skillsForTask5, AgentTask.Priority.LOW);

        Set<AgentDefault.Skills> skillsForTask6 = new HashSet<>();
        skillsForTask6.add(AgentDefault.Skills.SKILL3);
        skillsForTask6.add(AgentDefault.Skills.SKILL2);
        AgentTask t6 = new AgentTask(skillsForTask6, AgentTask.Priority.LOW);

        Set<AgentDefault.Skills> skillsForTask7 = new HashSet<>();
        skillsForTask7.add(AgentDefault.Skills.SKILL3);
        AgentTask t7 = new AgentTask(skillsForTask7, AgentTask.Priority.LOW);

        Set<AgentDefault.Skills> skillsForTask8 = new HashSet<>();
        skillsForTask8.add(AgentDefault.Skills.SKILL1);
        skillsForTask8.add(AgentDefault.Skills.SKILL2);
        AgentTask t8 = new AgentTask(skillsForTask8, AgentTask.Priority.LOW);

        assertTrue(dist.assign(t1));
        assertTrue(dist.assign(t2));
        assertTrue(dist.assign(t3));
        assertTrue(dist.assign(t4));
        assertTrue(dist.assign(t5));
        assertTrue(dist.assign(t6));
        assertTrue(dist.assign(t7));
        assertTrue(dist.assign(t8));

        // All agents are busy and will not accept a new low priority task
        assertFalse(dist.assign(t1));
    }

    @Test
    public void givenAllAgentsBusy_whenSentAHighPriorityTask_thenAcceptTaskBumpingALowPriorityTask() throws Exception {
        Distributor dist = new DistributorDefault();

        Set<AgentDefault.Skills> skillsForTask1 = new HashSet<>();
        skillsForTask1.add(AgentDefault.Skills.SKILL1);
        AgentTask t1 = new AgentTask(skillsForTask1, AgentTask.Priority.LOW);

        Set<AgentDefault.Skills> skillsForTask2 = new HashSet<>();
        skillsForTask2.add(AgentDefault.Skills.SKILL2);
        AgentTask t2 = new AgentTask(skillsForTask2, AgentTask.Priority.LOW);

        Set<AgentDefault.Skills> skillsForTask3 = new HashSet<>();
        skillsForTask3.add(AgentDefault.Skills.SKILL3);
        AgentTask t3 = new AgentTask(skillsForTask3, AgentTask.Priority.LOW);

        Set<AgentDefault.Skills> skillsForTask4 = new HashSet<>();
        skillsForTask4.add(AgentDefault.Skills.SKILL1);
        skillsForTask4.add(AgentDefault.Skills.SKILL2);
        AgentTask t4 = new AgentTask(skillsForTask4, AgentTask.Priority.LOW);

        Set<AgentDefault.Skills> skillsForTask5 = new HashSet<>();
        skillsForTask5.add(AgentDefault.Skills.SKILL1);
        skillsForTask5.add(AgentDefault.Skills.SKILL3);
        AgentTask t5 = new AgentTask(skillsForTask5, AgentTask.Priority.LOW);

        Set<AgentDefault.Skills> skillsForTask6 = new HashSet<>();
        skillsForTask6.add(AgentDefault.Skills.SKILL3);
        skillsForTask6.add(AgentDefault.Skills.SKILL2);
        AgentTask t6 = new AgentTask(skillsForTask6, AgentTask.Priority.LOW);

        Set<AgentDefault.Skills> skillsForTask7 = new HashSet<>();
        skillsForTask7.add(AgentDefault.Skills.SKILL3);
        AgentTask t7 = new AgentTask(skillsForTask7, AgentTask.Priority.LOW);

        Set<AgentDefault.Skills> skillsForTask8 = new HashSet<>();
        skillsForTask8.add(AgentDefault.Skills.SKILL1);
        skillsForTask8.add(AgentDefault.Skills.SKILL2);
        AgentTask t8 = new AgentTask(skillsForTask8, AgentTask.Priority.LOW);

        // want to leave some time between task assignments to make verifying bumping easier to verify. I did this
        // verification using the debugger and not a unit test.
        assertTrue(dist.assign(t1));
        Thread.sleep(1000);
        assertTrue(dist.assign(t2));
        Thread.sleep(1000);
        assertTrue(dist.assign(t3));
        Thread.sleep(1000);
        assertTrue(dist.assign(t4));
        Thread.sleep(1000);
        assertTrue(dist.assign(t5));
        Thread.sleep(1000);
        assertTrue(dist.assign(t6));
        Thread.sleep(1000);
        assertTrue(dist.assign(t7));
        Thread.sleep(1000);
        assertTrue(dist.assign(t8));
        Thread.sleep(1000);

        // All agents are busy and will not accept a new low priority task but should accept a high priority task
        Set<AgentDefault.Skills> skillsForTask9 = new HashSet<>();
        skillsForTask9.add(AgentDefault.Skills.SKILL1);
        AgentTask t9 = new AgentTask(skillsForTask1, AgentTask.Priority.HIGH);
        assertTrue(dist.assign(t9));
    }

    @Test
    public void givenSomeAgentsBusy_whenQueryForListOfBusyAgents_thenReturnAListOfAgentsWithTasks() {
        Distributor dist = new DistributorDefault();

        Set<AgentDefault.Skills> skillsForTask1 = new HashSet<>();
        skillsForTask1.add(AgentDefault.Skills.SKILL1);
        AgentTask t1 = new AgentTask(skillsForTask1, AgentTask.Priority.LOW);

        Set<AgentDefault.Skills> skillsForTask2 = new HashSet<>();
        skillsForTask2.add(AgentDefault.Skills.SKILL2);
        AgentTask t2 = new AgentTask(skillsForTask2, AgentTask.Priority.LOW);

        Set<AgentDefault.Skills> skillsForTask3 = new HashSet<>();
        skillsForTask3.add(AgentDefault.Skills.SKILL3);
        AgentTask t3 = new AgentTask(skillsForTask3, AgentTask.Priority.LOW);

        assertTrue(dist.assign(t1));
        assertTrue(dist.assign(t2));
        assertTrue(dist.assign(t3));

        assertEquals(3, dist.snapShotOfAgentsWithTasks().size());

    }

    @Test
    public void givenSomeAgentsBusy_whenATaskIsCompleted_thenAgentBecomesAvailable() {
        Distributor dist = new DistributorDefault();

        Set<AgentDefault.Skills> skillsForTask1 = new HashSet<>();
        skillsForTask1.add(AgentDefault.Skills.SKILL1);
        AgentTask t1 = new AgentTask(skillsForTask1, AgentTask.Priority.LOW);

        Set<AgentDefault.Skills> skillsForTask2 = new HashSet<>();
        skillsForTask2.add(AgentDefault.Skills.SKILL2);
        AgentTask t2 = new AgentTask(skillsForTask2, AgentTask.Priority.LOW);

        Set<AgentDefault.Skills> skillsForTask3 = new HashSet<>();
        skillsForTask3.add(AgentDefault.Skills.SKILL3);
        AgentTask t3 = new AgentTask(skillsForTask3, AgentTask.Priority.LOW);

        assertTrue(dist.assign(t1));
        assertTrue(dist.assign(t2));
        assertTrue(dist.assign(t3));

        assertEquals(3, dist.snapShotOfAgentsWithTasks().size());

        dist.completeTask(t1.getID());

        assertEquals(2, dist.snapShotOfAgentsWithTasks().size());

    }
}
