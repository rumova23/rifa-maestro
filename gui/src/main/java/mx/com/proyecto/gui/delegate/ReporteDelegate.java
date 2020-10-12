package mx.com.proyecto.gui.delegate;

import java.io.ByteArrayInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import mx.com.proyecto.gui.dto.Filtro;
import mx.com.proyecto.gui.dto.Resultado;
import mx.com.proyecto.gui.service.CatalogoService;
import mx.com.proyecto.gui.service.ServidorPublicoService;
import mx.com.proyecto.gui.service.TransformToExcel;

@Service
public class ReporteDelegate {

	@Autowired
	private CatalogoService serviceCat;
	
	@Autowired
	private ServidorPublicoService service;
	
	@Autowired
	private TransformToExcel excel;
	
	public ModelAndView getDatos(Filtro filtro) {
		
		ModelAndView modelAndView = new ModelAndView("reporte");
		modelAndView.addObject("regiones", serviceCat.obtenCatalogoRegiones());
		if((filtro.getEstatus()==null || filtro.getEstatus().length==0) && (filtro.getRegion()==null || filtro.getRegion().length==0) && filtro.getBoleto()==null &&  filtro.getCveServidorPublico()==null) {
			modelAndView.addObject("resultado", new Resultado());
			return modelAndView;
		}
		
		modelAndView.addObject("resultado",service.obtenListaServidoresPublicos(filtro, false));
		return modelAndView;
	}

	public ModelAndView getDescarga(Filtro filtro) {
		ModelAndView modelAndView = new ModelAndView("descarga");
		modelAndView.addObject("regiones", serviceCat.obtenCatalogoRegiones());
		return modelAndView;
	}

	public ByteArrayInputStream putDescarga(Filtro filtro) {
		
		
		if((filtro.getEstatus()==null || filtro.getEstatus().length==0) && (filtro.getRegion()==null || filtro.getRegion().length==0) && filtro.getBoleto()==null &&  filtro.getCveServidorPublico()==null) {
			return null;
		}
		
		return excel.creaExcel(service.obtenListaServidoresPublicos(filtro, true).getLista());
	
	}
}
