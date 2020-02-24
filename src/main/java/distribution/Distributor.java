package distribution;

import agents.Agent;
import agents.AgentDefault;
import tasks.AgentTask;

import java.util.List;

public interface Distributor {

    boolean assign(AgentTask task);

    List<Agent> agentsThatCanHandleThisTaskMix(AgentDefault.Skills aSkill, AgentDefault.Skills... skills);

    List<Agent> freeAgents(List<Agent> agents);

    void completeTask(String taskID);

    List<Agent> snapShotOfAgentsWithTasks();
}
