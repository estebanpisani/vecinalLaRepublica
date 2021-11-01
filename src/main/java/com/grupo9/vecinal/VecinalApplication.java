package com.grupo9.vecinal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.grupo9.vecinal.Servicios.UsuarioServicio;

@SpringBootApplication
public class VecinalApplication {
	
	@Autowired
	private UsuarioServicio usuarioServ;

	public static void main(String[] args) {
		SpringApplication.run(VecinalApplication.class, args);
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(usuarioServ).passwordEncoder(new BCryptPasswordEncoder());
		
	}

}
