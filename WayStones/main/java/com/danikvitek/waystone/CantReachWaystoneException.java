package com.danikvitek.waystone;

/**
 * Thrown when a player can not open Waystone GUI but somehow tries to do this
 */
public class CantReachWaystoneException extends IllegalStateException {
    public CantReachWaystoneException(String message) {
        super(message);
    }
}
