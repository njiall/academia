package edu.indra.alumnos.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
//import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import edu.indra.alumnos.model.FraseChuckNorris;
import edu.indra.alumnos.service.AlumnoService;
import edu.indra.comun.entity.Alumno;
import edu.indra.comun.entity.Curso;
import net.bytebuddy.asm.Advice.This;

//@CrossOrigin(originPatterns = {"*"}, methods = {RequestMethod.GET})
@RestController
@RequestMapping("/alumno")
public class AlumnoController {

	@Autowired
	AlumnoService alumnoService;
	
	Logger log = LoggerFactory.getLogger(AlumnoController.class);

	@Autowired
	Environment environment;
	
	@Value("${instancia}")//este atributo toma el valor de la propiedad instancia del properties
	String nombre_instancia;
	
	/**
	 * consulta 1todos -GET 2uno -GET
	 * 
	 * 3alta - POST 4baja - DELETE 5modificación - PUT
	 */

	@GetMapping("/obtener-alumno-test") // GET http://localhost:8081/alumno/obtener-alumno-test
	public Alumno obtenerAlumnoTest() {
		Alumno a = null;

		a = new Alumno(1l, "Juan", "Pedro", "juanpe@correo.es", 30, new Date());

		return a;
	}

	@GetMapping // GET http://localhost:8081/alumno
	public ResponseEntity<?> obtenerListaAlumnos() {
		ResponseEntity<?> responseEntity = null;
		Iterable<Alumno> listado_alumnos = null;

		log.debug("ATENDIDO POR LA INSTANCIA " + nombre_instancia);
		log.debug("ATENDIDO EN EL PUERTO " + this.environment.getProperty("local.server.port"));
		
		listado_alumnos = this.alumnoService.findAll();
		responseEntity = ResponseEntity.ok(listado_alumnos);

		return responseEntity;
	}

	@GetMapping("/{id}") // GET http://localhost:8081/alumno/2
	public ResponseEntity<?> obtenerAlumnoPorId(@PathVariable Long id) {
		ResponseEntity<?> responseEntity = null;
		Optional<Alumno> optional = null;

		optional = this.alumnoService.findById(id);
		if (optional.isPresent()) {
			Alumno alumno_leido = optional.get();
			responseEntity = ResponseEntity.ok(alumno_leido);
		} else {
			responseEntity = ResponseEntity.noContent().build();// 204
		}

		return responseEntity;
	}

	private ResponseEntity<?> obtenerRespuestaErrores(BindingResult bindingResult) {
		ResponseEntity<?> responseEntity = null;
		List<ObjectError> lista_errores = null;
		
			lista_errores = bindingResult.getAllErrors();//obtengo errores
			//lista_errores.get(0).getDefaultMessage()
			lista_errores.forEach(o_error -> {log.error(o_error.toString());});
			
			responseEntity = ResponseEntity.badRequest().body(lista_errores);//los escribo en la respuesta 
			
		return responseEntity;
	}

