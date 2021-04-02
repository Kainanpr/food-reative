package com.kainan.reactive.food.infrastructure.config;

import lombok.AllArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@AllArgsConstructor
public class FlywayConfig {

    @Bean(initMethod = "migrate")
    public Flyway flyway(Environment env) {
        return new Flyway(
                Flyway.configure()
                        .baselineOnMigrate(Boolean.getBoolean(
                                env.getRequiredProperty("spring.flyway.baseline-on-migrate"))
                        )
                        .dataSource(
                                env.getRequiredProperty("spring.flyway.url"),
                                env.getRequiredProperty("spring.flyway.username"),
                                env.getRequiredProperty("spring.flyway.password")
                        )
        );
    }
}
