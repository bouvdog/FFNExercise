package tasks;

import agents.AgentDefault;

import java.util.Set;
import java.util.UUID;

public class AgentTaskDefault implements AgentTask {

    public enum Priority {HIGH, LOW}

    private String taskID;
    private Set<AgentDefault.Skills> skills;
    private Priority priority;
    private String agentID;

    public AgentTaskDefault(Set<AgentDefault.Skills> skills, Priority p) {
        taskID = UUID.randomUUID().toString();
        this.skills = skills;
        priority = p;
    }

    @Override
    public Set<AgentDefault.Skills> requiredSkills() {
        return skills;
    }

    @Override
    public void setAgentId(String agentID) {
        this.agentID = agentID;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    @Override
    public String getID() {
        return taskID;
    }

}
