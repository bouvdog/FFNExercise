package agents;

import tasks.AgentTask;

public interface Agent {

    boolean canHandle(AgentTask task);

    boolean assign(AgentTask task);

    String getAgentHandle();

}
