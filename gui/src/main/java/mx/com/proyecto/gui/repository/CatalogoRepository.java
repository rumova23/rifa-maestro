package mx.com.proyecto.gui.repository;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

import mx.com.proyecto.catalogo.model.Catalogo;

public interface CatalogoRepository extends CrudRepository<Catalogo, Integer>{
	
	@Override
    public List<Catalogo> findAll();
}
