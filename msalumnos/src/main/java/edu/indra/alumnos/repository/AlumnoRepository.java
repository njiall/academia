package edu.indra.alumnos.repository;

import java.util.Map;

import javax.persistence.NamedStoredProcedureQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
//import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import edu.indra.comun.entity.Alumno;

//@Repository
//public interface AlumnoRepository extends CrudRepository<Alumno, Long> {
public interface AlumnoRepository extends PagingAndSortingRepository<Alumno, Long> {
	
	//al heredar de esta interfaz, la operativa básica
	//de alta baja y modificación de ALumnos en BD ya está hecha
	
	
	//KEYWORD Queries - Palabras Clave
		
		//1 OBTENER UN LISTADO DE ALUMNOS EN UN RANGO DE EDAD
		public Iterable<Alumno> findByEdadBetween (int edad_min, int edad_max);
		
		//1.1 OBTENER UN LISTADO DE ALUMNOS EN UN RANGO DE EDAD PAGINADO
		public Page<Alumno> findByEdadBetween (int edad_min, int edad_max, Pageable pageable);
		
		//2 TODO OBTENER UN LISTADO DE ALUMNOS CUYO NOMBRE CUMPLA UN PATRÓN REPO, SERVICE Y CONTROLLER
		public Iterable<Alumno> findByNombreContaining(String patron);
		
	
	//JPQL -"SQL Agnostico" - refiero a entidades (clases de Java) y no a tablas
		
		//3 LISTAR ALUMNOS CUYO NOMBRE O APELLIDO COINCIDAN CON UN PATRÓN DADO
		@Query("SELECT a FROM Alumno a WHERE a.nombre LIKE %?1% or a.apellido LIKE %?1%")
		public Iterable<Alumno> buscarAlumnosPorNombreOApellidoJPQL (String patron);
		
		//TODO CREAR SERVICIO Y CONTROLLER PARA ESTE MÉTODO Y VER CÓMO FUNCIONA LA PAGINACIÓN CON CONSULTAS JPQL
		@Query("SELECT a FROM Alumno a WHERE a.nombre LIKE %?1% or a.apellido LIKE %?1%")
		public Page<Alumno> busquedaPorNombreOApellidoPaginado (String patron, Pageable pageable);
	
	//NATIVAS
		
		//4 LISTAR ALUMNOS CUYO NOMBRE O APELLIDO COINCIDAN CON UN PATRÓN DADO
		@Query(value = "SELECT * FROM alumnos a WHERE a.nombre LIKE %?1% or a.apellido LIKE %?1%", nativeQuery = true)
		public Iterable<Alumno> buscarAlumnosPorNombreOApellidoNativa (String patron);
	
	//PROCEDIMIENTOS ALMACENADOS
		
		//1 obtener los alumnos dados de alta hoy
		//2 obtener los estadísticos (min, max, media) de edad
		//3 obtener alumnos con nombre like
		
		//3 PASOS
			//a) Definir los procedimientos en base de datos
			//b) Definir la referencia en la Entidad relacionada a esos procedimientos con @NamedStoredProcedureQuery
		    //c) Definir en el Bean Respository un método por cada procedimiento asociado en el paso anterior con @Procedure
		
		@Procedure(name = "Alumno.alumnosRegistradosHoy")
		public Iterable<Alumno> procedimientoAlumnosRegistradosHoy();
		
		@Procedure(name = "Alumno.alumnosEdadMediaMinMax")
		public Map<String, Number> procedimientoEstadisticosEdad (int edadmax, int edadmin, float edadmedia);
		
		@Procedure(name = "Alumno.alumnosNombreComo")
		public Iterable<Alumno> procedimientoAlumnosNombreComo (@Param("patron") String patron);
	
	//CRITERIA API -- x - no
	
	//PAGINACIÓN - CONSULTAS
		
}
