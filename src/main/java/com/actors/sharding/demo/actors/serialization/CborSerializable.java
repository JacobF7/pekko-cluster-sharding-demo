package com.actors.sharding.demo.actors.serialization;

/**
 * Marker trait for serialization with Jackson CBOR. Enabled in serialization.conf
 * `pekko.actor.serialization-bindings` (via application.conf).
 *
 * @author jacob.falzon
 */
public interface CborSerializable {}
