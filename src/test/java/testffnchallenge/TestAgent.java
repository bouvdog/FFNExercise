package testffnchallenge;

import agents.Agent;
import agents.AgentDefault;
import org.junit.jupiter.api.Test;
import tasks.AgentTask;
import tasks.AgentTaskDefault;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestAgent {

    @Test
    public void givenTask_whenAgentHasAppropriateSkills_thenReturnTrue() {
        Set<AgentDefault.Skills> skills = new HashSet<>();
        skills.add(AgentDefault.Skills.SKILL1);
        AgentTask task = AgentTaskDefault.create(skills, AgentTaskDefault.Priority.HIGH);

        Agent a = AgentDefault.create(AgentDefault.Skills.SKILL1);
        assertTrue(a.canHandle(task));

        Agent b = AgentDefault.create(AgentDefault.Skills.SKILL2, AgentDefault.Skills.SKILL1);
        assertTrue(b.canHandle(task));
    }

    @Test
    public void givenTask_whenAgentLacksAppropriateSkills_thenReturnFalse() {
        Set<AgentDefault.Skills> skills = new HashSet<>();
        skills.add(AgentDefault.Skills.SKILL1);
        AgentTask t = AgentTaskDefault.create(skills, AgentTaskDefault.Priority.HIGH);

        Agent a = AgentDefault.create(AgentDefault.Skills.SKILL2);
        assertFalse(a.canHandle(t));
    }

    @Test
    public void givenAgentWithTask_whenAskedToPerformAnotherTask_thenDeclineTask() {
        Set<AgentDefault.Skills> skills = new HashSet<>();
        skills.add(AgentDefault.Skills.SKILL1);
        AgentTask task = AgentTaskDefault.create(skills, AgentTaskDefault.Priority.HIGH);

        Agent a = AgentDefault.create(AgentDefault.Skills.SKILL1);
        assertTrue(a.canHandle(task));
        assertFalse(a.canHandle(task));
    }
}
