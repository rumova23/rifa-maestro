package mx.com.proyecto.gui.controller;

import java.time.LocalDateTime;

import javax.validation.Valid;

import org.apache.tomcat.jni.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import mx.com.proyecto.gui.service.CatalogoService;
import mx.com.proyecto.gui.service.ServidorPublicoService;
import mx.com.proyecto.servidor.dto.ServidorPublicoDto;
import mx.com.proyecto.servidor.model.ServidorPublico;

@Controller
@RequestMapping("/capturasinreg")
public class CapturaSinRegController {
	private static final Short CARGADO = 0;
	private static final Short ENTRO = 1;
	private static final Short CON_BOLETO = 2;
	private static final Short POR_VALIDAR = 3;
	private static final Short VALIDADO = 4;
	private static final Short NAN_REGION = 99;

	@Autowired
	private CatalogoService serviceCat;
	
	@Autowired
	private ServidorPublicoService service;

	
	
    @ModelAttribute("user")
    public ServidorPublicoDto servidorPublicoDto() {
        return new ServidorPublicoDto();
    }

	
	@GetMapping
	public String root(Model model) {
		return "redirect:maestros";
	}

	@PostMapping
    public ModelAndView registerServidor(@ModelAttribute("user") @Valid ServidorPublicoDto userDto, BindingResult result){
		ModelAndView modelAndView = new ModelAndView("capturasinreg");
		
		if(userDto.getIdRegion()==null ) {
			modelAndView.addObject("regiones", serviceCat.obtenCatalogoRegiones());
			userDto.setPagino(0);
			modelAndView.addObject("user", userDto);
			return modelAndView;
		}
		
		if(userDto.getPagino().equals(0) || userDto.getZonaEscolar()==null || userDto.getZonaEscolar().isEmpty()) {
			modelAndView.addObject("regiones", serviceCat.obtenCatalogoRegiones());
			modelAndView.addObject("zonasEscolares", serviceCat.obtenCatalogoZonaEscolar(userDto.getIdRegion().intValue()));
			userDto.setPagino(0);
			modelAndView.addObject("user", userDto);
			   return modelAndView;
		}
		
		ServidorPublico s = new ServidorPublico(userDto);
		s.setEstatus(POR_VALIDAR);
    	s.setFechaActualizacion(LocalDateTime.now());
    	service.actualizaServidorPublico(s);
    	
        modelAndView.setViewName("porvalidar");
        return modelAndView;
    }
}