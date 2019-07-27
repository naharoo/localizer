package com.naharoo.localizer.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "localizer")
public class LocalizerProperties {

    private ApiProperties api;
    private SwaggerProperties swagger;

    public ApiProperties getApi() {
        return api;
    }

    public void setApi(ApiProperties api) {
        this.api = api;
    }

    public SwaggerProperties getSwagger() {
        return swagger;
    }

    public void setSwagger(SwaggerProperties swagger) {
        this.swagger = swagger;
    }

    public static class ApiProperties {

        private int port;
        private String contextPath;
        private String version;

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

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }

    public static class SwaggerProperties {

        private String title;
        private String description;
        private String baseControllerPackage;
        private String pathSelectorsRegex;
        private Contact contact;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getBaseControllerPackage() {
            return baseControllerPackage;
        }

        public void setBaseControllerPackage(String baseControllerPackage) {
            this.baseControllerPackage = baseControllerPackage;
        }

        public String getPathSelectorsRegex() {
            return pathSelectorsRegex;
        }

        public void setPathSelectorsRegex(String pathSelectorsRegex) {
            this.pathSelectorsRegex = pathSelectorsRegex;
        }

        public Contact getContact() {
            return contact;
        }

        public void setContact(Contact contact) {
            this.contact = contact;
        }

        public static class Contact {
            private String name;
            private String url;
            private String email;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }
        }
    }
}