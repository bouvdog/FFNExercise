package testffnchallenge;

import agents.Agent;
import agents.AgentDefault;
import org.junit.jupiter.api.Test;
import tasks.AgentTask;
import tasks.AgentTaskDefault;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class TestAgent {

    @Test
    public void givenTask_whenAgentHasAppropriateSkills_thenReturnTrue() {
        Set<AgentDefault.Skills> skills = new HashSet<>();
        skills.add(AgentDefault.Skills.SKILL1);
        AgentTask t = AgentTaskDefault.create(skills, AgentTaskDefault.Priority.HIGH);

        Set<AgentDefault.Skills> agentSkills = new HashSet<>();
        agentSkills.add(AgentDefault.Skills.SKILL1);
        Agent a = AgentDefault.create(agentSkills);
        assertTrue(a.canHandle(t));
    }
}
