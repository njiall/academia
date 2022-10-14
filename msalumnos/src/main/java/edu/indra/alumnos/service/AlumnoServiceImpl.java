package edu.indra.alumnos.service;

import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import edu.indra.alumnos.cliente.CursoFeignClient;
import edu.indra.alumnos.controller.AlumnoController;
import edu.indra.alumnos.model.FraseChuckNorris;
import edu.indra.alumnos.repository.AlumnoRepository;
import edu.indra.comun.entity.Alumno;
import edu.indra.comun.entity.Curso;

@Service
public class AlumnoServiceImpl implements AlumnoService {
	
	@Autowired
	AlumnoRepository alumnoRepository;//acceda bd por este objeto
	
	@Autowired
	CursoFeignClient cursoFeignClient;
	
	Logger log = LoggerFactory.getLogger(AlumnoService.class);

	@Override
	@Transactional (readOnly = true)
	public Iterable<Alumno> findAll() {
		return this.alumnoRepository.findAll();
	}

	@Override
	@Transactional (readOnly = true)
	public Optional<Alumno> findById(Long id) {
		return this.alumnoRepository.findById(id);
	}

	@Override
	@Transactional
	public Alumno save(Alumno alumno) {
		
		return this.alumnoRepository.save(alumno);
	}

	@Override
	public void deleteById(Long id) {
		this.alumnoRepository.deleteById(id);
	}

	@Override
	@Transactional
	public Alumno update(Alumno alumno, Long id) {
		Alumno alumno_modificado = null;
		Optional<Alumno> optional = null;
		
				 	optional = this.alumnoRepository.findById(id);
				 	if (optional.isPresent())
				 	{
				 		Alumno alumno_leido = optional.get();//PERSISTENTE - si modifico un atributo, lo estoy haciendo en la columna de la tabla
				 		/*alumno_leido.setApellido(alumno.getApellido());
				 		alumno_leido.setEdad(alumno.getEdad());
				 		alumno_leido.setEmail(alumno.getEmail());
				 		alumno_leido.setNombre(alumno.getNombre());*/
				 		//copiamos todos los atributos de un bean a otro (en nuestro no queremos que nos copie, ni el id ni la fecha no lo usaramos)
				 		// BeanUtils.copyProperties(alumno, alumno_leido);
				 		//String[] propiedades_excluidas = {"id", "creadoEn"};
				 		//BeanUtils.copyProperties(alumno, alumno_leido, propiedades_excluidas);
				 		BeanUtils.copyProperties(alumno, alumno_leido, "id", "creadoEn");
				 		alumno_modificado = alumno_leido;
				 		
				 		
				 	}
		
		return alumno_modificado;
	}

	@Override
	public Optional<FraseChuckNorris> obtenerFraseAleaotoriaChuckNorris() {
		Optional<FraseChuckNorris> optional = Optional.empty();
		RestTemplate restTemplate = null;
		FraseChuckNorris fraseChuckNorris = null;
		
			
				restTemplate = new RestTemplate ();
				fraseChuckNorris = restTemplate.getForObject("https://api.chucknorris.io/jokes/random", FraseChuckNorris.class);
				optional = Optional.of(fraseChuckNorris);
		
		return optional;
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Alumno> findByEdadBetween(int edad_min, int edad_max) {
		
		return this.alumnoRepository.findByEdadBetween(edad_min, edad_max);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Alumno> findByNombreContaining(String patron) {
		
		return this.alumnoRepository.findByNombreContaining(patron);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Alumno> buscarAlumnosPorNombreOApellidoJPQL(String patron) {
		
		return this.alumnoRepository.buscarAlumnosPorNombreOApellidoJPQL(patron);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Iterable<Alumno> buscarAlumnosPorNombreOApellidoNativa(String patron) {
		
		return this.alumnoRepository.buscarAlumnosPorNombreOApellidoJPQL(patron);
	}

	@Override
	@Transactional //AUNQUE ESTOS PROCEDIMIENTOS NO MODIFIQUEN LA BASE DE DATOS, DEBE INDICARSE READONLY = FALSE (no poner nada, como si fuera a modificarse)
	public Iterable<Alumno> procedimientoAlumnosRegistradosHoy() {
		return this.alumnoRepository.procedimientoAlumnosRegistradosHoy();
	}

	@Override
	@Transactional
	public Map<String, Number> procedimientoEstadisticosEdad() {
		return this.alumnoRepository.procedimientoEstadisticosEdad(0, 0, 0f);
	}

	@Override
	@Transactional
	public Iterable<Alumno> procedimientoAlumnosNombreComo(String patron) {
		
		return this.alumnoRepository.procedimientoAlumnosNombreComo("%"+patron+"%");
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Alumno> findAll(Pageable pageable) {
		return this.alumnoRepository.findAll(pageable);
	}

	
	@Override
	@Transactional(readOnly = true)
	public Iterable<Alumno> findByEdadBetween(int edad_min, int edad_max, Pageable pageable) {
		
		return this.alumnoRepository.findByEdadBetween(edad_min, edad_max, pageable);
	}

	@Override
	public Page<Alumno> listarAlumnosPaginadosOrdenados(Pageable pageable) {
		return this.findAll(pageable);
	}

	@Override
	public Optional<Curso> obtenerCursoAlumnoViaFeing(Long idalumno) {
		Optional<Curso> opcurso = Optional.empty();
			
		try {
			//HARÍA MI TRABAJO fase 1- ESCRIBRO EN BD CON ATRIBUTO DE PARCIAL
			opcurso = this.cursoFeignClient.obtenerCursoAlumno(idalumno);//este método, es el cliente HTTP realmente
			//HARÍA MI TRABAJO fase 2- ESCRIBRO EN BD CON ATRIBUTO DE COMPLETO 
		}catch (Exception e) {
			// TODO: handle exception
			//DESHACER MI TRABAJO fase 1- ELIMINANDO EL REGISTRO DE PARCIAL
			log.error("ERROR EN LA COM FEING ", e);
			throw e;
			
		}
			
			
		return opcurso;
	}
}