	@PostMapping // POST http://localhost:8081/alumno
	public ResponseEntity<?> insertarAlumno(@Valid @RequestBody Alumno alumno, BindingResult bindingResult) {
		ResponseEntity<?> responseEntity = null;
		Alumno alumno_insertado = null;

		log.debug("en insertarAlumno()");
		if (bindingResult.hasErrors()) {
			// el alumno trae errores
			log.debug("alumno con errores " + alumno);
			responseEntity = obtenerRespuestaErrores(bindingResult);
		} else {
			// el alumno NO trae errores
			log.debug("alumno sin errores " + alumno);
			alumno_insertado = this.alumnoService.save(alumno);
			responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(alumno_insertado);// 201
			log.debug("alumno insertado");
		}

		return responseEntity;
	}
	
	
	@PostMapping("/crear-con-foto") // POST http://localhost:8081/alumno/insertarAlumnoConFoto
	public ResponseEntity<?> insertarAlumnoConFoto(@Valid Alumno alumno, BindingResult bindingResult, MultipartFile archivo) throws IOException {
		ResponseEntity<?> responseEntity = null;
		Alumno alumno_insertado = null;

		log.debug("en insertarAlumno()");
		if (bindingResult.hasErrors()) {
			// el alumno trae errores
			log.debug("alumno con errores " + alumno);
			responseEntity = obtenerRespuestaErrores(bindingResult);
		} else {
			// el alumno NO trae errores
			log.debug("alumno sin errores " + alumno);
			
			if (!archivo.isEmpty())
	 		{
	 			try {
					alumno.setFoto(archivo.getBytes());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					log.error("Error al crear el alumno con foto", e);
					throw e;
				}
	 		}
			
			
			alumno_insertado = this.alumnoService.save(alumno);
			responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(alumno_insertado);// 201
			log.debug("alumno insertado");
		}

		return responseEntity;
	}
	
