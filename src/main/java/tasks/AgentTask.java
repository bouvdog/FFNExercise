package tasks;

import agents.AgentDefault;

import java.util.Set;

public interface AgentTask {

    Set<AgentDefault.Skills> requiredSkills();
}
