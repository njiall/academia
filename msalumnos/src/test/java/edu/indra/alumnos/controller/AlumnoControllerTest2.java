package edu.indra.alumnos.controller;

import org.junit.jupiter.api.Test;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import edu.indra.comun.entity.Alumno;
import util.UtilTest;

@SpringBootTest
@AutoConfigureMockMvc // mockeo el servidor
public class AlumnoControllerTest2 {
	
	/**
	 *  La idea es testear una api REST de forma «interna» sin tener que desplegar la aplicación en un servidor, 
	 *  pero también de forma realista efectuando las llamadas a la misma tal y como lo harían los consumidores del servicio.
	 */
	
	@Autowired
	private MockMvc mockMvc; // el objeto con el que lanzamos las peticiones HTTP
	
	
	@Test
	public void insertarAlumnoTest() throws Exception {
		Alumno alumno = new Alumno();
		alumno.setNombre("Juan");
		alumno.setApellido("Moreno");
		alumno.setEdad(22);
		alumno.setEmail("juanmo@mail.es");

		// serializar este alumno
		String json_alumno = UtilTest.toJSON(alumno);

		mockMvc.perform(post("/alumno").contentType(MediaType.APPLICATION_JSON).content(json_alumno))
				.andExpect(status().isCreated()).andExpect(content().contentType("application/json"));

	}

}