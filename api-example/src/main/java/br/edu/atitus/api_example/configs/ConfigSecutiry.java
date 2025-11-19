package br.edu.atitus.api_example.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class ConfigSecutiry {

	@Bean
	SecurityFilterChain getSecurityFilter(HttpSecurity http) throws Exception{
		http
			// Habilita o CORS com a configuração definida abaixo
			.cors(cors -> cors.configurationSource(corsConfigurationSource()))
			
			// Desabilita CSRF (não necessário para API REST Stateless)
			.csrf(csrf -> csrf.disable())
			
			// Define a gestão de sessão como Stateless (sem cookies de sessão no servidor)
			.sessionManagement(session -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			
			.authorizeHttpRequests(auth -> auth
				// LIBERAÇÃO TOTAL para resolver problemas de CORS e Acesso
				// Permite o método OPTIONS (usado pelo browser antes do POST/GET)
				.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
				
				// Permite acesso a todas as rotas de autenticação
				.requestMatchers("/auth/**").permitAll()
				
				// Permite acesso total às rotas de pontos (/ws)
				// Isso é crucial para que o mapa funcione sem bloquear o POST
				.requestMatchers("/ws/**").permitAll()
				
				// Para garantir que nada mais bloqueie, liberamos tudo
				.anyRequest().permitAll());
		
		return http.build();
	}
	
	@Bean
	UrlBasedCorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		
		// Permite requisições de QUALQUER origem (Front-end no Railway, localhost, etc)
		configuration.addAllowedOriginPattern("*");
		
		// Permite todos os métodos (GET, POST, PUT, DELETE, OPTIONS, PATCH)
		configuration.addAllowedMethod("*");
		
		// Permite todos os cabeçalhos (Authorization, Content-Type, etc)
		configuration.addAllowedHeader("*");
		
		// Permite o envio de credenciais/cookies (importante para alguns navegadores)
		configuration.setAllowCredentials(true);
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
	
	@Bean
	PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
