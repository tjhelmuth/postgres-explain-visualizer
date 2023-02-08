package com.tjhelmuth.browser.commands;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value @AllArgsConstructor
public class CommandResult<T> {
    T result;
    boolean handled;

    public static <T> CommandResult<T> emptyHandled(){
        return new CommandResult<>(null, true);
    }

    public static <T> CommandResult<T> emptyNotHandled(){
        return new CommandResult<>(null, false);
    }
}
