package tasks;

import agents.AgentDefault;

import java.util.Set;

public interface AgentTask {

    Set<AgentDefault.Skills> requiredSkills();

    void setAgentId(String id);

    AgentTaskDefault.Priority getPriority();

    String getID();
}
