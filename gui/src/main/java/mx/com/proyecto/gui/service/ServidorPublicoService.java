package mx.com.proyecto.gui.service;

import mx.com.proyecto.gui.dto.Filtro;
import mx.com.proyecto.gui.dto.Resultado;
import mx.com.proyecto.servidor.model.ServidorPublico;

public interface ServidorPublicoService {

	public ServidorPublico obtenServidorPublicoByID(Long cveServidorPublico);

	public void actualizaServidorPublico(ServidorPublico servidor);

	public ServidorPublico obtenServidorPublicoByCorreo(String correo);

	public ServidorPublico obtenServidorPublicoByBoleto(Long boleto);

	public Resultado obtenListaServidoresPublicos(Filtro filtro, boolean todo);


}
