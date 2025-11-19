package br.edu.atitus.api_example.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
			// Configura o CORS para usar a nossa fonte definida abaixo
			.cors(cors -> cors.configurationSource(corsConfigurationSource()))
			// Desabilita CSRF (não necessário para API Stateless)
			.csrf(csrf -> csrf.disable())
			.sessionManagement(session -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(auth -> auth
				// LIBERAÇÃO EXPLÍCITA DE OPTIONS (Preflight do CORS)
				.requestMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**").permitAll()
				// Rotas protegidas
				.requestMatchers("/ws**", "/ws/**").authenticated()
				// Todo o resto é público (login, cadastro, etc)
				.anyRequest().permitAll());
		
		return http.build();
	}
	
	@Bean
	UrlBasedCorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		
		// Configuração Permissiva para o Frontend
		configuration.addAllowedOriginPattern("*"); // Permite qualquer origem (seu site no Railway)
		configuration.addAllowedMethod("*");        // Permite GET, POST, PUT, DELETE, OPTIONS
		configuration.addAllowedHeader("*");        // Permite todos os cabeçalhos
		configuration.setAllowCredentials(true);    // Permite credenciais (Cookies/Auth) - IMPORTANTE
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
	
	@Bean
	PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
