package mx.com.proyecto.gui.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import mx.com.proyecto.catalogo.model.Catalogo;
import mx.com.proyecto.catalogo.model.SubCatalogo;
import mx.com.proyecto.gui.repository.CatalogoRepository;
import mx.com.proyecto.gui.service.CatalogoService;

@Service
public class CatalogoServiceImpl implements CatalogoService {

	@Autowired
	private CatalogoRepository repo;
	

	
	@Override
	@Cacheable("zonas")
	public List<SubCatalogo> obtenCatalogoZonaEscolar(Integer idRegion) {
		List<Catalogo> reg = obtenCatalogoTodo();
		
		for(Catalogo c:reg) {
			if(c.getIdCatalogo().equals(idRegion)) {
				return c.getSubCatalogo();
			}
		}
		return null;
	}
	
	@Override
	@Cacheable("regiones")
	public List<Catalogo> obtenCatalogoRegiones() {
		List<Catalogo> reg = obtenCatalogoTodo();
		List<Catalogo> lista = new ArrayList<Catalogo>();
		for(Catalogo c:reg) {
			Catalogo c2= new Catalogo();
			c2.setDescripcion(c.getDescripcion());
			c2.setIdCatalogo(c.getIdCatalogo());
			
			lista.add(c2);
		}
		return lista;
	}
	
	@Override
	@Cacheable("todo")
	public List<Catalogo> obtenCatalogoTodo() {
		List<Catalogo> catalogo = repo.findAll();
		if(catalogo!=null) {
			return catalogo;
		}
		Catalogo c =  new Catalogo();
		c.setDescripcion("Sin informacion de catalogo");
		c.setIdCatalogo(99);
		catalogo = new ArrayList<Catalogo>();
		catalogo.add(c);
		return catalogo;
	}

	@Override
	@Cacheable("idZona")
	public String obtenZonaEscolar(Short idRegion, String zonaEscolar) {
		List<SubCatalogo> reg = obtenCatalogoZonaEscolar(new Integer(idRegion));
		for(SubCatalogo r : reg) {
			if(r.getIdSubCatalogo().equals(new Integer(zonaEscolar))) {
				return r.getDescripcion();
			}
		}
			
		
		
		return null;
	}
}
