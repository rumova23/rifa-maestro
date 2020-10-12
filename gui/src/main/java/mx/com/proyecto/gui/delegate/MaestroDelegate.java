package mx.com.proyecto.gui.delegate;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

@Service
public class MaestroDelegate {

	public ModelAndView getDatos() {
		ModelAndView modelAndView = new ModelAndView("maestro");
		return modelAndView;
	}

}
