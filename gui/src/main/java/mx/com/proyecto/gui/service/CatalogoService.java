package mx.com.proyecto.gui.service;

import java.util.List;

import mx.com.proyecto.catalogo.model.Catalogo;
import mx.com.proyecto.catalogo.model.SubCatalogo;

public interface CatalogoService {

	

	public List<Catalogo> obtenCatalogoRegiones();

	public List<SubCatalogo> obtenCatalogoZonaEscolar(Integer idRegion);

	public List<Catalogo> obtenCatalogoTodo();

	public String obtenZonaEscolar(Short idRegion, String zonaEscolar);

}
