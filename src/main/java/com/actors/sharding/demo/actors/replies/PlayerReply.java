package com.actors.sharding.demo.actors.replies;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.actors.sharding.demo.actors.serialization.CborSerializable;
import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * @author jacob.falzon
 */
@Data
@Builder
@AllArgsConstructor(onConstructor_ = @JsonCreator)
public class PlayerReply implements CborSerializable {

    private String id;
    private String balance;
    private LocalDateTime timestamp;
    
}
