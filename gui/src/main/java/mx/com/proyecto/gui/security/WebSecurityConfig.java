package mx.com.proyecto.gui.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import mx.com.proyecto.gui.web.LoggingAccessDeniedHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	
	
	@Autowired
	private LoggingAccessDeniedHandler accessDeniedHandler;
	
	@Autowired
	private MyAuthenticationProvider authProvider;
	  
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
        http.authorizeRequests().antMatchers(
                    "/",
                    "/js/**",
                    "/css/**",
                    "/vendor/**",
                    "/webjars/**",
                    "/exito/**",
                    "/captura/**",
                    "/sinregcaptura/**",
                    "/sistemacerrado/**", 
                    "/recupera/**",
                    "/recupera/**",
                    "/proximamente/**",
                    "/porvalidar/**",
                    "/maestros/**",
                    "/capturasinreg/**",
                    "/img/**").permitAll()
            .antMatchers("/configura/**","/reporte/**","/descarga/**", "/actualizaservidor/**").hasRole("ADMIN")
            
            .anyRequest().authenticated()
            .and()
            .formLogin().loginPage("/login").permitAll()
                .and()
             .formLogin().loginPage("/home").permitAll()
             	.and()
            .logout()
	            .invalidateHttpSession(true)
	            .clearAuthentication(true)
	            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	            .logoutSuccessUrl("/home")
	            .permitAll()
	            .and()
	        .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler);
        
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider);
        //auth.inMemoryAuthentication().passwordEncoder(passwordEncoder()).withUser("admin").password(ENCODED).roles("ADMIN");
    }
 
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
   
}