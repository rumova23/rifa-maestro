package mx.com.proyecto.gui.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode 
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter 
@Setter
public class Filtro {
	private Long cveServidorPublico;
	private Long boleto;
	private Integer descarga;
	private Integer[] estatus;
	private Integer[] region;
	
	private Integer pagina=0;
}
