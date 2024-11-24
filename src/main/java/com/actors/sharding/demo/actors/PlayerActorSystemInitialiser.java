package com.actors.sharding.demo.actors;

import lombok.RequiredArgsConstructor;

import jakarta.annotation.PostConstruct;

import org.apache.pekko.actor.typed.ActorSystem;
import org.springframework.stereotype.Component;

/**
 * This class is used to initialise the {@link PlayerActor}.
 *
 * @author jacob.falzon
 */
@Component
@RequiredArgsConstructor
public class PlayerActorSystemInitialiser {
    
    private final ActorSystem<?> actorSystem;
    
    @PostConstruct
    public void initMlmJackpotActor() {
        PlayerActor.init(actorSystem);
    }
}
