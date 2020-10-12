package mx.com.proyecto.gui.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.proyecto.gui.dto.Filtro;
import mx.com.proyecto.gui.dto.Resultado;
import mx.com.proyecto.gui.repository.ServidorPublicoJdbcRepository;
import mx.com.proyecto.gui.repository.ServidorPublicoRepository;
import mx.com.proyecto.gui.service.ServidorPublicoService;
import mx.com.proyecto.servidor.model.ServidorPublico;

@Service
public class ServidorPublicoServiceImpl implements ServidorPublicoService{

	@Autowired
	private ServidorPublicoRepository repositorio;
	
	@Autowired
	private ServidorPublicoJdbcRepository jdbcRepositorio;
	
	
	@Override
	public ServidorPublico obtenServidorPublicoByID(Long cveServidorPublico) {
		Optional<ServidorPublico> o = repositorio.findById(cveServidorPublico);
		if(o.isPresent())
			return o.get();
		return null;
	}
	
	@Override
	public ServidorPublico obtenServidorPublicoByCorreo(String correo) {
		Optional<ServidorPublico> o = repositorio.findByEmail(correo);
		if(o.isPresent())
			return o.get();
		return null;
	}
	
	@Override
	public void actualizaServidorPublico(ServidorPublico servidor) {
		repositorio.save(servidor);
	}

	@Override
	public Resultado obtenListaServidoresPublicos(Filtro filtro, boolean todo) {
		if(filtro.getEstatus()!=null && filtro.getEstatus().length>0) {
			for(int x=0; x<filtro.getEstatus().length;x++) {
				if(filtro.getEstatus()[x].equals(0)) {
					
				}
			}
			
			Integer[] estatus = {0,1,4};
			filtro.setEstatus(estatus);
		}
		return jdbcRepositorio.getData(filtro, todo);
	}

	
	@Override
	public ServidorPublico obtenServidorPublicoByBoleto(Long boleto) {
		Optional<ServidorPublico> o = repositorio.findByBoleto(boleto);
		if(o.isPresent())
			return o.get();
		return null;
	}
	
}
