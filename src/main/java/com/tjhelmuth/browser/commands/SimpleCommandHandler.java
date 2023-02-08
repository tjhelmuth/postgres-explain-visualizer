package com.tjhelmuth.browser.commands;

import org.cef.network.CefRequest;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public interface SimpleCommandHandler<T> {
    @NotNull CommandResult<T> execute(CefRequest request) throws IOException;

    default boolean isApplicable(CefRequest request){
        return true;
    }
}
