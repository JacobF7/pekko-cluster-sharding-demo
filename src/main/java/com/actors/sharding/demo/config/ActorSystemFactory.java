package com.actors.sharding.demo.config;

import lombok.extern.slf4j.Slf4j;

import com.typesafe.config.ConfigFactory;


import org.apache.pekko.actor.typed.ActorSystem;
import org.apache.pekko.actor.typed.javadsl.Behaviors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The configuration factory to start up the {@link ActorSystem}.
 *
 * @author jacob.falzon
 */
@Slf4j
@Configuration
public class ActorSystemFactory {
    
    private static final String APPLICATION_NAME = "demo";
    
    @Bean
    public ActorSystem<?> actorSystem() {
        final var system =
            ActorSystem.create(Behaviors.empty(), APPLICATION_NAME, ConfigFactory.load(APPLICATION_NAME));
        log.info("Cluster [{}] Initialisation Completed", APPLICATION_NAME);
        return system;
    }
}
