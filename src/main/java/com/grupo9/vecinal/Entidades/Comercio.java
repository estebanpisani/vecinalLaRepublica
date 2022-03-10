package com.grupo9.vecinal.Entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Comercio {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idComercio;
	private String nombre;
	private String descripcion;
	private String direccion;
	private Long telefono;
	private Boolean alta;
	@OneToOne
	private Foto foto;
	
	public Integer getIdComercio() {
		return idComercio;
	}
	public void setIdComercio(Integer idComercio) {
		this.idComercio = idComercio;
	}
	public Foto getFoto() {
		return foto;
	}
	public void setFoto(Foto foto) {
		this.foto = foto;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public Long getTelefono() {
		return telefono;
	}
	public void setTelefono(Long telefono) {
		this.telefono = telefono;
	}
	public Boolean getAlta() {
		return alta;
	}
	public void setAlta(Boolean alta) {
		this.alta = alta;
	}
	@Override
	public String toString() {
		return "Comercio [idComercio=" + idComercio + ", nombre=" + nombre + ", descripcion=" + descripcion
				+ ", direccion=" + direccion + ", telefono=" + telefono + ", alta=" + alta + "]";
	}
	

}
