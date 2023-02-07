package com.tjhelmuth.browser;

import org.cef.callback.CefCallback;
import org.cef.misc.IntRef;
import org.cef.misc.StringRef;
import org.cef.network.CefResponse;

import java.io.IOException;

public interface ResourceHandlerState {
    void getResponseHeaders(CefResponse response, IntRef responseLength, StringRef redirectUrl);

    boolean readResponse(byte[] dataOut, int designedBytesToRead, IntRef bytesRead, CefCallback callback);

    void close();
}
