package com.actors.sharding.demo.actors;


import static com.actors.sharding.demo.actors.PlayerActor.PLAYER_ENTITY_KEY;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.actors.sharding.demo.actors.commands.PlayerCommand;


import org.apache.pekko.actor.typed.ActorSystem;
import org.apache.pekko.cluster.sharding.typed.javadsl.ClusterSharding;
import org.apache.pekko.cluster.sharding.typed.javadsl.EntityRef;
import org.springframework.stereotype.Component;

/**
 * This resolver is used to obtain the relevant Player Actor to submit a command to.
 *
 * @author jacob.falzon
 */
@Component
@RequiredArgsConstructor
public class PlayerActorRefResolver {
    
    private final ActorSystem<?> actorSystem;
    
    public EntityRef<PlayerCommand> getPlayerActorRef(final long entityId) {
        return ClusterSharding.get(actorSystem).entityRefFor(PLAYER_ENTITY_KEY, String.valueOf(entityId));
    }
    
}
