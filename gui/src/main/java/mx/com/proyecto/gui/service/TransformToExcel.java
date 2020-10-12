package mx.com.proyecto.gui.service;

import java.io.ByteArrayInputStream;
import java.util.List;

import mx.com.proyecto.servidor.model.ServidorPublico;

public interface TransformToExcel {

	public ByteArrayInputStream creaExcel(List<ServidorPublico> lista);

}
