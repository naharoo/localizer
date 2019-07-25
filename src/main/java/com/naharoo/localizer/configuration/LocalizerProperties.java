package com.naharoo.localizer.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "localizer")
public class LocalizerProperties {

    private ApiProperties api;

    public ApiProperties getApi() {
        return api;
    }

    public void setApi(ApiProperties api) {
        this.api = api;
    }

    public static class ApiProperties {

        private int port;
        private String contextPath;

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public String getContextPath() {
            return contextPath;
        }

        public void setContextPath(String contextPath) {
            this.contextPath = contextPath;
        }
    }
}