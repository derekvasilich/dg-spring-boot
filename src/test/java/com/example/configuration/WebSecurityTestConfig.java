package com.example.configuration;

import java.util.Arrays;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.example.models.User;
import com.example.security.ShaPasswordEncoder;
import com.example.security.service.UserDetailsImpl;
import com.example.security.service.UserDetailsServiceImpl;

@TestConfiguration
public class WebSecurityTestConfig {

    private InMemoryUserDetailsManager userDetailsManager;

    @Bean
    @Primary
    public UserDetailsServiceImpl userDetailsService() {
        User basicUser = new User("user@company.com", "test", "test", passwordEncoder().encode("password"));
        basicUser.role = "user"; 
        UserDetailsImpl basicUserImpl = UserDetailsImpl.build(basicUser);

        User adminUser = new User("admin@company.com", "test", "test", passwordEncoder().encode("password"));
        adminUser.role = "admin"; 
        UserDetailsImpl adminUserImpl = UserDetailsImpl.build(adminUser);

        userDetailsManager = new InMemoryUserDetailsManager(Arrays.asList(
            basicUserImpl, adminUserImpl
        ));

        return new UserDetailsServiceImpl() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userDetailsManager.loadUserByUsername(username);
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
}