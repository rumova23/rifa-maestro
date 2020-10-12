package mx.com.proyecto.gui.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import mx.com.proyecto.gui.repository.CatRegionRepository;
import mx.com.proyecto.gui.service.CatRegionService;
import mx.com.proyecto.region.model.CatRegion;

@Service
public class CatRegionServiceImpl implements CatRegionService{

	@Autowired
	private CatRegionRepository repositorio;
	
	@Cacheable("region")
	@Override
	public CatRegion obtenRegion(Short idRegion) {
		Optional<CatRegion> o = repositorio.findById(idRegion);
		if(o.isPresent())
			return o.get();
		return null;
	}
}
