package edu.indra.cursos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.PathVariable;

import edu.indra.comun.entity.Alumno;
import edu.indra.comun.entity.Curso;


public interface CursoService {
	
	public Iterable<Curso> findAll ();
	
	public Optional<Curso> findById (Long id);
	
	public Curso save (Curso curso);
	
	public void deleteById (Long id);
	
	public Curso update (Curso curso, Long id);
	
	public Optional<Curso> asginarAlumnos (List<Alumno> alumnos, Long id); 
	
	public Optional<Curso> eliminarAlumno (Alumno alumno, Long id); 
	
	public Optional<Curso> obtenerCursoAlumno(Long id_alumno);
	
	
	

}
