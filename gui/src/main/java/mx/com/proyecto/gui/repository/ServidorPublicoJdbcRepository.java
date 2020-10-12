package mx.com.proyecto.gui.repository;

import mx.com.proyecto.gui.dto.Filtro;
import mx.com.proyecto.gui.dto.Resultado;

public interface ServidorPublicoJdbcRepository{

	public Resultado getData(Filtro filtro, boolean todo);

	public int count(Filtro filtro);



}
