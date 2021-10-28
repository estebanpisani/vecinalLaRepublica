package com.grupo9.vecinal.Entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "actividades")
public class Actividad {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idActividades;
}
