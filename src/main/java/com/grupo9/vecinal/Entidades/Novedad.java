package com.grupo9.vecinal.Entidades;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Novedad {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer actividadId;
	private String titulo;
	private String descripcion;
	private Boolean destacado;
	private Boolean alta;
	@DateTimeFormat(style = "dd-mm-YYYY")
	private Date fecha;

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Novedad() {
	}

	public Integer getActividadId() {
		return actividadId;
	}

	public void setActividadId(Integer actividadId) {
		this.actividadId = actividadId;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Boolean getDestacado() {
		return destacado;
	}

	public void setDestacado(Boolean destacado) {
		this.destacado = destacado;
	}

	public Boolean getAlta() {
		return alta;
	}

	public void setAlta(Boolean alta) {
		this.alta = alta;
	}

	@Override
	public String toString() {
		return "Novedades [actividadId=" + actividadId + ", titulo=" + titulo + ", descripcion=" + descripcion
				+ ", destacado=" + destacado + ", alta=" + alta + "]";
	}

}
