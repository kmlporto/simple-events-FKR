package br.edu.ifpb.pweb2.projeto.simpleeventFKR.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityWebConfig extends WebSecurityConfigurerAdapter {
//	https://o7planning.org/en/11705/create-a-login-application-with-spring-boot-spring-security-jpa
//	https://blog.algaworks.com/spring-security/
//	https://www.baeldung.com/spring-boot-security-autoconfiguration
//	https://stackabuse.com/password-encoding-with-spring-security/
	
	@Autowired
	private UserDetailsSimpleEvent usuarioDetalhes;
	
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	
		//paginas que nao precisam de login
		http.authorizeRequests()
									.antMatchers("/",
												"/usuarios/**",
												"/datafaker",
												"/logout").permitAll()
									.antMatchers("/especialidades/**").access("hasRole('ROLE_ADMIN')")
//									.and().formLogin()
////							        		.loginPage("/login")
//							        		.defaultSuccessUrl("/")
//							        		.failureUrl("/erro")
//							        		.permitAll()
									
									
									//necessario apenas para liberar o acesso ao banco de testes h2-console
									.and().authorizeRequests().antMatchers("/h2-console/**").permitAll()
									.and().csrf().ignoringAntMatchers("/h2-console/**")
									.and().headers().frameOptions().sameOrigin();
		
	super.configure(http);
	}
	

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	auth
		.userDetailsService(usuarioDetalhes)
		.passwordEncoder(new BCryptPasswordEncoder());
	}
	
}
