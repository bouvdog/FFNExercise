package agents;

import tasks.AgentTask;

import java.time.LocalDateTime;
import java.util.*;

public class AgentDefault implements Agent {

    // If a skill gains any responsibilities, then we can extract this enum and turn it into a class.
    // My decision to put the enum for skills in the Agent class is a bit arbitrary. Skills are relevant
    // to both agents and tasks but I didn't think putting them into their package was worth it.
    public enum Skills { SKILL1, SKILL2, SKILL3}

    private String id;
    private Set<Skills> skills;
    private AgentTask currentTask;
    private LocalDateTime timeTaskStarted;

    private AgentDefault(final Set<Skills> skills){
        id = UUID.randomUUID().toString();
        this.skills = skills;
        timeTaskStarted = LocalDateTime.now();
    }

    // Varargs seemed a better choice than passing in a set of skills
    // However, if performance of varargs is a concern, then we can require a set of skills
    // or have multiple creation signatures.
    public static Agent create(final Skills firstSkill, final Skills... restOfSkills) {
        Set<Skills> agentSkills = new HashSet<>();
        agentSkills.add(firstSkill);
        for (Skills s : restOfSkills) {
            agentSkills.add(s);
        }
        return new AgentDefault(agentSkills);
    }

    @Override
    public String getAgentHandle() {
        return null;
    }

    @Override
    public boolean canHandle(final AgentTask task) {
        boolean acceptedTask = false;
        if (currentTask == null ) {
            Set<Skills> required = task.requiredSkills();
            if (skills.containsAll(required)) {
                currentTask = task;
                acceptedTask = true;
            }
        }
        return acceptedTask;
    }
    
    @Override
    public LocalDateTime getTaskStartTime() {
        return timeTaskStarted;
    }


}
