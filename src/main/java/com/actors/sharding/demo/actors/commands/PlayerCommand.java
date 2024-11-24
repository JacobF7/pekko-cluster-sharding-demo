package com.actors.sharding.demo.actors.commands;

import java.time.Duration;

import com.actors.sharding.demo.actors.serialization.CborSerializable;

import org.apache.pekko.actor.typed.ActorRef;
import org.apache.pekko.pattern.StatusReply;

/**
 * Definition of a player command
 *
 * @author jacob.falzon
 */
public interface PlayerCommand extends CborSerializable {
    
    Duration DEFAULT_TIMEOUT = Duration.ofSeconds(1L);
    
    ActorRef<? extends StatusReply<?>> getReplyTo();
    
}
