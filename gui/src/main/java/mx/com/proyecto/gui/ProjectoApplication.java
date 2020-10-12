package mx.com.proyecto.gui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableCaching
@SpringBootApplication
@ComponentScan({"mx.com.proyecto.gui"})
@EnableJpaRepositories({"mx.com.proyecto.gui.repository"})
@EnableFeignClients(basePackages = {"mx.com.proyecto.gui.integration.service"})
@EntityScan({"mx.com.proyecto.config.model","mx.com.proyecto.servidor.model", "mx.com.proyecto.region.model","mx.com.proyecto.boleto.model","mx.com.proyecto.catalogo.model"})
public class ProjectoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectoApplication.class, args);
	}

}
