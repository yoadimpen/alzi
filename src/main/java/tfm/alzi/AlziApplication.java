package tfm.alzi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("tfm.alzi.models")
public class AlziApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlziApplication.class, args);
	} 

}
