package com.example.configuration;

import java.util.Arrays;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.models.User;
import com.example.security.ShaPasswordEncoder;
// import com.example.security.jwt.AuthTokenFilter;
import com.example.security.service.UserDetailsImpl;
import com.example.security.service.UserDetailsServiceImpl;

@TestConfiguration
public class WebSecurityTestConfig {

    private InMemoryUserDetailsManager userDetailsManager;

	// @Bean
	// public AuthTokenFilter authenticationJwtTokenFilter() {
	// 	return new AuthTokenFilter();
	// }

    @Bean
    @Primary
    public UserDetailsServiceImpl userDetailsService() {
        String pwd = passwordEncoder().encode("password");
        
        User basicUser = new User("user@company.com", "test", "test", pwd);
        User adminUser = new User("admin@company.com", "test", "test", pwd);
        User devUser = new User("dev@company.com", "test", "test", pwd);
        
        basicUser.id = 1L;
        basicUser.role = "user"; 
        adminUser.id = 2L;
        adminUser.role = "admin"; 
        devUser.id = 3L;
        devUser.role = "developer"; 

        userDetailsManager = new InMemoryUserDetailsManager(Arrays.asList(
            UserDetailsImpl.build(basicUser), 
            UserDetailsImpl.build(adminUser), 
            UserDetailsImpl.build(devUser)
        ));

        return new UserDetailsServiceImpl() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return UserDetailsImpl.build(
                    (org.springframework.security.core.userdetails.User)userDetailsManager.loadUserByUsername(username)
                );
            }
        };
    }

    @Bean
    @Primary
    public PasswordEncoder passwordEncoder() {
        return new ShaPasswordEncoder();
    }

    @Bean
    @Primary
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		 
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
	 
		return authProvider;
	}

	// @Bean
	// public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    //     http.csrf()
    //         .disable()
    //         .authorizeRequests()
    //         .anyRequest()
    //         .permitAll();

    //     http.authenticationProvider(authenticationProvider());
        
    //     http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

    //     return http.build();
    // }
}