package edu.indra.alumnos;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	//2 métodos
		//1 - DEFINO LOS USUARIOS Y ROLES
	
		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			//ejemplo de autenticación en memoria
			
			auth.inMemoryAuthentication().withUser("user1").password("{noop}user1")
			.roles("USER").and().withUser("admin").password("{noop}admin").roles("USER", "ADMIN");
			
			
			//os paso ejemplos para configuraciones con contraseñas encriptadas
			/*
			 * String pw1=new BCryptPasswordEncoder().encode("user1");
			 * System.out.println(pw1); auth.inMemoryAuthentication() .withUser("user1")
			 * .password("{bcrypt}"+pw1) //.password(pw1) .roles("USER") .and()
			 * .withUser("admin") .password(new BCryptPasswordEncoder().encode("admin"))
			 * .roles("USER", "ADMIN"); 
			 */
			
			//otra configuracion con usuarios en base de datos
			
			/*
			 * auth.jdbcAuthentication().dataSource(datasourceSecurity)
			 * .usersByUsernameQuery("select username, password, enabled" +
			 * " from users where username=?")
			 * .authoritiesByUsernameQuery("select username, authority " +
			 * "from authorities where username=?");
			 */
		}
		
		
		//2 - DEFINIR LAS RESTRICCIONES DE SEGURIDAD
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			//SÓLO LOS ADMIS PUEDEN BORRAR
			http.csrf().disable().authorizeRequests().antMatchers(HttpMethod.DELETE, "/alumno/*").hasRole("ADMIN").and().httpBasic();
		}

}
