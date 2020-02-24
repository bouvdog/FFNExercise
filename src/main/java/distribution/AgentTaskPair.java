package distribution;

public class AgentTaskPair {
    private String agentID;
    private String taskID;

    public AgentTaskPair() {}

    public void setAgentID(String agentID) {
        this.agentID = agentID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public String getAgentID() {
        return agentID;
    }

    public String getTaskID() {
        return taskID;
    }

    @Override
    public String toString() {
        return "Agent: " + agentID + " is assigned to task: " + taskID;
    }
}
