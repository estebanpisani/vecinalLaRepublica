package com.grupo9.vecinal.Entidades;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
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
	private Long telefono;
	private Boolean alta;
	private Boolean cuotaAlDia;
	@ManyToMany	
	private Set<Actividad> actividades;
	@OneToOne
	private Foto foto;
	private LocalDate fechaDeBaja;
	private String codValidacion;

	
	public LocalDate getFechaDeBaja() {
		return fechaDeBaja;
	}

	public void setFechaDeBaja(LocalDate fechaDeBaja) {
		this.fechaDeBaja = fechaDeBaja;
	}

	public String getCodValidacion() {
		return codValidacion;
	}

	public void setCodValidacion(String codValidacion) {
		this.codValidacion = codValidacion;
	}

	public Foto getFoto() {
		return foto;
	}

	public void setFoto(Foto foto) {
		this.foto = foto;
	}

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
				+ ", actividades=" + actividades + ", foto=" + foto + ", fechaDeBaja=" + fechaDeBaja
				+ ", codValidacion=" + codValidacion + "]";
	}

	
	}


