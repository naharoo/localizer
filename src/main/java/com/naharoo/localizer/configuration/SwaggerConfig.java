package com.naharoo.localizer.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

    private final LocalizerProperties localizerProperties;

    public SwaggerConfig(final LocalizerProperties localizerProperties) {
        this.localizerProperties = localizerProperties;
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiEndPointsInfo())
            .useDefaultResponseMessages(false)
            .genericModelSubstitutes(ResponseEntity.class)
            .select()
            .apis(RequestHandlerSelectors
                .basePackage(localizerProperties.getSwagger().getBaseControllerPackage()))
            .paths(PathSelectors.regex(localizerProperties.getSwagger().getPathSelectorsRegex()))
            .build();
    }

    private ApiInfo apiEndPointsInfo() {
        return new ApiInfoBuilder().title(localizerProperties.getSwagger().getTitle())
            .description(localizerProperties.getSwagger().getDescription())
            .contact(new Contact(
                localizerProperties.getSwagger().getContact().getName(),
                localizerProperties.getSwagger().getContact().getUrl(),
                localizerProperties.getSwagger().getContact().getEmail()
            ))
            .version(localizerProperties.getApi().getVersion())
            .build();
    }
}
