package edu.indra.alumnos.cliente;

import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import edu.indra.comun.entity.Curso;

@FeignClient(name = "mscursos")
public interface CursoFeignClient {
	
	@GetMapping("/curso/obtenerCursoAlumno/{idalumno}") 
	public Optional<Curso> obtenerCursoAlumno(@PathVariable Long idalumno);

}
