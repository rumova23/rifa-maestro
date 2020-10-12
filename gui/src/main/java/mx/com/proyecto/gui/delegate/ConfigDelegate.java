package mx.com.proyecto.gui.delegate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import mx.com.proyecto.config.model.CatConfig;
import mx.com.proyecto.gui.dto.Config;
import mx.com.proyecto.gui.service.CatConfigService;


@Service
public class ConfigDelegate {

	@Autowired
	private CatConfigService service;
	
	private static final short UNO=1;
	public ModelAndView getDatos() {
		ModelAndView modelAndView = new ModelAndView("configura");
		CatConfig con =  service.obtenConfig(UNO);
		Config config = new Config();
		config.setConfig(con.getEstatusSistema());
		modelAndView.addObject("config", config);
		return modelAndView;
	}
	
	
	public ModelAndView putDatos(Config config) {
		ModelAndView modelAndView = new ModelAndView("configura");
		CatConfig con =  service.obtenConfig(UNO);
		con.setEstatusSistema(config.getConfig());
		service.actualizaConfig(con);
		modelAndView.addObject("config", config);
		return modelAndView;
	}

}
