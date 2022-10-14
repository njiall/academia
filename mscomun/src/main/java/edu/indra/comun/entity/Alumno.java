package edu.indra.comun.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.PrePersist;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

//import org.springframework.data.annotation.CreatedBy;
//import org.springframework.data.annotation.CreatedDate;

@NamedStoredProcedureQueries(
		{
			@NamedStoredProcedureQuery(name = "Alumno.alumnosRegistradosHoy", procedureName =  "obtenerAlumnosRegistradosHoy", resultClasses = edu.indra.comun.entity.Alumno.class),
			@NamedStoredProcedureQuery(name = "Alumno.alumnosEdadMediaMinMax", procedureName = "calcular_max_min_media_edad",
			parameters = { // Por un fallo en Hibernate, hay que indicar a los parámetros de salida como de ENTRADA_SALIDA
					@StoredProcedureParameter(mode = ParameterMode.INOUT , name="edadmax", type = Integer.class),
					@StoredProcedureParameter(mode = ParameterMode.INOUT , name="edadmin", type = Integer.class),
					@StoredProcedureParameter(mode = ParameterMode.INOUT , name="edadmedia", type = Float.class)
			}),
			@NamedStoredProcedureQuery(name = "Alumno.alumnosNombreComo", procedureName = "obtenerAlumnosConNombreComo", resultClasses = edu.indra.comun.entity.Alumno.class,
			parameters = {
				@StoredProcedureParameter(mode =  ParameterMode.IN, name="patron", type = String.class)
			})
		})
@Entity
@Table(name = "alumnos")
public class Alumno {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //auto Inc en MYSQL
	private Long id;//PK
	
	@Size(min = 3, max = 15)//en caso de usar mensaje propio message = "NOMBRE INCORRECTO" tiene prioridad sobre el lenguaje de petición
	private String nombre;
	
	@NotEmpty//no sea null y además, que tenga longuitud >= 1
	private String apellido;
	
	@Email
	private String email;
	
	@Min(0)
	@Max(130)
	private int edad;
	
	
	//@CreatedDate//alternativa a generar la fecha automáticamente
	@Column(name = "creado_en")
	private Date creadoEn;
	
	//@CreatedBy//anotación para incluir automáticamente el usuario de app que crea el registro - DEPEDENDE DE SPRING SECURITY
	//private String usuario;
	
	@Lob
	@JsonIgnore //evito que se serialice este atributo - que no vaya en la respuesta JSON cuando se envie un alumno
	private byte[] foto;
	
	public Integer getFotoHashCode ()
	{
		Integer idev = null;
		
			if (this.foto!=null)
			{
				idev = this.foto.hashCode();
			}
		
		return idev;
	}
	
	@PrePersist
	private void generarFechaCreacion ()
	{
		this.creadoEn = new Date();//coger la fecha actual
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public Date getCreadoEn() {
		return creadoEn;
	}

	public void setCreadoEn(Date creadoEn) {
		this.creadoEn = creadoEn;
	}
	
	public Alumno() {
		// TODO Auto-generated constructor stub
	}

	public Alumno(Long id, String nombre, String apellido, String email, int edad, Date creadoEn) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.edad = edad;
		this.creadoEn = creadoEn;
	}

	@Override
	public String toString() {
		return "Alumno [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", email=" + email + ", edad="
				+ edad + ", creadoEn=" + creadoEn + "]";
	}
	
	
	@Override
	public boolean equals(Object obj) {
		boolean iguales = false;
			
			if (this==obj)
			{
				iguales = true;
			} else if (obj instanceof Alumno)//(obj instanceof Alumno a) pattern Matching java14 https://docs.oracle.com/en/java/javase/14/language/pattern-matching-instanceof-operator.html 
			{
				Alumno a = (Alumno) obj;
				iguales = ((this.id!=null)&&(this.id.equals(a.id)));
			} 
			//else iguales = false
			 
		
		return iguales;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}
	
	

}
