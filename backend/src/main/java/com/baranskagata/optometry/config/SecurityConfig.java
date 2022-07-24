package com.baranskagata.optometry.config;

import com.baranskagata.optometry.filter.CustomAuthenticationFilter;
import com.baranskagata.optometry.filter.CustomAuthorizationFilter;
import com.baranskagata.optometry.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        //tells spring how to find users
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        CustomAuthenticationFilter authenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        authenticationFilter.setFilterProcessesUrl("/login");

        http.csrf().disable().sessionManagement().sessionCreationPolicy(STATELESS).and()
                .addFilter(authenticationFilter)
                .addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .cors().configurationSource(corsConfigurationSource())
                .and().authorizeRequests().antMatchers("/login", "/token/refresh/**").permitAll()
                .and().authorizeRequests().antMatchers("/patients/**").hasAnyAuthority("ADMIN")
                .and().authorizeRequests().antMatchers(HttpMethod.POST,"/users").permitAll()// for user registration
                .and().authorizeRequests().antMatchers(HttpMethod.POST,"/users/token/refresh").permitAll()// for token refreshing
                .and().authorizeRequests().antMatchers("/users/**").hasAnyAuthority("ADMIN")
                .and().authorizeRequests().antMatchers("/appointments/**").permitAll()
                .and().authorizeRequests().anyRequest().authenticated();

    }

    @Bean
    public UserDetailsService userDetailsService(){
        return new UserServiceImpl();
    }




    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET", "HEAD", "POST", "PUT", "DELETE", "TRACE", "OPTIONS", "PATCH"));
        configuration.setAllowCredentials(true);

        //headers that are allowed for browsers to access
        configuration.setExposedHeaders(Collections.singletonList("*"));//"Authorization"
        //used in response to a preflight request to indicate which HTTP headers can be used when making the actual request
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}





