package com.naharoo.localizer.api.web;

import com.naharoo.localizer.api.web.configuration.ApiConfigurationProperties;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ImportResource;

@EnableCaching
@SpringBootApplication
@EnableConfigurationProperties(ApiConfigurationProperties.class)
@ImportResource("/com/naharoo/localizer/localizer-api-web-context.xml")
public class LocalizerApi {

    public static void main(final String... args) {
        new SpringApplicationBuilder(LocalizerApi.class)
                .bannerMode(Banner.Mode.OFF)
                .run(args);
    }
}
