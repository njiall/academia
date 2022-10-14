package edu.indra.cursos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EntityScan("edu.indra.comun")
public class MscursosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MscursosApplication.class, args);
	}

}
