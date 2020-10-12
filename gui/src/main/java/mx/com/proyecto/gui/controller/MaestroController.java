package mx.com.proyecto.gui.controller;

import java.time.LocalDateTime;

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

import mx.com.proyecto.config.model.CatConfig;
import mx.com.proyecto.gui.service.BoletosService;
import mx.com.proyecto.gui.service.CatConfigService;
import mx.com.proyecto.gui.service.CatalogoService;
import mx.com.proyecto.gui.service.ServidorPublicoService;
import mx.com.proyecto.servidor.dto.ServidorPublicoDto;
import mx.com.proyecto.servidor.model.ServidorPublico;

@Controller
@RequestMapping("/maestros")
public class MaestroController {
	
	private static final Short CARGADO = 0;
	private static final Short ENTRO = 1;
	private static final Short CON_BOLETO = 2;
	private static final Short POR_VALIDAR = 3;
	private static final Short VALIDADO = 4;
	private static final Short REGISTRADO = 5;
	private static final Short NAN_REGION = 99;
	
	private static final Integer EST_SISTEMA_PROXIMO = 0;
	private static final Integer EST_SISTEMA_CAPTURA = 1;
	private static final Integer EST_SISTEMA_CERRADO = 2;
	
	@Autowired
	private CatConfigService configService;
	
	@Autowired
	private ServidorPublicoService service;
	
	@Autowired
	private BoletosService boletos;

	@Autowired
	private CatalogoService serviceCat;
	
	
	private static final Short ADMIN=1;
	
	@GetMapping
	public ModelAndView root(Model model) {
		ModelAndView modelAndView = new ModelAndView("maestros");
		CatConfig conf = configService.obtenConfig(ADMIN);
		
		if(conf==null || conf.getEstatusSistema().equals(EST_SISTEMA_PROXIMO)) {
			 modelAndView.setViewName("proximamente");
			 return modelAndView;
		}
		if(conf.getEstatusSistema().equals(EST_SISTEMA_CERRADO)) {
			 modelAndView.setViewName("sistemacerrado");
			 return modelAndView;
		}
		
		modelAndView.addObject("user",new ServidorPublicoDto());
		return modelAndView;
	}

	@PostMapping
    public ModelAndView registerServidor(@ModelAttribute("user") @Valid ServidorPublicoDto userDto, BindingResult result){
		
		ModelAndView modelAndView = new ModelAndView("maestros");
		
		CatConfig conf = configService.obtenConfig(ADMIN);
		
		if(conf==null || conf.getEstatusSistema().equals(EST_SISTEMA_PROXIMO)) {
			 modelAndView.setViewName("proximamente");
			 return modelAndView;
		}
		
		if(conf.getEstatusSistema().equals(EST_SISTEMA_CERRADO)) {
			 modelAndView.setViewName("sistemacerrado");
			 return modelAndView;
		}
		/*ServidorPublico s = service.obtenServidorPublicoByCorreo(userDto.getEmail());
		
        if (s != null && !s.getCveServidorPublico().equals(userDto.getCveServidorPublico())){
            result.rejectValue("email", null, "Ya fue registrado este correo");
            return modelAndView;
        }*/
        
		ServidorPublico s = service.obtenServidorPublicoByID(userDto.getCveServidorPublico());

        if(s != null) {
       	
        	if(s.getEstatus().equals(CON_BOLETO)) {
        		result.rejectValue("cveServidorPublico", null, "Profesor(a), le informamos que sus datos ya han sido registrados con éxito anteriormente");
        		return modelAndView;
        	}else if(s.getEstatus().equals(POR_VALIDAR)) {
        		result.rejectValue("cveServidorPublico", null, "Profesor(a), es necesario esperar la validación de su información");
        		return modelAndView;
        	}else if(s.getEstatus().equals(REGISTRADO)) {
        		modelAndView.setViewName("capturasinreg");
        		userDto.setPagino(0);
        		modelAndView.addObject("user", userDto);
            	modelAndView.addObject("regiones", serviceCat.obtenCatalogoRegiones());
        		return modelAndView;
        	}
        	
        	s.setEmail(userDto.getEmail());
        	s.setTelefono(userDto.getTelefono());
        	s.setEstatus(ENTRO);
        	s.setFechaActualizacion(LocalDateTime.now());
        	service.actualizaServidorPublico(s);
        }else {

        	s = new ServidorPublico();
        	s.setCveServidorPublico(userDto.getCveServidorPublico());
        	s.setEmail(userDto.getEmail());
        	s.setTelefono(userDto.getTelefono());
        	s.setFechaActualizacion(LocalDateTime.now());
        	s.setEstatus(REGISTRADO);
        	s.setIdRegion(NAN_REGION);
        	modelAndView.setViewName("capturasinreg");
        	modelAndView.addObject("regiones", serviceCat.obtenCatalogoRegiones());
        	service.actualizaServidorPublico(s);
        	return modelAndView;
        	
        }
        userDto = new ServidorPublicoDto(s);
        userDto.setPagina(0);
        userDto.setPagino(2);
        userDto.setTotalPaginas((int) boletos.cuantaBoletosLibres(userDto.getIdRegion())/200);
        modelAndView.addObject("boletos", boletos.obtenBoletosLibres(s.getIdRegion(), 0));
        modelAndView.setViewName("captura");
        modelAndView.addObject("user", userDto);
        
        return modelAndView;
        
    }
}