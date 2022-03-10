package com.grupo9.vecinal.Servicios;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.grupo9.vecinal.Entidades.Foto;
import com.grupo9.vecinal.Repositorios.FotoRepositorio;

@Service
public class FotoServicio {

	@Autowired
	private FotoRepositorio fotoRepo;

	@Transactional
	public Foto guardar(MultipartFile archivo) throws Exception {

		if (archivo != null && !archivo.isEmpty()) {

			Foto foto = new Foto();
			foto.setMime(archivo.getContentType());
			foto.setNombre(archivo.getName());
			foto.setContenido(archivo.getBytes());

			return fotoRepo.save(foto);

		} else {
			return null;
		}

	}

	@Transactional
	public Foto actualizar(Integer idFoto, MultipartFile archivo) throws Exception {

		if (archivo != null && !archivo.isEmpty()) {

			Foto foto = new Foto();

			if (idFoto != null) {
				Optional<Foto> respuesta = fotoRepo.findById(idFoto);
				if (respuesta.isPresent()) {
					foto = respuesta.get();
				}
			}

			foto.setMime(archivo.getContentType());
			foto.setNombre(archivo.getName());
			foto.setContenido(archivo.getBytes());

			return fotoRepo.save(foto);

		} else {
			return null;
		}

	}
	
	@Transactional
	public void eliminar(Integer id){
		
		Foto foto = fotoRepo.findById(id).get();
		fotoRepo.delete(foto);

	}

}
