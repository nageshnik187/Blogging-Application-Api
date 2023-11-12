package com.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.blog.security.CustomUserDetailService;
import com.blog.security.JwtAuthenticationEntryPoint;
import com.blog.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	
	public static final String[] PUBLIC_URLS = {
			"/v3/api-docs",
			"/v2/api-docs",
			"/swagger-resources/**",
			"/swagger-ui/**",
			"/webjars/**",
			"/api/auth/**",
			};
	
	@Autowired
	private CustomUserDetailService customUserDetailService;
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	private JwtAuthenticationFilter jAuthenticationFilter;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf()
            .disable()
            .authorizeHttpRequests()
            .requestMatchers(PUBLIC_URLS).permitAll()
            .requestMatchers(HttpMethod.GET).permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .exceptionHandling().authenticationEntryPoint(this.jwtAuthenticationEntryPoint)
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(this.jAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		http.authenticationProvider(daoAuthenticationProvider());
		
		DefaultSecurityFilterChain build = http.build();
		return build;
	}
	
	
	// Define a password encoder (e.g., BCryptPasswordEncoder)
    // You can also use NoOpPasswordEncoder for simplicity, but it's not recommended for production.
	@Bean
	 public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
    }
	

	@Bean
	public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
	
	 
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(this.customUserDetailService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
		
	}	 
	
}

