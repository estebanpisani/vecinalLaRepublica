package com.grupo9.vecinal.Controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class IndexControlador {

	@GetMapping("/")
	public String index() {
		
		
		return "index.html";
	}
	
	@GetMapping("/login")
	public String login(ModelMap modelo, @RequestParam(required = false) String error) {
		if (error != null) {
			modelo.put("error", "Usuario o contraseña incorrectas");
		}
		return "login.html";
	}
}
