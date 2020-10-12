package mx.com.proyecto.gui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import mx.com.proyecto.servidor.dto.ServidorPublicoDto;

@Controller
@RequestMapping("/porvalidar")
public class PorvalidarController {
	


	
	@GetMapping
	public String root(Model model) {
		return "redirect:maestros";
	}

	
}