	@GetMapping("/obtenerFoto/{id}") //GET localhost:8081/alumno/obtenerFoto/id
	public ResponseEntity<?> obtenerFortoAlumnoPorId(@PathVariable Long id)
	{
		 ResponseEntity<?> responseEntity = null;
		 Optional<Alumno> op_alumno = null;
		 Resource imagen = null;
		 
		 	this.log.debug("obtenerFortoAlumnoPorId ()");
		 	op_alumno = this.alumnoService.findById(id);
		 	if (op_alumno.isPresent() && op_alumno.get().getFoto() !=null )
		 	{
		 		//devolver un 200
		 		Alumno alumno_aux = op_alumno.get();
		 		imagen = new ByteArrayResource (alumno_aux.getFoto());
		 		responseEntity = ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imagen);
		 		
		 	} else {
		 		responseEntity = ResponseEntity.noContent().build();
		 	}
		 	
		 
		 return responseEntity; //este será el HTTP de vuelta
	}

	@DeleteMapping("/{id}") // DELETE http://localhost:8081/alumno/id
	public ResponseEntity<?> eliminarAlumno(@PathVariable Long id) {
		ResponseEntity<?> responseEntity = null;

		// try {
		// var cade = "HOLA"; provocamos un fallo para forzar otro tipo de excepción
		// cade.charAt(20); y que el flujo derive en GestionExcepciones.errorGeneral
		this.alumnoService.deleteById(id);
		responseEntity = ResponseEntity.ok().build();

		/*
		 * } catch (Exception e) { // TODO: handle exception
		 * System.err.println("Error al borrar el registro id " + id ); responseEntity =
		 * ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
		 * body("LO SIENTO HA HABIDO UN ERROR"); }
		 */

		return responseEntity;
	}

	@PutMapping("/{id}") // PUT http://localhost:8081/alumno/id
	public ResponseEntity<?> modificarAlumno(@Valid @RequestBody Alumno alumno, BindingResult bindingResult,
			@PathVariable Long id) {
		ResponseEntity<?> responseEntity = null;
		Alumno alumno_mod = null;

		if (bindingResult.hasErrors()) {
			// trae errores
			responseEntity = obtenerRespuestaErrores(bindingResult);
		} else {
			// alumno válido
			alumno_mod = this.alumnoService.update(alumno, id);
			if (alumno_mod != null) {
				responseEntity = ResponseEntity.ok(alumno_mod);
			} else {
				responseEntity = ResponseEntity.notFound().build();// 404/
			}

		}

		return responseEntity;
	}
	
	@PutMapping("/editar-con-foto/{id}") //PUT localhost:8081/alumno/editar-con-foto
	public ResponseEntity<?> modificarAlumnoConFoto (@Valid Alumno alumno, BindingResult bindingResult, MultipartFile archivo, @PathVariable Long id) throws IOException
	{
		 ResponseEntity<?> responseEntity = null;
		 Alumno alumno_modificado = null;
		 
		 	this.log.debug("modificarAlumnoConFoto()");
		 	if (bindingResult.hasErrors())
		 	{
		 		//viene con errores
		 		//devolver un mensaje de error //BAD REQUEST -le enviamos los fallos
		 		this.log.debug("modificarAlumnoConFoto() - TRAE ERRORES");
		 		responseEntity = obtenerRespuestaErrores(bindingResult);
		 	} else {
		 		//sin fallos- seguimos con el update
		 		this.log.debug("modificarAlumnoConFoto() - TRAE ERRORES");
		 		
		 		
		 		if (!archivo.isEmpty())
		 		{
		 			try {
						alumno.setFoto(archivo.getBytes());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						log.error("Error al crear el alumno con foto", e);
						throw e;
					}
		 		}
		 		
		 		alumno_modificado = this.alumnoService.update(alumno, id);
			 	if (alumno_modificado!=null)
			 	{
			 		responseEntity = ResponseEntity.ok(alumno_modificado);
			 	} else 
			 	{
			 		//no existía
			 		responseEntity = ResponseEntity.notFound().build();
			 	}
		 	}
		 
		 
		 return responseEntity; //este será el HTTP de vuelta
	}
	
	
	@GetMapping("/obtenerFraseChuck") // GET http://localhost:8081/alumno/obtenerFraseChuck
	public ResponseEntity<?> obtenerFraseChuck() {
		ResponseEntity<?> responseEntity = null;
		Optional<FraseChuckNorris> optional = null;

		optional = this.alumnoService.obtenerFraseAleaotoriaChuckNorris();
		if (optional.isPresent()) {
			FraseChuckNorris fraseChuckNorris = optional.get();
			responseEntity = ResponseEntity.ok(fraseChuckNorris);
		} else {
			responseEntity = ResponseEntity.noContent().build();// 204
		}

		return responseEntity;
	}
	
	@GetMapping("/obtenerAlumnosRangoEdad") // GET http://localhost:8081/alumno/obtenerAlumnosRangoEdad?edadmin=5&edadmax=10
	public ResponseEntity<?> obtenerAlumnosRangoEdad(@RequestParam(name = "edadmin", required = true) int edadmin, @RequestParam(name = "edadmax", required = true) int edadmax) {
		ResponseEntity<?> responseEntity = null;
		Iterable<Alumno> listado_alumnos = null;
		
			log.debug("en obtenerAlumnosRangoEdad() edad min = " + edadmin + " edadmax = " + edadmax);

			listado_alumnos = this.alumnoService.findByEdadBetween(edadmin, edadmax);
			responseEntity = ResponseEntity.ok(listado_alumnos);
			
			log.debug("salida obtenerAlumnosRangoEdad() "+listado_alumnos);

		return responseEntity;
	}
	
	@GetMapping("/obtenerAlumnosRangoEdadPaginado") // GET http://localhost:8081/alumno/obtenerAlumnosRangoEdadPaginado?edadmin=5&edadmax=10&page=0&size=2
	public ResponseEntity<?> obtenerAlumnosRangoEdadPaginado(@RequestParam(name = "edadmin", required = true) int edadmin, @RequestParam(name = "edadmax", required = true) int edadmax, Pageable pageable) {
		ResponseEntity<?> responseEntity = null;
		Iterable<Alumno> listado_alumnos = null;
		
			log.debug("en obtenerAlumnosRangoEdad() edad min = " + edadmin + " edadmax = " + edadmax);

			listado_alumnos = this.alumnoService.findByEdadBetween(edadmin, edadmax, pageable);
			responseEntity = ResponseEntity.ok(listado_alumnos);
			
			log.debug("salida obtenerAlumnosRangoEdad() "+listado_alumnos);

		return responseEntity;
	}
	
	@GetMapping("/obtenerAlumnosNombreComo/{nombre}") //GET http://localhost:8081/alumno/obtenerAlumnosNombreComo/Pepe
	public ResponseEntity<?> obtenerAlumnosNombre (@PathVariable String nombre) {
		ResponseEntity <?> respuesta = null;
		Iterable<Alumno> listado_alumnos = null;
			
			log.debug("obtenerAlumnosNombre() nombre = " + nombre);
			
			listado_alumnos = this.alumnoService.findByNombreContaining(nombre);
			respuesta = ResponseEntity.ok(listado_alumnos);
			
			log.debug("salida obtenerAlumnosNombre() "+listado_alumnos);
	
		return respuesta;
	}
	
	
	@GetMapping("/obtenerAlumnosPorNombreOApeJQPL/{nombre}") //GET http://localhost:8081/alumno/obtenerAlumnosPorNombreOApeJQPL/Pepe
	public ResponseEntity<?> obtenerAlumnosPorNombreOApeJQPL (@PathVariable String nombre) {
		ResponseEntity <?> respuesta = null;
		Iterable<Alumno> listado_alumnos = null;
			
			log.debug("obtenerAlumnosPorNombreOApeJQPL() patron = " + nombre);
			
			listado_alumnos = this.alumnoService.buscarAlumnosPorNombreOApellidoJPQL(nombre);
			respuesta = ResponseEntity.ok(listado_alumnos);
			
			log.debug("salida obtenerAlumnosPorNombreOApeJQPL() "+listado_alumnos);
	
		return respuesta;
	}
	
	@GetMapping("/obtenerAlumnosPorNombreOApeNativa/{nombre}") //GET http://localhost:8081/alumno/obtenerAlumnosPorNombreOApeNativa/Pepe
	public ResponseEntity<?> obtenerAlumnosPorNombreOApeNativa (@PathVariable String nombre) {
		ResponseEntity <?> respuesta = null;
		Iterable<Alumno> listado_alumnos = null;
			
			log.debug("obtenerAlumnosPorNombreOApeNativa() patron = " + nombre);
			
			listado_alumnos = this.alumnoService.buscarAlumnosPorNombreOApellidoNativa(nombre);
			respuesta = ResponseEntity.ok(listado_alumnos);
			
			log.debug("salida obtenerAlumnosPorNombreOApeNativa() "+listado_alumnos);
	
		return respuesta;
	}
	
	@GetMapping("/procedimientoNombreComo/{nombre}") //GET http://localhost:8081/alumno/procedimientoNombreComo/Pepe
	public ResponseEntity<?> procedimientoNombreComo (@PathVariable String nombre) {
		ResponseEntity <?> respuesta = null;
		Iterable<Alumno> listado_alumnos = null;
			
			log.debug("procedimientoNombreComo() patron = " + nombre);
			
			listado_alumnos = this.alumnoService.procedimientoAlumnosNombreComo(nombre);
			respuesta = ResponseEntity.ok(listado_alumnos);
			
			log.debug("salida procedimientoNombreComo() "+listado_alumnos);
	
		return respuesta;
	}
	
	
	@GetMapping("/procedimientoAlumnosAltaHoy") //GET http://localhost:8081/alumno/procedimientoAlumnosAltaHoy
	public ResponseEntity<?> procedimientoAlumnosAltaHoy () {
		ResponseEntity <?> respuesta = null;
		Iterable<Alumno> listado_alumnos = null;
			
			log.debug("procedimientoAlumnosAltaHoy() ");
			
			listado_alumnos = this.alumnoService.procedimientoAlumnosRegistradosHoy();
			respuesta = ResponseEntity.ok(listado_alumnos);
			
			log.debug("salida procedimientoAlumnosAltaHoy() "+listado_alumnos);
	
		return respuesta;
	}
	
	
	@GetMapping("/procedimientoEstadisticosEdad") //GET http://localhost:8081/alumno/procedimientoEstadisticosEdad
	public ResponseEntity<?> procedimientoEstadisticosEdad () {
		ResponseEntity <?> respuesta = null;
		Map<String, Number> mapa_edades = null;
			
			log.debug("procedimientoEstadisticosEdad() ");
			
			mapa_edades = this.alumnoService.procedimientoEstadisticosEdad();
			respuesta = ResponseEntity.ok(mapa_edades);
			
			log.debug("salida procedimientoEstadisticosEdad() "+mapa_edades);
	
		return respuesta;
	}
	
	@GetMapping("/pagina") //GET http://localhost:8081/alumno/obtenerPaginaAlumno?page=0&size=3
	public ResponseEntity<?> obtenerPaginaAlumno (Pageable pageable) {
		ResponseEntity <?> respuesta = null;
		Iterable<Alumno> listado_alumnos = null;
			
			log.debug("obtenerPaginaAlumno");
			
			listado_alumnos = this.alumnoService.findAll(pageable);
			respuesta = ResponseEntity.ok(listado_alumnos);
			
			log.debug("salida obtenerPaginaAlumno "+listado_alumnos);
	
		return respuesta;
	}
	
	
	@GetMapping("/obtenerPaginaAlumnoPorEdad") //GET http://localhost:8081/alumno/obtenerPaginaAlumnoPorEdad?page=0&size=3&sort=edad
	public ResponseEntity<?> obtenerPaginaAlumnoPorEdad ( 
			@RequestParam(defaultValue = "0", name = "page") Integer page, 
            @RequestParam(defaultValue = "10", name = "size") Integer size,
            @RequestParam(defaultValue = "edad", name = "sort") String sort) {
		ResponseEntity <?> respuesta = null;
		Iterable<Alumno> listado_alumnos = null;
			
			log.debug("obtenerPaginaAlumnoPorEdad");
			
			Pageable pageable = PageRequest.of(page, size, Sort.by(sort).descending());
			 
			listado_alumnos = this.alumnoService.listarAlumnosPaginadosOrdenados(pageable);
			respuesta = ResponseEntity.ok(listado_alumnos);
			
			log.debug("salida obtenerPaginaAlumnoPorEdad "+listado_alumnos);
	
		return respuesta;
	}
	
	@GetMapping("/obtenerPaginaAlumnoPorEdad2") //GET http://localhost:8081/alumno/obtenerPaginaAlumnoPorEdad2?page=0&size=3&sort=edad,DESC
	public ResponseEntity<?> obtenerPaginaAlumnoPorEdad2 (Pageable pageable) {
		ResponseEntity <?> respuesta = null;
		Iterable<Alumno> listado_alumnos = null;
			
			log.debug("obtenerPaginaAlumnoPorEdad2");
			
			//Pageable pageable = PageRequest.of(page, size, Sort.by(sort).descending());
			 
			listado_alumnos = this.alumnoService.listarAlumnosPaginadosOrdenados(pageable);
			respuesta = ResponseEntity.ok(listado_alumnos);
			
			log.debug("salida obtenerPaginaAlumnoPorEdad2 "+listado_alumnos);
	
		return respuesta;
	}
	
	@GetMapping("/obtenerCursoAlumnoViaFeign/{idalumno}") // GET http://localhost:8081/alumno/obtenerCursoAlumnoViaFeign/2
	public ResponseEntity<?> obtenerCursoAlumnoViaFeign(@PathVariable Long idalumno) {
		ResponseEntity<?> responseEntity = null;
		Optional<Curso> optional = null;

		optional = this.alumnoService.obtenerCursoAlumnoViaFeing(idalumno);
		if (optional.isPresent()) {
			Curso curso_leido = optional.get();
			responseEntity = ResponseEntity.ok(curso_leido);
		} else {
			responseEntity = ResponseEntity.noContent().build();// 204
		}

		return responseEntity;
	}


}
