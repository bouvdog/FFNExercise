package testffnchallenge;

import agents.AgentDefault;
import distribution.AgentTaskPair;
import distribution.TaskDistributor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import tasks.AgentTask;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SpringBootTest(classes = TaskDistributor.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DistributionControllerTest {

    @Autowired
    private TestRestTemplate trt;

    @LocalServerPort
    int randomPort;

    @Test
    public void testAssignTaskEndPointSuccess() throws Exception {

        AgentTask task = new AgentTask(AgentTask.Priority.LOW);
        task.setSkill1(AgentDefault.Skills.SKILL1);

        HttpEntity<AgentTask> request = new HttpEntity<>(task);

        String url = "http://localhost:" + randomPort + "/assigntask";
        ResponseEntity<AgentTask> response = this.trt.postForEntity(url,
               request, AgentTask.class );
        assertEquals(202, response.getStatusCodeValue());
        assertTrue(response.getBody().getAgentId().length() > 0);
    }

    @Test
    public void givenATaskNoAgentCanFulfill_whenTaskAssigned_thenReturn503() throws Exception {

        // No agent has all three skills
        AgentTask task = new AgentTask(AgentTask.Priority.LOW);
        task.setSkill1(AgentDefault.Skills.SKILL1);
        task.setSkill2(AgentDefault.Skills.SKILL2);
        task.setSkill3(AgentDefault.Skills.SKILL3);

        HttpEntity<AgentTask> request = new HttpEntity<>(task);

        String url = "http://localhost:" + randomPort + "/assigntask";
        ResponseEntity<AgentTask> response = this.trt.postForEntity(url,
                request, AgentTask.class );
        assertEquals(503, response.getStatusCodeValue());
    }

    @Test
    public void givenATask_whenTaskSentToCompleteTaskEndPoint_thenReturn202() throws Exception {

        AgentTask task = new AgentTask(AgentTask.Priority.LOW);
        task.setSkill1(AgentDefault.Skills.SKILL1);

        HttpEntity<AgentTask> request = new HttpEntity<>(task);

        String url = "http://localhost:" + randomPort + "/completetask";
        ResponseEntity<String> response = this.trt.postForEntity(url,
                request, String.class );
        assertEquals(202, response.getStatusCodeValue());
    }

    @Test
    public void givenAssignedAgents_whenListAssignedAgentsEndpoint_thenReturn200() {
        AgentTask task = new AgentTask(AgentTask.Priority.LOW);
        task.setSkill1(AgentDefault.Skills.SKILL1);

        HttpEntity<AgentTask> request = new HttpEntity<>(task);

        String url = "http://localhost:" + randomPort + "/assigntask";
        ResponseEntity<AgentTask> response = this.trt.postForEntity(url,
                request, AgentTask.class );

        url = "http://localhost:" + randomPort + "/showassignedagents";
        List<AgentTaskPair> listOfAgents  = this.trt.getForObject(url, List.class);
        assertTrue(listOfAgents.size() > 0);
    }

    @Test
    public void smokeTest() {
        String url = "http://localhost:" + randomPort + "/hello";
        String response = this.trt.getForObject(url, String.class);
        assertEquals(response, "hello world");
    }
}
