package com.example.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.security.jwt.AuthEntryPointJwt;
import com.example.security.jwt.AuthTokenFilter;
import com.example.security.service.UserDetailsServiceImpl;

import java.util.Arrays;
import java.util.List;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableGlobalMethodSecurity(
		securedEnabled = true,
		prePostEnabled = true)
public class WebSecurityConfig {
	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;

	@Value("${app.security-cors-allowed-origin}")
	private String allowedOrigin;

	@Value("${graphql.graphiql.enabled:false}")
	private boolean graphiqlEnabled;

	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
	  return authConfiguration.getAuthenticationManager();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		 
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
	 
		return authProvider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new ShaPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
		String[] allowedOrigins = allowedOrigin.split(",");
        corsConfiguration.setAllowedOrigins(Arrays.asList(allowedOrigins));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PUT", "OPTIONS", "PATCH", "DELETE"));
        // corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setExposedHeaders(List.of("Authorization"));

		List<String> antPatternStrings = Arrays.asList(
			"/api/login", "/api/signup", "/api/refreshtoken", "/api/test/**", 
			// Deprecated AngularJS frontend
			"/", "/webjars/**", "/js/**", "/vehicle-list/**", "/vehicle-detail/**"
		);
		if (true == graphiqlEnabled) {
			antPatternStrings.add("/graphiql");
			antPatternStrings.add("/vendor/graphiql/**");
			System.out.println("=========================== WARNING: INSECURE! ==================================");
			System.out.println(antPatternStrings.toString());
			System.out.println("=================================================================================");
		}
		http.cors().configurationSource(request -> corsConfiguration).and()
			.csrf().disable()
			.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.authorizeRequests().antMatchers(antPatternStrings.toArray(String[]::new)).permitAll()
			.anyRequest().authenticated();

		http.authenticationProvider(authenticationProvider());
		
		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
	
}