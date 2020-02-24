package distribution;

import agents.Agent;
import agents.AgentDefault;
import tasks.AgentTask;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

// Interestingly, if different distribution behaviors were desired, we could implement a strategy pattern
// and have different behaviors that we could inject into 'assign'.
public class DistributorDefault implements Distributor {

    private List<Agent> agents = new ArrayList<>();

    private List<AgentTask> bumpedTasks = new ArrayList<>();

    public DistributorDefault() {
        // A task that requires all three skills will be rejected as their is no agent with all three skills
        agents.add(AgentDefault.create(AgentDefault.Skills.SKILL1));
        agents.add(AgentDefault.create(AgentDefault.Skills.SKILL2));
        agents.add(AgentDefault.create(AgentDefault.Skills.SKILL3));
        agents.add(AgentDefault.create(AgentDefault.Skills.SKILL1, AgentDefault.Skills.SKILL2));
        agents.add(AgentDefault.create(AgentDefault.Skills.SKILL1, AgentDefault.Skills.SKILL3));
        agents.add(AgentDefault.create(AgentDefault.Skills.SKILL2, AgentDefault.Skills.SKILL3));

        // A couple of agents that have the same skills as the ones above
        agents.add(AgentDefault.create(AgentDefault.Skills.SKILL3));
        agents.add(AgentDefault.create(AgentDefault.Skills.SKILL1, AgentDefault.Skills.SKILL2));
    }

    // Not happy with the cyclomatic complexity here
    @Override
    public boolean assign(AgentTask task) {
        List<Agent> listOfQualifiedAgents = agents.stream()
                .filter(s -> s.whatAreAgentsSkills().containsAll(task.requiredSkills()))
                .collect(Collectors.toList());
        if (listOfQualifiedAgents.size() == 0) {
            return false;
        }

        List<Agent> listOfFreeAgents = listOfQualifiedAgents.stream()
                .filter(s -> s.isAvailable())
                .collect(Collectors.toList());

        // If all agents busy, look for a task to bump
        if (listOfFreeAgents.size() == 0) {
            if (task.getPriority() == AgentTask.Priority.LOW) {
                // Low priority task, no open agents, return error
                return false;
            } else {
                // High priority task, no open agents, look to see if we can bump a lower priority task
                List<Agent> agentsWithLowerPriorityTasks = listOfQualifiedAgents.stream()
                        .filter(s->!s.isAvailable())
                        .filter(s -> s.currentTaskPriority() == AgentTask.Priority.LOW)
                        .collect(Collectors.toList());
                if (agentsWithLowerPriorityTasks.size() > 0) {
                    bumpLowerPriorityTask(task);
                } else {
                    // High priority task, no agent with a low priority task, return error
                    return false;
                }
            }
        } else {
            assignTaskToAgent(task, listOfFreeAgents);
        }
        return true;
    }

    private void bumpLowerPriorityTask(AgentTask task) {
        // High priority task that needs to bump a low priority task
        List<Agent> agentsWithLowPriorityTasks = agents.stream()
                .filter(s -> !s.isAvailable())
                .filter(s -> s.currentTaskPriority() == AgentTask.Priority.LOW)
                .sorted(Comparator.comparing(Agent::getTaskStartTime).reversed())
                .collect(Collectors.toList());
        assignTaskToAgent(task, agentsWithLowPriorityTasks);
    }

    private void assignTaskToAgent(AgentTask task, List<Agent> lowPriority) {
        for (Agent a : agents) {
            Optional<AgentTask> bumped;
            String agentId = a.getAgentHandle();
            String selectedAgentId = lowPriority.get(0).getAgentHandle();
            if (agentId.equals(selectedAgentId)) {
                bumped = a.assign(task);
                if (bumped.isPresent()) {
                    bumpedTasks.add(bumped.get());
                }
                break;
            }
        }
    }


    @Override
    public List<Agent> agentsThatCanHandleThisTaskMix(final AgentDefault.Skills firstSkill,
                                                      final AgentDefault.Skills... otherSkills) {
        Set<AgentDefault.Skills> setOfTaskSkills = new HashSet<>();
        setOfTaskSkills.add(firstSkill);
        for (AgentDefault.Skills skill : otherSkills) {
            setOfTaskSkills.add(skill);
        }

        boolean test = agents.get(0).whatAreAgentsSkills().containsAll(setOfTaskSkills);

        List<Agent> listOfQualifedAgents = agents.stream()
                .filter(s -> s.whatAreAgentsSkills().containsAll(setOfTaskSkills))
                .collect(Collectors.toList());

        return listOfQualifedAgents;
    }

    @Override
    public List<Agent> freeAgents(List<Agent> agents) {
        List<Agent> listOfAvailableAgents = agents.stream()
                .filter(s -> s.isAvailable())
                .collect(Collectors.toList());
        return listOfAvailableAgents;
    }

    // When a task is completed, we check to see if there are any bumped tasks
    // available and then assign again, if there are.
    @Override
    public void completeTask(String taskID) {
        List<Agent> taskWithAgentToComplete = agents.stream()
                .filter(s -> !s.isAvailable())
                .filter(s -> s.getCurrentTaskID().equals(taskID))
                .collect(Collectors.toList());
        if (taskWithAgentToComplete.size() > 0) {
            taskWithAgentToComplete.get(0).completeTask();
            if (bumpedTasks.size() > 0) {
                assign(bumpedTasks.remove(0));
            }
        }
    }

    @Override
    public List<Agent> snapShotOfAgentsWithTasks() {
        List<Agent> listOfAgentsWithTasks = agents.stream()
                .filter(s -> !s.isAvailable())
                .collect(Collectors.toList());
        return listOfAgentsWithTasks;
    }
}
