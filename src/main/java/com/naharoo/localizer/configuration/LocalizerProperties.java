package com.naharoo.localizer.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "localizer")
public class LocalizerProperties {

    private ApiProperties api;
    private SwaggerProperties swagger;

    @Data
    public static class ApiProperties {
        private int port;
        private String contextPath;
        private String version;
    }

    @Data
    public static class SwaggerProperties {
        private String title;
        private String description;
        private String baseControllerPackage;
        private String pathSelectorsRegex;
        private Contact contact;

        @Data
        public static class Contact {
            private String name;
            private String url;
            private String email;
        }
    }
}