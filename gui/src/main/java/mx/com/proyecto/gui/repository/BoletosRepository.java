package mx.com.proyecto.gui.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import mx.com.proyecto.boleto.model.Boleto;

public interface BoletosRepository extends CrudRepository<Boleto, Long>{
	
	public List<Boleto> findByIdRegionAndEstatus(Short idRegion, Boolean estatus, Pageable pageable);
	
	public Optional<Boleto> findByIdAndEstatus(Long id, Boolean estatus);
	
	public long countByIdRegionAndEstatus(Short idRegion, boolean estatus);

}
