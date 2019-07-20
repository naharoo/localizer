package com.naharoo.localizer.api.web.configuration;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebServerConfig implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

    private final ApiConfigurationProperties apiConfigurationProperties;

    public WebServerConfig(final ApiConfigurationProperties apiConfigurationProperties) {
        this.apiConfigurationProperties = apiConfigurationProperties;
    }

    @Override
    public void customize(final TomcatServletWebServerFactory factory) {
        factory.setPort(apiConfigurationProperties.getPort());
        factory.setContextPath("/" + apiConfigurationProperties.getContextPath());
    }
}
