package mx.com.proyecto.gui.exception;

public class PacGuiProcessException  extends Exception{

	private static final long serialVersionUID = 8498741078915740994L;


	private int code; 
	
	public PacGuiProcessException(){
		super();
	}
	
	public PacGuiProcessException(String msg){
		super(msg);
	}
	
	public PacGuiProcessException(int code, String msg){
		super(msg);
		this.code = code;
	}
	
	public PacGuiProcessException(Exception e){
		super(e);
	}
	
	public int getCode(){
		return code;
	}
	
	

}
