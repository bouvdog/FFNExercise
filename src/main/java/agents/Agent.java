package agents;

import tasks.AgentTask;
import tasks.AgentTask;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

public interface Agent {

    Optional<AgentTask> assign(AgentTask task);

    String getAgentHandle();

    Set<AgentDefault.Skills> whatAreAgentsSkills();

    LocalDateTime getTaskStartTime();

    boolean isAvailable();

    AgentTask.Priority currentTaskPriority();

    String getCurrentTaskID();

    void completeTask();

}
