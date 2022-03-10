package com.grupo9.vecinal.Entidades;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "novedades")
public class Novedad {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer novedadId;
	private String titulo;
	private String descripcion;
	private Boolean destacado;
	private Boolean alta;
	private LocalDateTime fecha;
	@OneToOne
	private Foto foto;

	public Foto getFoto() {
		return foto;
	}

	public void setFoto(Foto foto) {
		this.foto = foto;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public Novedad() {
	}

	

	public Integer getNovedadId() {
		return novedadId;
	}

	public void setNovedadId(Integer novedadId) {
		this.novedadId = novedadId;
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
		return "Novedad [actividadId=" + novedadId + ", titulo=" + titulo + ", descripcion=" + descripcion
				+ ", destacado=" + destacado + ", alta=" + alta + ", fecha=" + fecha + "]";
	}

}
