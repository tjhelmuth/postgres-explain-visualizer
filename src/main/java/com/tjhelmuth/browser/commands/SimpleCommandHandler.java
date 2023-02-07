package com.tjhelmuth.browser.commands;

import org.cef.network.CefRequest;

import java.io.IOException;

public interface SimpleCommandHandler<T> {
    T execute(CefRequest request) throws IOException;

    default boolean isApplicable(CefRequest request){
        return true;
    }
}
