package agents;

import tasks.AgentTask;
import tasks.AgentTask;

import java.time.LocalDateTime;
import java.util.*;

// There isn't much business logic in this class. It is almost a POJO.
// The distributor determines which agent is appropriate for an AgentTask and then
// assigns (tells) the agent to accept the task.
// A principle for classes is 'Tell, don't ask' but there is a lot of 'asking' methods in this 'class'.
//
// However, if we think about a contact center, whether the agents have the skills to handle a request/task
// isn't known to each agent. That is a property of the aggregated agents. Also, which agents are free and which
// are busy is not known at the individual agent level. But the Distributor of tasks would know this because it
// contains the aggregation of agents.
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
    }

    // Varargs seemed a better choice than passing in a set of skills
    // However, if performance of varargs is a concern, then we can require a Set of skills
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
    public Optional<AgentTask> assign(AgentTask task) {
        AgentTask bumpedTask = null;
        if (currentTask != null) {
            bumpedTask = currentTask;
        } else {
            currentTask = task;
        }
        task.setAgentId(id);
        timeTaskStarted = LocalDateTime.now();
        return Optional.ofNullable(bumpedTask);
    }

    @Override
    public String getAgentHandle() {
        return id;
    }

    @Override
    public Set<Skills> whatAreAgentsSkills() {
        return skills;
    }


    @Override
    public LocalDateTime getTaskStartTime() {
        return timeTaskStarted;
    }

    @Override
    public boolean isAvailable() {
        if (currentTask == null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public AgentTask.Priority currentTaskPriority() {
        return currentTask.getPriority();
    }

    @Override
    public String getCurrentTaskID() {
        return currentTask.getID();
    }

    @Override
    public void completeTask() {
        currentTask = null;
    }


}
