package edu.indra.alumnos.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import edu.indra.alumnos.model.FraseChuckNorris;
import edu.indra.comun.entity.Alumno;
import edu.indra.comun.entity.Curso;

public interface AlumnoService {
	
	public Iterable<Alumno> findAll ();
	
	public Optional<Alumno> findById (Long id);
	
	public Alumno save (Alumno alumno);
	
	public void deleteById (Long id);
	
	public Alumno update (Alumno alumno, Long id);
	
    public Optional<FraseChuckNorris> obtenerFraseAleaotoriaChuckNorris ();
    
    public Iterable<Alumno> findByEdadBetween (int edad_min, int edad_max);
    
    public Iterable<Alumno> findByNombreContaining(String patron);
    
    public Iterable<Alumno> buscarAlumnosPorNombreOApellidoJPQL (String patron);
    
    public Iterable<Alumno> buscarAlumnosPorNombreOApellidoNativa (String patron);
    
    public Iterable<Alumno> procedimientoAlumnosRegistradosHoy();
    
    public Map<String, Number> procedimientoEstadisticosEdad ();
    
    public Iterable<Alumno> procedimientoAlumnosNombreComo (String patron);
    
    public Page<Alumno> findAll (Pageable pageable);
    
    public Iterable<Alumno> findByEdadBetween (int edad_min, int edad_max, Pageable pageable);
    
    public Page<Alumno> listarAlumnosPaginadosOrdenados (Pageable pageable);
    
    public Optional<Curso> obtenerCursoAlumnoViaFeing(Long idalumno);

}
