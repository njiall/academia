package edu.indra.alumnos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
//import org.springframework.context.annotation.ComponentScan;
import org.springframework.cloud.openfeign.EnableFeignClients;

//@ComponentScan //debería usarlo si el Controller, Service o Repository están fuera del paquete raíz edu.indra.alumnos
@SpringBootApplication
@EnableEurekaClient
@EntityScan("edu.indra.comun")//al mover del paquete raíz del proyecto, hay que indicarle dónde están las entidades (paquete)
@EnableFeignClients//HABILITAR EL CLIENTE FEGIN - para comunicar con servicios de la misma nube-
public class MsalumnosApplication {
	
	/**
	 * 
	 * PARA APUNTAR /REGISTRARSE CON EUREKA
	 * 1) ADD DEPENDECIA EUREKACLIENT (ADD STARTER)
	 * 2) ANOTAMOS CON ENABLEEUREKACLIENT EL MAIN
	 * 3) CONFIGURAMOS PROPIEDADES EXTRAS PROPIAS DE EUREKA
	 */

	public static void main(String[] args) {
		SpringApplication.run(MsalumnosApplication.class, args);
	}

}
