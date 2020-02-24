package distribution;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "agents", "tasks", "distribution"} )
public class TaskDistributor {

    public static void main(String[] args) {
        SpringApplication.run(TaskDistributor.class, args);
    }
}
