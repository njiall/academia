package edu.indra.cursos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.indra.comun.entity.Curso;

@Repository
public interface CursoRepository extends CrudRepository<Curso, Long> {

	// DADO UN IDE DE UN ALUMNO
	// A QUÉ CURSO ESTÁ ASGINADO ?¿

	// JPQL
	@Query("select c from Curso c JOIN c.alumnos l where l.id= ?1")
	public Optional<Curso> obtenerCursoAlumnoJPQL(Long id_alumno);

	// NATIVA
	@Query(value = "select * from cursos where id = "
			+ "(select curso_id from cursos_alumnos where alumnos_id = ?1)", nativeQuery = true)
	public Optional<Curso> obtenerCursoAlumnoNativa(Long id_alumno);
}
