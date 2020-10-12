package mx.com.proyecto.gui.service;

import java.util.List;

import mx.com.proyecto.catalogo.model.Catalogo;
import mx.com.proyecto.catalogo.model.SubCatalogo;

public interface CatalogoService {

	

	public List<Catalogo> obtenCatalogoRegiones();

	public List<SubCatalogo> obtenCatalogoZonaEscolar(Integer idRegion);

	List<Catalogo> obtenCatalogoTodo();

}
