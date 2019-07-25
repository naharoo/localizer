package com.naharoo.localizer.configuration;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebServerConfig implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

    private final LocalizerProperties localizerProperties;

    public WebServerConfig(final LocalizerProperties localizerProperties) {
        this.localizerProperties = localizerProperties;
    }

    @Override
    public void customize(final TomcatServletWebServerFactory factory) {
        factory.setPort(localizerProperties.getApi().getPort());
        factory.setContextPath("/" + localizerProperties.getApi().getContextPath());
    }
}