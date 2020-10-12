package mx.com.proyecto.gui.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.com.proyecto.gui.dto.Filtro;
import mx.com.proyecto.gui.dto.Resultado;
import mx.com.proyecto.servidor.model.ServidorPublico;

@Repository
public class ServidorPublicoJdbcRepositoryImpl implements ServidorPublicoJdbcRepository{

	@Autowired
    private JdbcTemplate jdbcTemplate;

	@Override
	public Resultado getData(Filtro filtro, boolean todo) {
		StringBuilder str = new StringBuilder();
		str.append("select * from SERVIDOR_PUBLICO where ");
		if(filtro.getCveServidorPublico()!=null) {
			str.append(" cveServidorPublico = ");
			str.append(filtro.getCveServidorPublico());
			str.append(" and ");
		}
		if(filtro.getBoleto()!=null) {
			str.append(" boleto = ");
			str.append(filtro.getBoleto());
			str.append(" and ");
		}
		boolean borrar= true;
		if(filtro.getRegion()!=null &&filtro.getRegion().length>0)  {
			borrar= false;
			str.append(" idRegion in (");
			StringBuilder in = new StringBuilder();
			for(Integer x:filtro.getRegion()) {
				in.append(x);
				in.append(",");
			}
			str.append(in.toString().substring(0, in.toString().length()-1));
			str.append(" ) ");
			str.append(" and ");
		}
		if(filtro.getEstatus()!=null && filtro.getEstatus().length>0) {
			borrar= false;
			str.append(" estatus in (");
			StringBuilder in = new StringBuilder();
			for(Integer x:filtro.getEstatus()) {
				in.append(x);
				in.append(",");
	
			}
			str.append(in.toString().substring(0, in.toString().length()-1));
			str.append(" ) ");
		}
		
		if(borrar) {
			str.replace(str.lastIndexOf("and"), str.lastIndexOf("and")+3, "");
		}
		if(!todo) {
			if((filtro.getRegion()!=null &&filtro.getRegion().length>0) || (filtro.getEstatus()!=null && filtro.getEstatus().length>0))  {
				str.append("LIMIT 20 OFFSET "+filtro.getPagina());
			}
		}
		
		
		
		
		 List<ServidorPublico> lista =  jdbcTemplate.query(
				str.toString(),
                (rs, rowNum) ->
                        new ServidorPublico(
                        		rs.getLong("cveServidorPublico"), 
                        		rs.getString("nombreCompleto"), 
                        		rs.getShort("idRegion"), 
                        		rs.getString("zonaEscolar"),
                        		rs.getString("email"), 
                    			rs.getLong("telefono"),
                    			rs.getShort("estatus"), 
                    			rs.getLong("boleto"),
                    			rs.getString("fechaAsignacion"),
                    			rs.getString("fechaCarga")
                    			   
                        )
				 );
		 
		 int total = count(filtro);
		 Resultado res = new Resultado();
		 res.setLista(lista);
		 res.setTotal(total);
		 res.setTotalPages(total/20);
		 if(filtro.getPagina()==null || filtro.getPagina().equals(0)) {
			 res.setPageNumber(1);
		 }else {
			 res.setPageNumber(filtro.getPagina());
		 }
		 
		 res.setInicioPag((filtro.getPagina()-3)<=0?1:(filtro.getPagina()-3));
		 res.setFinPag((filtro.getPagina()-3)<=0?5:filtro.getPagina()+2);
		 
		 
		 
		 return res;

	}
	
	 
	@Override
	    public int count(Filtro filtro) {
		 	StringBuilder str = new StringBuilder();
			str.append("select count(*) from SERVIDOR_PUBLICO where ");
			
			
			if(filtro.getCveServidorPublico()!=null) {
				str.append(" cveServidorPublico = ");
				str.append(filtro.getCveServidorPublico());
				str.append(" and ");
			}
			if(filtro.getBoleto()!=null) {
				str.append(" boleto = ");
				str.append(filtro.getBoleto());
				str.append(" and ");
			}
			boolean borrar= true;
			if(filtro.getRegion()!=null &&filtro.getRegion().length>0)  {
				borrar= false;
				str.append(" idRegion in (");
				StringBuilder in = new StringBuilder();
				for(Integer x:filtro.getRegion()) {
					in.append(x);
					in.append(",");
				}
				str.append(in.toString().substring(0, in.toString().length()-1));
				str.append(" ) ");
				str.append(" and ");
			}
			if(filtro.getEstatus()!=null && filtro.getEstatus().length>0) {
				borrar= false;
				str.append(" estatus in (");
				StringBuilder in = new StringBuilder();
				for(Integer x:filtro.getEstatus()) {
					in.append(x);
					in.append(",");
		
				}
				str.append(in.toString().substring(0, in.toString().length()-1));
				str.append(" ) ");
			}
			
			if(borrar) {
				str.replace(str.lastIndexOf("and"), str.lastIndexOf("and")+3, "");
			}
			if((filtro.getRegion()!=null &&filtro.getRegion().length>0) || (filtro.getEstatus()!=null && filtro.getEstatus().length>0))  {
				str.append("LIMIT 20 OFFSET "+filtro.getPagina());
			}
			
	        return jdbcTemplate.queryForObject(str.toString(), Integer.class);
	    }


}
