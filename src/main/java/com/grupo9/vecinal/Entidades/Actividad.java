package com.grupo9.vecinal.Entidades;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "actividades")
public class Actividad {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idActividades; 
	private String nombreActividad; 
	private String descripcionActividad;
	@DateTimeFormat(style = "dd-mm-YYYY")
	private Date fecha; 
	private Boolean alta; 
	private Integer cupo; 
	private Integer inscriptos;
	
	@ManyToMany
	private List<Usuario> usuarios;

	public Actividad() {
	}

	public Integer getIdActividades() {
		return idActividades;
	}

	public void setIdActividades(Integer idActividades) {
		this.idActividades = idActividades;
	}

	public String getNombreActividad() {
		return nombreActividad;
	}

	public void setNombreActividad(String nombreActividad) {
		this.nombreActividad = nombreActividad;
	}

	public String getDescripcionActividad() {
		return descripcionActividad;
	}

	public void setDescripcionActividad(String descripcionActividad) {
		this.descripcionActividad = descripcionActividad;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Boolean getAlta() {
		return alta;
	}

	public void setAlta(Boolean alta) {
		this.alta = alta;
	}

	public Integer getCupo() {
		return cupo;
	}

	public void setCupo(Integer cupo) {
		this.cupo = cupo;
	}

	public Integer getInscriptos() {
		return inscriptos;
	}

	public void setInscriptos(Integer inscriptos) {
		this.inscriptos = inscriptos;
	}
	
	

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	@Override
	public String toString() {
		return "Actividad [idActividades=" + idActividades + ", nombreActividad=" + nombreActividad
				+ ", descripcionActividad=" + descripcionActividad + ", fecha=" + fecha + ", alta=" + alta + ", cupo="
				+ cupo + ", inscriptos=" + inscriptos + "]";
	}
	
	
	
	
}
