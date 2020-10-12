package mx.com.proyecto.gui.delegate;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import mx.com.proyecto.gui.dto.Filtro;
import mx.com.proyecto.gui.dto.Resultado;
import mx.com.proyecto.gui.service.CatalogoService;
import mx.com.proyecto.gui.service.ServidorPublicoService;
import mx.com.proyecto.servidor.model.ServidorPublico;

@Service
public class ServidorDelegate {
	
	@Autowired
	private ServidorPublicoService service;
	
	@Autowired
	private CatalogoService serviceCat;
	
	public ModelAndView getDatos(Long cveServidorPublico) {
		ModelAndView modelAndView = new ModelAndView("actualizaservidor");
		if(cveServidorPublico!=null) {
			ServidorPublico s = service.obtenServidorPublicoByID(cveServidorPublico);
			modelAndView.addObject("user", s);
			return modelAndView;
		}
		modelAndView.setViewName("home");
		return modelAndView;
	}

	public ModelAndView postDatos(ServidorPublico servidorPublico) {

		ModelAndView modelAndView = new ModelAndView("reporte");
		
		ServidorPublico s = service.obtenServidorPublicoByID(servidorPublico.getCveServidorPublico());
		s.setNombreCompleto(servidorPublico.getNombreCompleto());
		s.setTelefono(servidorPublico.getTelefono());
		s.setEmail(servidorPublico.getEmail());
		s.setEstatus(servidorPublico.getEstatus());
		service.actualizaServidorPublico(servidorPublico);
		Filtro f =  new Filtro();
		//f.setCveServidorPublico(servidorPublico.getCveServidorPublico());
		modelAndView.addObject("filtro", f);
		modelAndView.addObject("regiones", serviceCat.obtenCatalogoRegiones());
		modelAndView.addObject("resultado", new Resultado());
		return modelAndView;
	}

}
