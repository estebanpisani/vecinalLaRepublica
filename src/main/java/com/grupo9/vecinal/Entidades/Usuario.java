package com.grupo9.vecinal.Entidades;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
//@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "emailUsuario", "nombreUsuario" }) })
@Table(name = "usuario")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idUsuario;
	private String nombreUsuario;
	private String contrasenia;
	private String emailUsuario;
	private Boolean admin;
	private String nombre;
	private String apellido;
	private Integer telefono;
	private Boolean alta;
	private Boolean cuotaAlDia;
	@ManyToMany	
	private Set<Actividad> actividades;

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public String getEmailUsuario() {
		return emailUsuario;
	}

	public void setEmailUsuario(String emailUsuario) {
		this.emailUsuario = emailUsuario;
	}

	public Boolean getAdmin() {
		return admin;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
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

	public Integer getTelefono() {
		return telefono;
	}

	public void setTelefono(Integer telefono) {
		this.telefono = telefono;
	}

	public Boolean getAlta() {
		return alta;
	}

	public void setAlta(Boolean alta) {
		this.alta = alta;
	}

	public Boolean getCuotaAlDia() {
		return cuotaAlDia;
	}

	public void setCuotaAlDia(Boolean cuotaAlDia) {
		this.cuotaAlDia = cuotaAlDia;
	}

	public Set<Actividad> getActividades() {
		return actividades;
	}

	public void setActividades(Set<Actividad> actividades) {
		this.actividades = actividades;
	}

	public Usuario() {

	}

	@Override
	public String toString() {
		return "Usuario [idUsuario=" + idUsuario + ", nombreUsuario=" + nombreUsuario + ", contrasenia=" + contrasenia
				+ ", emailUsuario=" + emailUsuario + ", admin=" + admin + ", nombre=" + nombre + ", apellido="
				+ apellido + ", telefono=" + telefono + ", alta=" + alta + ", cuotaAlDia=" + cuotaAlDia
				+ ", actividades=" + actividades + "]";
	}

}
