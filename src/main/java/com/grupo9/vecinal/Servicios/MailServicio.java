package com.grupo9.vecinal.Servicios;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.grupo9.vecinal.Entidades.Usuario;

@Service
public class MailServicio {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private UsuarioServicio usuarioServ;

	public void sendRecuperarDatos(Usuario usuario) throws MessagingException, UnsupportedEncodingException {
		String toAddress = usuario.getEmailUsuario();
		String fromAddress = "larepublica.vecinal@gmail.com";
		String senderName = "Vecinal la república";
		String subject = "Recuperacion de datos de usuario";
		String content = "[[name]],<br>" + "Usuario: " + usuario.getNombreUsuario()
				+ "<br><h3><a href=\"[[URL]]\" target=\"_self\">Crear contraseña</a></h3>" + "Muchas gracias,<br>"
				+ "Vecinal la república.";

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom(fromAddress, senderName);
		helper.setTo(toAddress);
		helper.setSubject(subject);

		content = content.replace("[[name]]", usuario.getNombre() + " " + usuario.getApellido());
		String verifyURL = "http://localhost:8081/usuarios/recuperar/" + usuario.getCodValidacion();

		content = content.replace("[[URL]]", verifyURL);

		helper.setText(content, true);

		mailSender.send(message);

	}

	public void sendVerificacionEmail(Usuario usuario) throws MessagingException, UnsupportedEncodingException {
		String toAddress = usuario.getEmailUsuario();
		String fromAddress = "larepublica.vecinal@gmail.com";
		String senderName = "Vecinal la república";
		String subject = "Por favor verifique su registro";
		String content = "[[name]],<br>" + "Por favor acceda al siguiente link para dar de alta su usuario:<br>"
				+ "<h3><a href=\"[[URL]]\" target=\"_self\">Confirmar Registro</a></h3>" + "Muchas gracias,<br>"
				+ "Vecinal la república.";

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom(fromAddress, senderName);
		helper.setTo(toAddress);
		helper.setSubject(subject);

		content = content.replace("[[name]]", usuario.getNombre() + " " + usuario.getApellido());
		String verifyURL = "http://localhost:8081/usuarios/verificacion/" + usuario.getCodValidacion();

		content = content.replace("[[URL]]", verifyURL);

		helper.setText(content, true);

		mailSender.send(message);

	}

	public void sendEmailMasivos(String titulo, String descripcion)
			throws MessagingException, UnsupportedEncodingException, Exception {
		List<Usuario> usuarios = usuarioServ.mostrarUsuariosAlta();
		if (usuarios.isEmpty()) {
			throw new Exception("No hay usuarios registrados para mandar correos");
		} else {
			for (Usuario usuario : usuarios) {
				String toAddress = usuario.getEmailUsuario();
				String fromAddress = "larepublica.vecinal@gmail.com";
				String senderName = "Vecinal La República";
				String subject = titulo;
				String content = "[[name]],<br>" + descripcion + "<br><br>" + "Vecinal La República.";

				MimeMessage message = mailSender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(message);

				helper.setFrom(fromAddress, senderName);
				helper.setTo(toAddress);
				helper.setSubject(subject);

				content = content.replace("[[name]]", usuario.getNombre() + " " + usuario.getApellido());
				String verifyURL = "http://localhost:8081/usuarios/verificacion/" + usuario.getCodValidacion();

				content = content.replace("[[URL]]", verifyURL);

				helper.setText(content, true);

				mailSender.send(message);
			}

		}

	}
	
	/*public void sendEmailActividadInscriptos(Integer idActividad)
			throws MessagingException, UnsupportedEncodingException, Exception {
		List<Usuario> usuarios = usuarioServ.mostrarUsuariosAlta();
		if (usuarios.isEmpty()) {
			throw new Exception("No hay usuarios registrados para mandar correos");
		} else {
			for (Usuario usuario : usuarios) {
				String toAddress = usuario.getEmailUsuario();
				String fromAddress = "larepublica.vecinal@gmail.com";
				String senderName = "Vecinal La República";
				String subject = titulo;
				String content = "[[name]],<br>" + descripcion + "<br><br>" + "Vecinal La República.";

				MimeMessage message = mailSender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(message);

				helper.setFrom(fromAddress, senderName);
				helper.setTo(toAddress);
				helper.setSubject(subject);

				content = content.replace("[[name]]", usuario.getNombre() + " " + usuario.getApellido());
				String verifyURL = "http://localhost:8081/usuarios/verificacion/" + usuario.getCodValidacion();

				content = content.replace("[[URL]]", verifyURL);

				helper.setText(content, true);

				mailSender.send(message);
			}

		}

	}*/

}
