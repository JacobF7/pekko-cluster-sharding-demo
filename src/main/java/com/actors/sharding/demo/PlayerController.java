package com.actors.sharding.demo;

import static com.actors.sharding.demo.actors.commands.PlayerCommand.DEFAULT_TIMEOUT;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.actors.sharding.demo.actors.PlayerActorRefResolver;
import com.actors.sharding.demo.actors.commands.GetPlayerCommand;
import com.actors.sharding.demo.actors.commands.PlayerCommand;
import com.actors.sharding.demo.actors.replies.PlayerReply;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@link RestController} for player operations.
 *
 * @author jacob.falzon
 */
@RestController
@RequestMapping("players")
@RequiredArgsConstructor
@Slf4j
public class PlayerController {
    
    private final PlayerActorRefResolver playerActorRefResolver;
    
    @GetMapping("{id}")
    public PlayerReply getPlayer(@PathVariable("id") final int id) {
        log.info("Received getPlayer {}", id);
        return playerActorRefResolver.getPlayerActorRef(id)
            .<PlayerReply>askWithStatus(replyTo -> GetPlayerCommand.builder().replyTo(replyTo).build(), DEFAULT_TIMEOUT)
            .toCompletableFuture()
            .join();
    }
    
    @PostMapping("{id}")
    public void adjustBalance(@PathVariable("id") final int id,
                              @RequestBody final PlayerBalanceAdjustDto request) {
        log.info("{} , {}", id, request);
    }
    
    @Data
    @NoArgsConstructor
    public static class PlayerBalanceAdjustDto {
        private int amount;
    }
}
