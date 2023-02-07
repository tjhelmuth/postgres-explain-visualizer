package com.tjhelmuth.browser;

import com.intellij.ui.jcef.JBCefBrowser;
import com.tjhelmuth.browser.commands.LoadStatic;
import com.tjhelmuth.browser.commands.RefreshPlan;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cef.callback.CefCallback;
import org.cef.handler.CefResourceHandler;
import org.cef.misc.IntRef;
import org.cef.misc.StringRef;
import org.cef.network.CefRequest;
import org.cef.network.CefResponse;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class LocalResourceHandler implements CefResourceHandler {
    private final RefreshPlan refreshPlan;
    private final LoadStatic loadStatic = new LoadStatic();

    private ResourceHandlerState state = new ClosedConnection();

    public LocalResourceHandler(JBCefBrowser browser) {
        this.refreshPlan = new RefreshPlan(browser);
    }

    @Override
    public boolean processRequest(CefRequest cefRequest, CefCallback cefCallback) {
        log.warn("Processing request: {}", cefRequest.getURL());

        //TODO: refactor command handler to return whether continue or not...
        try {
            if(refreshPlan.isApplicable(cefRequest)){
                refreshPlan.execute(cefRequest);
                cefCallback.cancel();
                return false;
            }

            var nextState = loadStatic.execute(cefRequest);
            this.state = nextState == null ? new ClosedConnection() : nextState;
            if(nextState == null){
                cefCallback.cancel();
                return false;
            } else {
                cefCallback.Continue();
                return true;
            }

        } catch (IOException e){
            log.error("Error processing cef request", e);
            return false;
        }
    }

    @Override
    public void getResponseHeaders(CefResponse cefResponse, IntRef intRef, StringRef stringRef) {
        state.getResponseHeaders(cefResponse, intRef, stringRef);
    }

    @Override
    public boolean readResponse(byte[] bytes, int i, IntRef intRef, CefCallback cefCallback) {
        return state.readResponse(bytes, i, intRef, cefCallback);
    }

    @Override
    public void cancel() {
        state.close();
        state = new ClosedConnection();
    }

}
