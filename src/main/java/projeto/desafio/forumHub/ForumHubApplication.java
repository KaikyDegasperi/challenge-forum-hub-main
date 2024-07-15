package projeto.desafio.forumHub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ForumHubApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForumHubApplication.class, args);
	}

}
