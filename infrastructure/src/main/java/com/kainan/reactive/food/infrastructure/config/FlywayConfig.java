package com.kainan.reactive.food.infrastructure.config;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class FlywayConfig {

    @Bean(initMethod = "migrate")
    public Flyway flyway(Environment env) {
        return new Flyway(
                Flyway.configure()
                        .baselineOnMigrate(Boolean.getBoolean(
                                env.getRequiredProperty("flyway.baseline-on-migrate"))
                        )
                        .schemas(env.getRequiredProperty("flyway.schemas"))
                        .dataSource(
                                env.getRequiredProperty("flyway.url"),
                                env.getRequiredProperty("flyway.username"),
                                env.getRequiredProperty("flyway.password")
                        )
        );
    }
}
