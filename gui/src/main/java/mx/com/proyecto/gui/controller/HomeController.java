package mx.com.proyecto.gui.controller;

import java.io.ByteArrayInputStream;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.ModelAndView;

import mx.com.proyecto.gui.delegate.ConfigDelegate;
import mx.com.proyecto.gui.delegate.ReporteDelegate;
import mx.com.proyecto.gui.delegate.ServidorDelegate;
import mx.com.proyecto.gui.dto.Config;
import mx.com.proyecto.gui.dto.Filtro;
import mx.com.proyecto.servidor.model.ServidorPublico;

@Controller
public class HomeController {
	
	
	@Autowired
	private ConfigDelegate cargaDelegate;
	
	@Autowired
	private ReporteDelegate reporteDelegate;
	
	@Autowired
	private ServidorDelegate servidorDelegate;
	
	@GetMapping("/")
	public String root() {
		return "home";
	}

	@GetMapping("/home")
	public String home() {
		return "home";
	}
	

	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/403")
	public String accessDenied() {
		return "error/403";
	}
	
	@GetMapping("/configura")
	public ModelAndView getConfigura() {
		return cargaDelegate.getDatos();
	}

	@PostMapping("/configura")
	public ModelAndView postConfigura(Config config) {
		return cargaDelegate.putDatos(config);
	}
	
	@GetMapping("/reporte")
	public ModelAndView getReporte(Filtro filtro) {
		return reporteDelegate.getDatos(filtro);
	}
	
	@PostMapping("/reporte")
	public ModelAndView postReporte(Filtro filtro) {
		return reporteDelegate.getDatos(filtro);
	}
	
	@GetMapping("/descarga")
	public ModelAndView getDescarga(Filtro filtro) {
		return reporteDelegate.getDescarga(filtro);
	}
	
	@PostMapping("/descarga")
	public ResponseEntity<InputStreamResource> putDescarga(Filtro filtro) {
		System.out.println("buscando: "+filtro);
		
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=reporte.xlsx");
        
        ByteArrayInputStream b = reporteDelegate.putDescarga(filtro);
        
        System.out.println("descargandi "+b);
        return ResponseEntity.ok().headers(headers).body(new InputStreamResource(b));	
		
	}
	
	@GetMapping("/actualizaservidor")
	public ModelAndView getActualizaServidor(Long cveServidorPublico) {
		return servidorDelegate.getDatos(cveServidorPublico);
	}
	
	@PostMapping("/actualizaservidor")
	public ModelAndView postActualizaServidor(ServidorPublico user) {
		return servidorDelegate.postDatos(user);
	}
}