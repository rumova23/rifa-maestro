package mx.com.proyecto.gui.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import mx.com.proyecto.servidor.model.ServidorPublico;

public interface ServidorPublicoRepository extends CrudRepository<ServidorPublico, Long>{

	public Optional<ServidorPublico> findByEmail(String correo);
	
	public Optional<ServidorPublico> findByBoleto(Long boleto);


}
