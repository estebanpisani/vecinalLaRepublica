package com.grupo9.vecinal.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupo9.vecinal.Entidades.Foto;

@Repository
public interface FotoRepositorio extends JpaRepository<Foto, Integer> {

}
