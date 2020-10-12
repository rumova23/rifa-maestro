package mx.com.proyecto.gui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/exito2")
public class ExitoController2 {
	


	
	@GetMapping
	public String root(Model model) {
		return "redirect:maestros";
	}

	
}