package mx.com.proyecto.gui.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mx.com.proyecto.servidor.model.ServidorPublico;

@EqualsAndHashCode 
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter 
@Setter
public class Resultado {
	
	private List<ServidorPublico> lista = new ArrayList<>();
	private Integer totalPages;
	private Integer	total;
	private Integer	pageNumber=1;
	private Integer	inicioPag;
	private Integer	finPag;
	
}
