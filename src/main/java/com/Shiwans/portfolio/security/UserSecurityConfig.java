
package com.Shiwans.portfolio.security;

import com.Shiwans.portfolio.throttling.ApiRateLimitFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;


@Configuration
@EnableWebSecurity
public class UserSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider auth =
                new DaoAuthenticationProvider(userDetailsService);

        auth.setPasswordEncoder(passwordEncoder);

        return auth;
    }

    @Bean
    public FilterRegistrationBean<ApiRateLimitFilter>
    rateLimitFilterRegistration(ApiRateLimitFilter filter) {

        FilterRegistrationBean<ApiRateLimitFilter> registration =
                new FilterRegistrationBean<>(filter);

        registration.setEnabled(false);

        return registration;
    }

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            ApiRateLimitFilter apiRateLimitFilter,
            DaoAuthenticationProvider authenticationProvider
    ) throws Exception {

        http
                .authenticationProvider(authenticationProvider)

                .addFilterBefore(
                        apiRateLimitFilter,
                        UsernamePasswordAuthenticationFilter.class
                )

                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                "/admin",
                                "/admin/",
                                "/admin/**"
                        ).hasRole("ADMIN")
                        .anyRequest().permitAll()
                )

                .httpBasic(Customizer.withDefaults())

                // Keep CSRF enabled for session-based Thymeleaf login
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/authenticateUser")
                        .successHandler(new AuthenticationHandler())
                        .failureHandler(new AuthenticationHandler())
                        .permitAll()
                )

                .logout(logout -> logout
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .logoutUrl("/admin/logout")
                        .logoutSuccessUrl("/login")
                        .permitAll()
                )

                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(new AdminAccessDeniedHandler())
                );

        return http.build();
    }
}
