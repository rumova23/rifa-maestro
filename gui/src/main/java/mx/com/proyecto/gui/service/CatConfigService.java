package mx.com.proyecto.gui.service;

import mx.com.proyecto.config.model.CatConfig;

public interface CatConfigService {

	public CatConfig obtenConfig(Short id);

	void actualizaConfig(CatConfig config);

}
