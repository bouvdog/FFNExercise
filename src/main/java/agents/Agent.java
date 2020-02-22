package agents;

import tasks.AgentTask;

import java.time.LocalDateTime;

public interface Agent {

    boolean canHandle(AgentTask task);

    String getAgentHandle();

    LocalDateTime getTaskStartTime();

}
