package agents;

import tasks.AgentTask;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class AgentDefault implements Agent {

    // If a skill gains any responsibilities, then we can extract this enum and turn it into a class.
    // My decision to put the enum for skills in the Agent class is a bit arbitrary. Skills are relevant
    // to both agents and tasks but I didn't think putting them into their package was worth it.
    public enum Skills { SKILL1, SKILL2, SKILL3}

    private String id;
    private Set<Skills> skills;
    private AgentTask currentTask;

    private AgentDefault(final Set<Skills> skills){
        id = UUID.randomUUID().toString();
        this.skills = skills;
    }

    public static Agent create(final Set<Skills> skills) {
        return new AgentDefault(skills);
    }

    @Override
    public String getAgentHandle() {
        return null;
    }

    @Override
    public boolean canHandle(final AgentTask task) {
        Set<Skills> required = task.requiredSkills();
        return this.skills.containsAll(required);
    }

    @Override
    public boolean assign(AgentTask task) {
        return false;
    }
}
