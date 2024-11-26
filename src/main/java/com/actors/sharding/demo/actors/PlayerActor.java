package com.actors.sharding.demo.actors;


import static org.apache.pekko.pattern.StatusReply.error;
import static org.apache.pekko.pattern.StatusReply.success;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

import com.actors.sharding.demo.actors.commands.GetPlayerCommand;
import com.actors.sharding.demo.actors.commands.PlayerCommand;
import com.actors.sharding.demo.actors.replies.PlayerReply;
import com.actors.sharding.demo.actors.state.PlayerState;

import org.apache.pekko.actor.typed.ActorSystem;
import org.apache.pekko.actor.typed.Behavior;
import org.apache.pekko.actor.typed.javadsl.AbstractBehavior;
import org.apache.pekko.actor.typed.javadsl.ActorContext;
import org.apache.pekko.actor.typed.javadsl.Behaviors;
import org.apache.pekko.actor.typed.javadsl.Receive;
import org.apache.pekko.cluster.sharding.typed.javadsl.ClusterSharding;
import org.apache.pekko.cluster.sharding.typed.javadsl.Entity;
import org.apache.pekko.cluster.sharding.typed.javadsl.EntityTypeKey;

/**
 * The player actor.
 *
 * @author jacob.falzon
 */
@Slf4j
public class PlayerActor extends AbstractBehavior<PlayerCommand> {
    
    public static final EntityTypeKey<PlayerCommand> PLAYER_ENTITY_KEY = EntityTypeKey.create(PlayerCommand.class, "PLAYER");
    
    @Getter
    private final String entityId;
    
    private final PlayerState state;
    
    private PlayerActor(final ActorContext<PlayerCommand> context,
                        final String entityId) {
        super(context);
        
        this.entityId = entityId;
        this.state = PlayerState.builder().id(entityId).balance(0L).build();
    }
    
    // define how actor with PLAYER_ENTITY_KEY is created in cluster
    public static void init(final ActorSystem<?> system) {
        ClusterSharding.get(system)
            .init(Entity.of(PLAYER_ENTITY_KEY, entityContext -> create(entityContext.getEntityId())));
    }
    
    private static Behavior<PlayerCommand> create(final String entityId) {
        return Behaviors.setup(ctx ->  new PlayerActor(ctx, entityId));
    }
    
    @Override
    public Receive<PlayerCommand> createReceive() {
        return newReceiveBuilder()
            .onMessage(GetPlayerCommand.class, this::onGetPlayerCommand)
            .build();
    }
    
    private Behavior<PlayerCommand> onGetPlayerCommand(final GetPlayerCommand cmd) {
        log.info("Player [{}]: Received Command {}", entityId, cmd.getClass().getSimpleName());
        final var reply =
            PlayerReply.builder().id(state.getId()).balance(state.getBalance()).timestamp(LocalDateTime.now()).build();
        cmd.getReplyTo().tell(success(reply));
        return Behaviors.same();
    }
}
