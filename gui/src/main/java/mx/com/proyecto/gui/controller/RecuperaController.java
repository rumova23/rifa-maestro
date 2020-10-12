package mx.com.proyecto.gui.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import mx.com.proyecto.boleto.dto.Recupera;
import mx.com.proyecto.config.model.CatConfig;
import mx.com.proyecto.gui.service.CatConfigService;

@Controller
@RequestMapping("/recupera")
public class RecuperaController {
	

	private static final Short EST_SISTEMA_PROXIMO = 0;
	private static final Short EST_SISTEMA_CAPTURA = 1;
	private static final Short EST_SISTEMA_CERRADO = 2;
	private static final Short ADMIN=1;
	
	@Autowired
	private CatConfigService configService;
	
	@GetMapping
	public ModelAndView root(Model model) {
		CatConfig conf = configService.obtenConfig(ADMIN);
		ModelAndView modelAndView = new ModelAndView("recupera");
		if(conf==null || conf.getEstatusSistema().equals(EST_SISTEMA_PROXIMO)) {
			 modelAndView.setViewName("proximamente");
			 return modelAndView;
		}
		if(conf.getEstatusSistema().equals(EST_SISTEMA_CERRADO)) {
			 modelAndView.setViewName("sistemacerrado");
			 return modelAndView;
		}
		Recupera r = new Recupera();
		modelAndView.addObject("recupera", r);
		return modelAndView;
	}

	@PostMapping
	public ModelAndView recupera(@ModelAttribute("cve") @Valid Recupera r, BindingResult result) {
		ModelAndView modelAndView = new ModelAndView("recupera");
		
		modelAndView.addObject("recupera", r);
		return modelAndView;
	}
	
}