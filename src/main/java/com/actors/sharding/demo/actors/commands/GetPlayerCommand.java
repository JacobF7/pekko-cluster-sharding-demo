package com.actors.sharding.demo.actors.commands;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import com.actors.sharding.demo.actors.replies.PlayerReply;
import com.fasterxml.jackson.annotation.JsonCreator;

import org.apache.pekko.actor.typed.ActorRef;
import org.apache.pekko.pattern.StatusReply;

/**
 * Command to fetch a player.
 *
 * @author jacob.falzon
 */
@Data
@Builder
@AllArgsConstructor(onConstructor_ = @JsonCreator)
public class GetPlayerCommand implements PlayerCommand {
    
    private ActorRef<StatusReply<PlayerReply>> replyTo;
    
}
