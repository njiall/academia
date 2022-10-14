package edu.indra.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class MseurekaprofeApplication {
	
	/**
	 * PASOS PARA DEFINIR EUREKA
	 * 
	 * 1) SPRING STARTER PROJECT --> ADD DEPENDENCIA DE EUREKA SERVER
	 * 2) ADD DEPEDENCIA DE GLASSFISH JAXB
	 * 3) ANOTACIÓN ENABLE EUREKA SERVER EN EL MAIN
	 * 4) PROPERTIES
	 * 
	 */

	public static void main(String[] args) {
		SpringApplication.run(MseurekaprofeApplication.class, args);
	}

}
