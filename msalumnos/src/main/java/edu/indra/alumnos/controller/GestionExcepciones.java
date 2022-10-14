package edu.indra.alumnos.controller;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Esta clase actúa como sumidero de los errores en este microservicio
 * "GESTION DE CENTRALIZADA DE EXCEPCIONES"
 * @author valer
 *
 */

@RestControllerAdvice(basePackages = {"edu.indra.alumnos"})//esta clase "escucha" todos los fallos dentro de este paquete
public class GestionExcepciones {

	//TODO definir un método para cada tipo de excepción que quiero gestionar
	
	@ExceptionHandler(org.springframework.dao.EmptyResultDataAccessException.class)
	public ResponseEntity<?> errorBorradoIdNoExiste (EmptyResultDataAccessException excepcion_borrado)
	{
		ResponseEntity<?> responseEntity = null;
		
			String mensaje_error = "ERROR al BORRAR ID NO existe - " + excepcion_borrado.getMessage();
			responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensaje_error);
		
		return responseEntity;
	}
	
	@ExceptionHandler(Throwable.class)//"GESTIÓN DE EXCEPCIÓN COMODÍN: si el fallo no coincide con ninguna excepción más concreta, se mete por aquí
	public ResponseEntity<?> errorGeneral (Throwable excepcion_generica)
	{
		ResponseEntity<?> responseEntity = null;
		
			String mensaje_error = "HA OCURRIDO UN ERROR - " + excepcion_generica.getMessage();
			excepcion_generica.printStackTrace();
			responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensaje_error);
		
		return responseEntity;
	}
}
