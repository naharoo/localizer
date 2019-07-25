package com.naharoo.localizer;

import com.naharoo.localizer.configuration.LocalizerProperties;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
@EnableConfigurationProperties(LocalizerProperties.class)
public class Localizer {

    public static void main(final String... args) {
        new SpringApplicationBuilder(Localizer.class)
            .bannerMode(Banner.Mode.OFF)
            .run(args);
    }
}