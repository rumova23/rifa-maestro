package mx.com.proyecto.gui.integration.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name= "correo")
public interface RemoteCorreoService {
	
	@GetMapping(value = "/correo/v1/correos/envia/{tipo}/{servidorPublico}", produces=MediaType.APPLICATION_JSON_VALUE)
	public void enviaEmail(@PathVariable("tipo") Integer tipo, @PathVariable("servidorPublico") Long servidorPublico) ;
	
}
