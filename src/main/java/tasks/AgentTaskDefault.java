package tasks;

import agents.AgentDefault;

import java.util.Set;
import java.util.UUID;

public class AgentTaskDefault implements AgentTask {

    public enum Priority {HIGH, LOW}

    private String id;
    private Set<AgentDefault.Skills> skills;
    private Priority priority;

    private AgentTaskDefault(Set<AgentDefault.Skills> skills, Priority p) {
        id = UUID.randomUUID().toString();
        this.skills = skills;
        priority = p;
    }

    public static AgentTask create(Set<AgentDefault.Skills> skills, Priority p) {
        return new AgentTaskDefault(skills, p);
    }

}
