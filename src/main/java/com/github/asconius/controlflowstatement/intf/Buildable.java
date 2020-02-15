package com.github.asconius.controlflowstatement.intf;

@FunctionalInterface
public interface Buildable<T> {
    T build();
}
