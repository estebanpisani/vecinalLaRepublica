package com.grupo9.vecinal.Servicios;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.grupo9.vecinal.Entidades.Novedad;
import com.grupo9.vecinal.Repositorios.NovedadRepositorio;

@Service
public class NovedadServicio {

	@Autowired
	NovedadRepositorio novedadRepo;

	@Autowired
	FotoServicio fotoServ;

	@Autowired
	UsuarioServicio usuraioServ;

	@Autowired
	private MailServicio enviarMails;

	@Transactional
	public void crearNovedad(MultipartFile foto, String titulo, String descripcion, Boolean destacado) {

		try {
			validarDatosNovedad(titulo, descripcion);
			if (destacado == null) {
				destacado = false;
			}
			Novedad novedad = new Novedad();
			novedad.setDescripcion(descripcion);
			novedad.setTitulo(titulo);
			novedad.setAlta(true);
			novedad.setFecha(LocalDateTime.now().minusHours(3));
			novedad.setDestacado(destacado);
			novedad.setFoto(fotoServ.guardar(foto));
			novedadRepo.save(novedad);
			if (novedad.getDestacado()) {
				enviarMails.sendEmailMasivos(titulo, descripcion);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Transactional
	public void modificarNovedad(MultipartFile foto, String titulo, String descripcion, Boolean destacado, Integer id)
			throws Exception {

		try {
			validarDatosNovedad(titulo, descripcion);
			Optional<Novedad> respuesta = novedadRepo.findById(id);
			if (respuesta.isPresent()) {
				Novedad novedad = respuesta.get();
				novedad.setDescripcion(descripcion);
				novedad.setTitulo(titulo);
				novedad.setFecha(LocalDateTime.now().minusHours(3));
				novedad.setDestacado(destacado);
				if (!foto.isEmpty()) {
					if (novedad.getFoto() != null) {
						novedad.setFoto(fotoServ.actualizar(novedad.getFoto().getId(), foto));
					} else {
						novedad.setFoto(fotoServ.guardar(foto));
					}
				}
				novedadRepo.save(novedad);

				if (novedad.getDestacado()) {
					enviarMails.sendEmailMasivos(titulo, descripcion);
				}
			} else {
				throw new Exception("La noticia no fue modificada");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void bajaNovedad(Integer id) throws Exception {

		try {
			Optional<Novedad> respuesta = novedadRepo.findById(id);
			if (respuesta.isPresent()) {
				Novedad novedad = respuesta.get();
				novedad.setAlta(false);

				novedadRepo.save(novedad);
			} else {
				throw new Exception("Error al dar de baja a la noticia");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Transactional(readOnly = true)
	public List<Novedad> mostrarTodasNovedades() throws Exception {
		List<Novedad> novedades = novedadRepo.findAll();
		if (novedades.isEmpty()) {
			throw new Exception("No hay noticias para mostrar");
		} else {
			return novedades;
		}
	}

	@Transactional(readOnly = true)
	public List<Novedad> mostrarAltaNovedades() throws Exception {
		List<Novedad> novedades = novedadRepo.novedadesAlta();
		if (novedades.isEmpty()) {
			throw new Exception("No hay noticias activas");
		} else {
			return novedades;
		}
	}

	@Transactional(readOnly = true)
	public List<Novedad> mostrarBajaNovedades() throws Exception {
		List<Novedad> novedades = novedadRepo.novedadesBaja();
		if (novedades.isEmpty()) {
			throw new Exception("No hay noticias dadas de baja");
		} else {
			return novedades;
		}
	}

	@Transactional(readOnly = true)
	public Novedad mostrarNovedad(Integer id) throws Exception {
		Novedad novedad = novedadRepo.findById(id).get();
		if (novedad == null) {
			throw new Exception("No se encontro la noticia solicitada");
		} else {
			return novedad;
		}
	}

	@Transactional(readOnly = true)
	public List<Novedad> mostrarNovedadesPorFechaActual(LocalDateTime fecha) throws Exception {
		List<Novedad> novedades = novedadRepo.novedadesPorFechaNueva(fecha);
		if (novedades.isEmpty()) {
			throw new Exception("No hay noticias con esa fecha");
		} else {
			return novedades;
		}
	}

	@Transactional(readOnly = true)
	public List<Novedad> mostrarNovedadesPorFechaVieja(LocalDateTime fecha) throws Exception {
		List<Novedad> novedades = novedadRepo.novedadesPorFechaVieja(fecha);
		if (novedades.isEmpty()) {
			throw new Exception("No hay noticias con esa fecha");
		} else {
			return novedades;
		}
	}

	@Transactional(readOnly = true)
	public List<Novedad> mostrarNovedadesPorTitulo(String titulo) throws Exception {
		List<Novedad> novedades = novedadRepo.novedadesPorTitulo("%" + titulo + "%");
		if (novedades.isEmpty()) {
			throw new Exception("No hay noticias con ese título");
		} else {
			return novedades;
		}
	}

	@Transactional(readOnly = true)
	public List<Novedad> mostrarNovedadesPorDescripcion(String descripcion) throws Exception {
		List<Novedad> novedades = novedadRepo.novedadesPorDescripcion("%" + descripcion + "%");
		if (novedades.isEmpty()) {
			throw new Exception("No hay noticias dadas de baja");
		} else {
			return novedades;
		}
	}

	@Transactional(readOnly = true)
	public List<Novedad> mostrarNovedadesDestacadas() throws Exception {
		List<Novedad> novedades = novedadRepo.novedadesPorDestacado();
		if (novedades.isEmpty()) {
			throw new Exception("No hay noticias destacadas");
		} else {
			return novedades;
		}
	}
	
	@Transactional(readOnly = true)
	public List<Novedad> mostrarNovedadesNoDestacadas() throws Exception {
		List<Novedad> novedades = novedadRepo.novedadesNoDestacadas();
		if (novedades.isEmpty()) {
			throw new Exception("No hay noticias para mostrar");
		} else {
			return novedades;
		}
	}

	public void validarDatosNovedad(String titulo, String descripcion) throws Exception {

		if (titulo == null || titulo.isEmpty())

		{
			throw new Exception("El campo no puede estar vacio.");

		}
		if (descripcion == null || descripcion.isEmpty())

		{
			throw new Exception("El campo no puede estar vacio.");

		}
	}

}
