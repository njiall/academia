package edu.indra.cursos;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.indra.comun.entity.Alumno;
import edu.indra.comun.entity.Curso;
import edu.indra.cursos.service.CursoService;



@RestController
@RequestMapping("/curso")
public class CursoController {
	
	@Autowired
	CursoService cursoService;
	
	@GetMapping // GET http://localhost:8082/curso
	public ResponseEntity<?> obtenerListaCursos() {
		ResponseEntity<?> responseEntity = null;
		Iterable<Curso> listado_cursos = null;

		listado_cursos = this.cursoService.findAll();
		responseEntity = ResponseEntity.ok(listado_cursos);

		return responseEntity;
	}

	@GetMapping("/{id}") // GET http://localhost:8082/curso/2
	public ResponseEntity<?> obtenerAlumnoPorId(@PathVariable Long id) {
		ResponseEntity<?> responseEntity = null;
		Optional<Curso> optional = null;

		optional = this.cursoService.findById(id);
		if (optional.isPresent()) {
			Curso curso_leido = optional.get();
			responseEntity = ResponseEntity.ok(curso_leido);
		} else {
			responseEntity = ResponseEntity.noContent().build();// 204
		}

		return responseEntity;
	}

	
	
	@PostMapping // POST http://localhost:8082/curso
	public ResponseEntity<?> insertarCurso(@RequestBody Curso curso) {
		ResponseEntity<?> responseEntity = null;
		Curso curso_insertado = null;

		
			curso_insertado = this.cursoService.save(curso);
			responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(curso_insertado);// 201
			

		return responseEntity;
	}
	
	
	@DeleteMapping("/{id}") // DELETE http://localhost:8082/curso/id
	public ResponseEntity<?> eliminarCurso(@PathVariable Long id) {
		ResponseEntity<?> responseEntity = null;

		
		this.cursoService.deleteById(id);
		responseEntity = ResponseEntity.ok().build();

		

		return responseEntity;
	}
	
	@PutMapping("/{id}") // PUT http://localhost:8082/curso/id
	public ResponseEntity<?> modificarCurso(@RequestBody Curso curso, @PathVariable Long id) {
		ResponseEntity<?> responseEntity = null;
		Curso curso_mod = null;

	
			curso_mod = this.cursoService.update(curso, id);
			if (curso_mod != null) {
				responseEntity = ResponseEntity.ok(curso_mod);
			} else {
				responseEntity = ResponseEntity.notFound().build();// 404/
			}


		return responseEntity;
	}
	
	@PutMapping("/asignarAlumnos/{id}") // PUT http://localhost:8082/curso/asignarAlumnos/id
	public ResponseEntity<?> asignarAlumnos(@RequestBody List<Alumno> alumnos, @PathVariable Long id) {
		ResponseEntity<?> responseEntity = null;
		Optional<Curso> op_curso = null;

	
			op_curso = this.cursoService.asginarAlumnos(alumnos, id);
			if (op_curso.isPresent()) {
				Curso curso_mod = op_curso.get();
				responseEntity = ResponseEntity.ok(curso_mod);
			} else {
				responseEntity = ResponseEntity.notFound().build();// 404/
			}


		return responseEntity;
	}
	
	
	@PutMapping("/eliminarAlumno/{id}") // PUT http://localhost:8082/curso/asignarAlumnos/id
	public ResponseEntity<?> asignarAlumnos(@RequestBody Alumno alumno, @PathVariable Long id) {
		ResponseEntity<?> responseEntity = null;
		Optional<Curso> op_curso = null;

	
			op_curso = this.cursoService.eliminarAlumno(alumno, id);
			if (op_curso.isPresent()) {
				Curso curso_mod = op_curso.get();
				responseEntity = ResponseEntity.ok(curso_mod);
			} else {
				responseEntity = ResponseEntity.notFound().build();// 404/
			}


		return responseEntity;
	}
	
	
	@GetMapping("/obtenerCursoAlumno/{idalumno}") // GET http://localhost:8082/curso/obtenerCursoAlumno/2
	public ResponseEntity<?> obtenerCursoAlumno(@PathVariable Long idalumno) {
		ResponseEntity<?> responseEntity = null;
		Optional<Curso> optional = null;

		var saludo = "HOLA";
		saludo.charAt(20);
		
		optional = this.cursoService.obtenerCursoAlumno(idalumno);
		if (optional.isPresent()) {
			Curso curso_alumno = optional.get();
			responseEntity = ResponseEntity.ok(curso_alumno);
		} else {
			responseEntity = ResponseEntity.noContent().build();// 204
		}

		return responseEntity;
	}

}
