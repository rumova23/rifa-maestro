package mx.com.proyecto.gui.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import mx.com.proyecto.gui.service.ServidorPublicoService;
import mx.com.proyecto.servidor.model.ServidorPublico;

@Component
public class MyAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private ServidorPublicoService service;
	
	private static final Short VALIDADO=1; 

	@Override
	public Authentication authenticate(Authentication authentication) {
		final UsernamePasswordAuthenticationToken upAuth = (UsernamePasswordAuthenticationToken) authentication;

		final Object principal = authentication.getPrincipal();
		final String name = (String) principal;
		final String password = (String) upAuth.getCredentials();

		if (Objects.equals(name, "") || Objects.equals(password, "")) {
			
			throw new BadCredentialsException("Datos Incorrectos");
		}

		List<GrantedAuthority> grantedAuths = new ArrayList<>();

		if (Objects.equals(name, "admin") && Objects.equals(password, "secret123")) {
			grantedAuths.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

		} else {
			Long cve = validaLong(name.trim());
			if (cve==null) {
				throw new BadCredentialsException("Formato invalido para clave de servidor publico");
			}

			ServidorPublico s = service.obtenServidorPublicoByID(cve);

			if (s!=null && s.getEstatus().equals(VALIDADO)) {
				throw new BadCredentialsException("El Servidor ya tiene asignado un boleto");
			}
			s.setEmail(password);
			service.actualizaServidorPublico(s);
			grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
		}
		final UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(principal,	authentication.getCredentials(), grantedAuths);

		result.setDetails(authentication.getDetails());

		return result;
	}
	
	private Long validaLong(String numero) {
		try {
			return new Long(numero);
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
