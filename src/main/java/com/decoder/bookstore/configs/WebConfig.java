package com.decoder.bookstore.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ApiVersionConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /* De longe a parada mais interessante que ela mostrou ate agora, e que, é novidade desde a
    * aula de 2022... */
    @Override
    public void configureApiVersioning(ApiVersionConfigurer configurer) {
        configurer.useRequestHeader("X-API-VERSION");
        configurer.addSupportedVersions("v1","v2");
        configurer.setDefaultVersion("v1");
    }
}
