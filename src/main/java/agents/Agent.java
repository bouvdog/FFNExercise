package agents;

import tasks.AgentTask;
import tasks.AgentTaskDefault;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

public interface Agent {

    Optional<AgentTask> assign(AgentTask task);

    String getAgentHandle();

    Set<AgentDefault.Skills> whatAreAgentsSkills();

    LocalDateTime getTaskStartTime();

    boolean isAvailable();

    AgentTaskDefault.Priority currentTaskPriority();

}
