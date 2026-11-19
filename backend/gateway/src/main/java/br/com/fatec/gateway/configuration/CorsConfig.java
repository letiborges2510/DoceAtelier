package br.com.fatec.gateway.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

/**
 * O front-end React chama o gateway de outra origem, então o navegador exige
 * CORS. Como o login usa cookie HttpOnly (access_token), é obrigatório
 * allowCredentials(true) + origens explícitas (nunca "*" com credentials).
 * As origens vêm de app.cors.origins (variável APP_CORS_ORIGINS no Render).
 */
@Configuration
public class CorsConfig {

    @Value("${app.cors.origins:http://localhost:5173,http://127.0.0.1:5173}")
    private List<String> origins;

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(origins);
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(-102);
        return bean;
    }
}
