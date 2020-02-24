package tasks;

import agents.AgentDefault;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

// The class was developed with the idea of having a Set of skills but this isn't straightforward to accomplish with
// sending the object to a REST POST endpoint.
// Given the amount of code that relies on the Set notion, we are going to leave that in place and also have a
// vanilla POJO pattern for the skills.
public class AgentTask {

    public enum Priority {HIGH, LOW}

    private String taskID;
    private Set<AgentDefault.Skills> requiredSkills;
    private AgentDefault.Skills skill1;
    private AgentDefault.Skills skill2;
    private AgentDefault.Skills skill3;
    private Priority priority;
    private String agentID;

    public AgentTask(Priority p) {
        taskID = UUID.randomUUID().toString();
        priority = p;
    }

    public AgentTask(Set<AgentDefault.Skills> skills, Priority p) {
        taskID = UUID.randomUUID().toString();
        requiredSkills = skills;
        priority = p;
    }

    public AgentTask() {
        taskID = UUID.randomUUID().toString();
    }

    public void setSkill1(AgentDefault.Skills skill) {
        skill1 = skill;
    }

    public void setSkill2(AgentDefault.Skills skill) {
        skill2 = skill;
    }

    public void setSkill3(AgentDefault.Skills skill) {
        skill3 = skill;
    }

    public AgentDefault.Skills getSkill1() {
        return skill1;
    }

    public AgentDefault.Skills getSkill2() {
        return skill2;
    }

    public AgentDefault.Skills getSkill3() {
        return skill3;
    }


    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public void setPriority(Priority p) {
        this.priority = p;
    }

    // This is a hack. Called in the controller after the Task object is deserialized.
    public void populateSet() {
        requiredSkills = new HashSet<>();
        if (skill1 != null) {
            requiredSkills.add(skill1);
        }
        if (skill2 != null) {
            requiredSkills.add(skill2);
        }
        if (skill3 != null) {
            requiredSkills.add(skill3);
        }
    }

    public Set<AgentDefault.Skills> requiredSkills() {
        return requiredSkills;
    }

    public void setAgentId(String agentID) {
        this.agentID = agentID;
    }

    public String getAgentId() {
        return agentID;
    }

    public Priority getPriority() {
        return priority;
    }

    public String getID() {
        return taskID;
    }

    @Override
    public String toString() {
        String skills = skill1 + " " + skill2 + " " + skill3;
        return "Task: " + taskID + " with Priority: " + priority + " with skills: " + skills;
    }

}
