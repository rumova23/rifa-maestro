package mx.com.proyecto.gui.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import mx.com.proyecto.boleto.model.Boleto;
import mx.com.proyecto.gui.exception.PacGuiProcessException;
import mx.com.proyecto.gui.repository.BoletosRepository;
import mx.com.proyecto.gui.service.BoletosService;

@Service
public class BoletosServiceImpl implements BoletosService {

	@Autowired
	private BoletosRepository repo;
	
	@Override
	public List<Boleto> obtenBoletosLibres(Short idRegion, Integer pagina){
		Pageable page = PageRequest.of(pagina, 200);
		List<Boleto> boletos = repo.findByIdRegionAndEstatus(idRegion, true, page);
		return boletos;
	}

	
	
	@Override
	public long cuantaBoletosLibres(Short idRegion){
		
		long l = repo.countByIdRegionAndEstatus(idRegion, true);
		return l;
	}

	
	@Override
	public void apartaBoleto(Long boleto) throws PacGuiProcessException {
		Optional<Boleto> b = repo.findByIdAndEstatus(boleto, true);
		if(b.isPresent()) {
			b.get().setEstatus(false);
			repo.save(b.get());
		}else {
			throw new PacGuiProcessException("Boleto ya se encuantra asignado");
		}
		
	}
}
