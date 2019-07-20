package com.naharoo.localizer.core.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Profile("!test")
@Configuration
@PropertySource("classpath:com/naharoo/localizer/db.properties")
@EntityScan(basePackages = "com.naharoo.localizer.core.services")
@EnableJpaRepositories(basePackages = "com.naharoo.localizer.core.repositories")
public class DatabaseConfiguration {
}
