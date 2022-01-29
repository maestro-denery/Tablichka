package com.danikvitek.waystones.misc.exceptions;

/**
 * Thrown when a player can not open Waystone GUI but somehow tries to do this
 */
public final class CantReachWaystoneException extends IllegalStateException {
    public CantReachWaystoneException(String message) {
        super(message);
    }
}
