package distribution;

import agents.Agent;
import agents.AgentDefault;
import tasks.AgentTask;
import tasks.AgentTaskDefault;

import java.util.*;
import java.util.stream.Collectors;

// TODO: figure out how to have multiple controllers use this distributor
public class DistributorDefault implements Distributor {

    private static DistributorDefault instance;

    private List<Agent> agents = new ArrayList<>();

    private DistributorDefault() {
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

    public static DistributorDefault getInstance() {
        if (instance == null) {
            instance = new DistributorDefault();
        }
        return instance;
    }

    @Override
    public boolean assign(AgentTask task) {
        List<Agent> listOfQualifedAgents = agents.stream()
                .filter(s -> s.whatAreAgentsSkills().containsAll(task.requiredSkills()))
                .collect(Collectors.toList());
        if (listOfQualifedAgents.size() == 0) {
            return false;
        }

        List<Agent> listOfFreeAgents = agents.stream()
                .filter(s -> s.isAvailable())
                .collect(Collectors.toList());

        if (listOfFreeAgents.size() == 0) {
            if (task.getPriority() == AgentTaskDefault.Priority.LOW) {
                // there are no free agents and no task that can be bumped
                // return error
                return false;
            } else {
                // High priority task that needs to bump a low priority task
                List<Agent> agentsWithLowPriorityTasks = agents.stream()
                        .filter(s -> !s.isAvailable())
                        .filter(s->s.currentTaskPriority() == AgentTaskDefault.Priority.LOW)
                        .sorted(Comparator.comparing(Agent::getTaskStartTime))
                        .collect(Collectors.toList());
                Optional<AgentTask> bumped = agentsWithLowPriorityTasks.get(0).assign(task);
                // assign bumped task to a bumped task list. One idea could be to reassign bumped tasks
                // after a call is made to complete a task.
            }
        } else {
            listOfFreeAgents.get(0).assign(task);
        }
        return true;
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
        List<Agent> listOfAvailableAgents  = agents.stream()
                .filter(s -> s.isAvailable())
                .collect(Collectors.toList());
        return listOfAvailableAgents;
    }
}
