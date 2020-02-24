package distribution;

import agents.Agent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tasks.AgentTask;
import tasks.AgentTask;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class DistributionController {

    private Distributor dist = new DistributorDefault();

    @PostMapping(path="/assigntask")
    public ResponseEntity<AgentTask> assignTask(@RequestBody AgentTask task) {
        task.populateSet();
        if (dist.assign(task)) {
            List<Agent> agents = dist.snapShotOfAgentsWithTasks();
            List<Agent> assignedAgent = agents.stream()
                    .filter(a -> !a.isAvailable())
                    .filter(a -> a.getCurrentTaskID().equals(task.getID()))
                    .collect(Collectors.toList());
            task.setAgentId(assignedAgent.get(0).getAgentHandle());
        } else {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(task);
        }
        return ResponseEntity.accepted().body(task);
    }

    // Completing a task that doesn't exist, is a NOP. We return success regardless of whether the task
    // was assigned or not.
    @PostMapping("/completetask")
    public ResponseEntity<String> completeTask (@RequestBody AgentTask task) {
        dist.completeTask(task.getID());

        return ResponseEntity.accepted().body("Task: " + task.getID() + " has been completed");
    }

    @RequestMapping("/showassignedagents")
    public List<AgentTaskPair> retrieveAssignedAgents() {
        List<AgentTaskPair> assignedAgents = new ArrayList<>();
        List<Agent> agents = dist.snapShotOfAgentsWithTasks();
        for (Agent a : agents) {
            AgentTaskPair at = new AgentTaskPair();
            at.setAgentID(a.getAgentHandle());
            at.setTaskID(a.getCurrentTaskID());
            assignedAgents.add(at);
        }
        return assignedAgents;
    }

    // smoke test
    @RequestMapping("/hello")
    public String greeting() {
        return "hello world";
    }
}
