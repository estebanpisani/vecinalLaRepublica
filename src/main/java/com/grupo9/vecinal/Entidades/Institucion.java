package com.grupo9.vecinal.Entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
//@Table(name = "actividades")        //Verificar si corresponde (tipo dato telefono)
public class Institucion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idInstitucion;
	private String nombre;
	private String descripcion;
	private String direccion;
	private Long telefono;        //Verificar si corresponde Integer
	private Boolean alta;
	
	
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
		return "Institucion [idInstitucion=" + idInstitucion + ", nombre=" + nombre + ", descripcion=" + descripcion
				+ ", direccion=" + direccion + ", telefono=" + telefono + ", alta=" + alta + "]";
	}
	

}
