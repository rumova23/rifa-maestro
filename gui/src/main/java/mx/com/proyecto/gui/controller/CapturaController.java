package mx.com.proyecto.gui.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

import mx.com.proyecto.gui.exception.PacGuiProcessException;
import mx.com.proyecto.gui.integration.service.RemoteCorreoService;
import mx.com.proyecto.gui.service.BoletosService;
import mx.com.proyecto.gui.service.ServidorPublicoService;
import mx.com.proyecto.servidor.dto.ServidorPublicoDto;
import mx.com.proyecto.servidor.model.ServidorPublico;

@Controller
@RequestMapping("/captura")
public class CapturaController {
	private static final Short CARGADO = 0;
	private static final Short ENTRO = 1;
	private static final Short CON_BOLETO = 2;
	private static final Short POR_VALIDAR = 3;
	private static final Short VALIDADO = 4;
	private static final Short NAN_REGION = 99;
	

	@Autowired
	private BoletosService boletos;
	
	@Autowired
	private ServidorPublicoService service;
	
	@Autowired
	private RemoteCorreoService correoService;

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
		ModelAndView modelAndView = new ModelAndView("captura");
		
		
		ServidorPublico s = service.obtenServidorPublicoByID(userDto.getCveServidorPublico());
       
        if(s != null) {
        	if(userDto.getPagino().equals(1) || userDto.getPagino().equals(0)) {
        		ServidorPublicoDto ns= new ServidorPublicoDto(s);
        		if(userDto.getPagino().equals(1)) {
        			modelAndView.addObject("boletos", boletos.obtenBoletosLibres(userDto.getIdRegion(), userDto.getPagina()+1));
        			ns.setPagina(userDto.getPagina()+1);
        		}else if(userDto.getPagino().equals(0)) {
        			modelAndView.addObject("boletos", boletos.obtenBoletosLibres(userDto.getIdRegion(), userDto.getPagina()-1));
        			ns.setPagina(userDto.getPagina()-1);
        		}
        		
        		ns.setPagino(2);
        		
        		ns.setTotalPaginas((int) (boletos.cuantaBoletosLibres(userDto.getIdRegion())/200));
        		modelAndView.addObject("user", ns);
        		return modelAndView;
        	}
        	
        	
        	if(s.getEstatus().equals(CON_BOLETO)) {
    			modelAndView.addObject("user", new ServidorPublico());
    			modelAndView.addObject("boletos", boletos.obtenBoletosLibres(userDto.getIdRegion(), 0));
            	result.rejectValue("cveServidorPublico", null, "Servidor publico ya tiene boleto asignado");
            	 modelAndView.setViewName("maestros");
                 return modelAndView;
    		}else {
    			ServidorPublico sm = service.obtenServidorPublicoByBoleto(userDto.getBoleto());
    			if(sm !=null) {
    				ServidorPublicoDto ns= new ServidorPublicoDto(s);
            		ns.setPagino(2);
            		ns.setPagina(1);
            		ns.setTotalPaginas((int) (boletos.cuantaBoletosLibres(userDto.getIdRegion())/200));
    				modelAndView.addObject("user", sm);
    	        	result.rejectValue("boleto", null, "Este boleto ya fue seleccionado por alguien mas, selecciona otro");
    	        	modelAndView.addObject("boletos", boletos.obtenBoletosLibres(userDto.getIdRegion(), 0));
    	        	return modelAndView;
    			}

    		}
        	s.setBoleto(userDto.getBoleto());
        	s.setFechaAsignacion(LocalDateTime.now());
        	s.setAsignado(true);
        	s.setEstatus(CON_BOLETO);
        	try {
				boletos.apartaBoleto(userDto.getBoleto());
			} catch (PacGuiProcessException e) {
				ServidorPublicoDto ns= new ServidorPublicoDto(s);
        		ns.setPagino(2);
        		ns.setPagina(1);
        		ns.setTotalPaginas((int) (boletos.cuantaBoletosLibres(userDto.getIdRegion())/200));
				modelAndView.addObject("user", ns);
	        	result.rejectValue("boleto", null, "Este boleto ya fue seleccionado por alguien mas, selecciona otro");
	        	modelAndView.addObject("boletos", boletos.obtenBoletosLibres(userDto.getIdRegion(), 0));
	        	return modelAndView;
			}
        	service.actualizaServidorPublico(s);
        }else {
        	modelAndView.setViewName("maestros");
        	modelAndView.addObject("user", new ServidorPublico());
        	result.rejectValue("cveServidorPublico", null, "No existe servidor público.");
       		return modelAndView;
        }
        try {
        	correoService.enviaEmail(1, userDto.getCveServidorPublico());
        }catch (Exception e) {
			e.printStackTrace();
		}
        modelAndView.setViewName("exito");
        return modelAndView;
    }
}