package edu.indra.comun.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Entity
@Table(name = "cursos")
//@EnableJpaAuditing
public class Curso {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //auto Inc en MYSQL
	private Long id;//PK
	
	private String nombre;
	
	@OneToMany(fetch = FetchType.LAZY)//1CURSO, TIENE N ALUMNOS
	private List<Alumno> alumnos;
	
	//@Temporal(TemporalType.TIMESTAMP) //por defecto usa el formato de fecha TIMESTAMP
	@CreatedDate//alternativa a generar la fecha automáticamente sin prepersist 
	@Column(name = "creado_en")
	private Date creadoEn;
	
	@PrePersist
	private void generarFechaCreacion ()
	{
		this.creadoEn = new Date();//coger la fecha actual
	}
	
	public int getNumParticipantes ()
	{
		return this.alumnos.size();
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
	
	public Curso() {
		// TODO Auto-generated constructor stub
		this.alumnos = new ArrayList<Alumno>();
	}
	
	

	public Curso(Long id, String nombre, List<Alumno> alumnos, Date creadoEn) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.creadoEn = creadoEn;
		this.alumnos = new ArrayList<Alumno>();
	}

	public Date getCreadoEn() {
		return creadoEn;
	}

	public void setCreadoEn(Date creadoEn) {
		this.creadoEn = creadoEn;
	}

	public List<Alumno> getAlumnos() {
		return alumnos;
	}

	public void setAlumnos(List<Alumno> alumnos) {
		this.alumnos = alumnos;
	}
	
	
	public void asginarAlumno (Alumno alumno)
	{
		this.alumnos.add(alumno);
	}
	
	public void borrarAlumno (Alumno alumno)
	{
		this.alumnos.remove(alumno);
	}
	
	

}
