package edu.indra.cursos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.indra.comun.entity.Alumno;
import edu.indra.comun.entity.Curso;
import edu.indra.cursos.repository.CursoRepository;

@Service
public class CursoServiceImpl implements CursoService {

	@Autowired
	CursoRepository cursoRepository;

	@Override
	@Transactional(readOnly = true)
	public Iterable<Curso> findAll() {
		return this.cursoRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Curso> findById(Long id) {
		return this.cursoRepository.findById(id);
	}

	@Override
	@Transactional
	public Curso save(Curso curso) {
		return this.cursoRepository.save(curso);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		this.cursoRepository.deleteById(id);

	}

	@Override
	@Transactional
	public Curso update(Curso curso, Long id) {
		Curso curso_mod = null;
		Optional<Curso> optional = null;
		Curso curso_leido = null;

		optional = this.cursoRepository.findById(id);
		if (optional.isPresent()) {
			curso_leido = optional.get();
			curso_leido.setNombre(curso.getNombre());

			curso_mod = curso_leido;
		}

		return curso_mod;
	}

	@Override
	@Transactional
	public Optional<Curso> asginarAlumnos(List<Alumno> alumnos, Long id) {
		Optional<Curso> optional = Optional.empty();

		// leer el curso
		optional = this.cursoRepository.findById(id);
		if (optional.isPresent()) {
			Curso curso_leido = optional.get();
			// asginarle los alumnos
			alumnos.forEach(a -> curso_leido.asginarAlumno(a));

			optional = Optional.of(curso_leido);

		}

		return optional;
	}

	@Override
	@Transactional
	public Optional<Curso> eliminarAlumno(Alumno alumno, Long id) {
		Optional<Curso> optional = Optional.empty();

			// leer el curso
			optional = this.cursoRepository.findById(id);
			if (optional.isPresent()) {
				Curso curso_leido = optional.get();
				// eliminar los alumnos
				curso_leido.borrarAlumno(alumno);
	
				optional = Optional.of(curso_leido);
	
			}

		return optional;
	}

	@Override
	@Transactional (readOnly = true)
	public Optional<Curso> obtenerCursoAlumno(Long id_alumno) {
		return this.cursoRepository.obtenerCursoAlumnoJPQL(id_alumno);
	}

}
