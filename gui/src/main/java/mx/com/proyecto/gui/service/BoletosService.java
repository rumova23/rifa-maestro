package mx.com.proyecto.gui.service;

import java.util.List;

import mx.com.proyecto.boleto.model.Boleto;
import mx.com.proyecto.gui.exception.PacGuiProcessException;

public interface BoletosService {

	public List<Boleto> obtenBoletosLibres(Short idRegion, Integer pagina);

	public void apartaBoleto(Long boleto) throws PacGuiProcessException;

	long cuantaBoletosLibres(Short idRegion);

}
