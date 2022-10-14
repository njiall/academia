package edu.indra.alumnos.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * EN ESTA CLASE, VAMOS A DEVOLVER UN ALUMNO CON LA TÉCNICA DE JSONP
 * ASÍ QUE DEVOLVEMOS UN ALUMNO EN FORMATO JSON
 * COMO PARÁEMTRO DE UNA FUNCIÓN JS
 * 
 * @author valer
 *
 */

@Controller
public class JSONPController {
	
	@Autowired
	ObjectMapper om;//pasar la info de un alumno a un objeto JSON
	
	@GetMapping("/jsonp/alumno")//http://localhost:8081/jsonp/alumno?callback=mifuncion"
	public void obtenerAlumnoJsonP (HttpServletRequest request, HttpServletResponse respuesta,
			@RequestParam(name = "callback", required = true) String callback) throws IOException {
		
		ObjectNode objectNode = om.createObjectNode();
		objectNode.put("nombre", "Nacho");
		objectNode.put("edad", 15);
		objectNode.put("email", "nacho@real.es");
		objectNode.put("apellido", "Fdez");
		objectNode.put("id", 37);
		
		String alumno_json = objectNode.toString();
		String cuerpo_respuesta = callback + "(" + alumno_json +")";
		
		//escribimos la respuesta
		respuesta.getWriter().print(cuerpo_respuesta);
		respuesta.setContentType("application/javascript;charset=UTF-8");
		
		
	}

}
