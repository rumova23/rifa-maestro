package mx.com.proyecto.gui.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.sun.org.apache.bcel.internal.generic.SWITCH;

import lombok.RequiredArgsConstructor;
import mx.com.proyecto.gui.service.TransformToExcel;
import mx.com.proyecto.servidor.model.ServidorPublico;

@Service
@RequiredArgsConstructor
public class TransformToExcelImpl implements TransformToExcel {

	@Override
	public ByteArrayInputStream creaExcel(List<ServidorPublico> lista) {

		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream bos = new ByteArrayOutputStream()){

			Sheet pagina = workbook.createSheet("Reporte de Patrones");

			CellStyle style = workbook.createCellStyle();
			Font font = workbook.createFont();
			font.setFontName("Arial");
			style.setFillForegroundColor(HSSFColor.BLUE.index);
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			font.setBold(true);
			font.setColor(HSSFColor.WHITE.index);
			style.setFont(font);

			String[] titulos = {"CLAVE DE DOCENTE","NOMBRE","REGION","ZONA ESCOLAR","EMAIL","TELEFONO","ESTATUS","FECHA ALTA","FECHA ASIGNACION","BOLETO"};

			Row fila = pagina.createRow(0);

			for (int i = 0; i < titulos.length; i++) {
				Cell celda = fila.createCell(i);
				celda.setCellStyle(style);
				celda.setCellValue(titulos[i]);
			}

			int x = 1;
			int i = 0;

			if(lista!=null && !lista.isEmpty()) {
				for (ServidorPublico meta : lista) {

					fila = pagina.createRow(x++);
					
					fila.createCell(i++).setCellValue(meta.getCveServidorPublico());
					fila.createCell(i++).setCellValue(meta.getNombreCompleto());
					fila.createCell(i++).setCellValue(meta.getIdRegion());
					fila.createCell(i++).setCellValue(meta.getZonaEscolar());
					fila.createCell(i++).setCellValue(meta.getEmail());
					fila.createCell(i++).setCellValue(meta.getTelefono());

					switch (meta.getEstatus()) {
					case 0:
						fila.createCell(i++).setCellValue("Cargado en sistema");
						break;
					case 1:
						fila.createCell(i++).setCellValue("Entro a sistema");
						break;
					case 2:
						fila.createCell(i++).setCellValue("Con boleto");
						break;
					case 3:
						fila.createCell(i++).setCellValue("Registro manual Pendiente de validacion");
						break;
					case 4:
						fila.createCell(i++).setCellValue("Registro manual Validado por administrador");
						break;
					case 5:
						fila.createCell(i++).setCellValue("Registro manual sin terminar");
						break;
					case 6:
						fila.createCell(i++).setCellValue("Bloqueado por Administrador");
						break;
					case 7:
						fila.createCell(i++).setCellValue("Eliminado por Administrador");
						break;
					default:
						fila.createCell(i++).setCellValue("Estatus desconocido");
						break;
					}
					if(meta.getFechaCarga()==null)
						fila.createCell(i++).setCellValue("");
					else
						fila.createCell(i++).setCellValue(meta.getFechaCarga().toString());
					
					if(meta.getFechaAsignacion()==null)
						fila.createCell(i++).setCellValue("");
					else
						fila.createCell(i++).setCellValue(meta.getFechaAsignacion().toString());
					fila.createCell(i++).setCellValue(meta.getBoleto());
					
					i = 0;
				}
			}
			workbook.write(bos);

			return new ByteArrayInputStream(bos.toByteArray());
		} catch (IOException e) {
			return null;
		}
	}

	

	public static String validaNulo(Object object) {
		return object != null ? object.toString() : "";
	}

}	