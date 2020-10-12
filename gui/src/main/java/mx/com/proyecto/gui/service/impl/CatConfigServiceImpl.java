package mx.com.proyecto.gui.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.proyecto.config.model.CatConfig;
import mx.com.proyecto.gui.repository.CatConfigRepository;
import mx.com.proyecto.gui.service.CatConfigService;

@Service
public class CatConfigServiceImpl implements CatConfigService{

	@Autowired
	private CatConfigRepository repositorio;
	
	@Override
	public CatConfig obtenConfig(Short id) {
		Optional<CatConfig> o = repositorio.findById(id);
		if(o.isPresent())
			return o.get();
		return null;
	}
	
	
	@Override
	public void actualizaConfig(CatConfig config) {
		repositorio.save(config);
	}
}
