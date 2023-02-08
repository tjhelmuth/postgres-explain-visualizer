package com.tjhelmuth.browser;

import com.tjhelmuth.ExplainWindow;
import com.tjhelmuth.browser.commands.LoadStatic;
import com.tjhelmuth.browser.commands.RerunPlan;
import lombok.extern.slf4j.Slf4j;
import org.cef.callback.CefCallback;
import org.cef.handler.CefResourceHandler;
import org.cef.misc.IntRef;
import org.cef.misc.StringRef;
import org.cef.network.CefRequest;
import org.cef.network.CefResponse;

import java.io.IOException;

@Slf4j
public class LocalResourceHandler implements CefResourceHandler {
    private final ExplainWindow window;
    private final RerunPlan refreshPlan;
    private final LoadStatic loadStatic = new LoadStatic();

    private ResourceHandlerState state = new ClosedConnection();

    public LocalResourceHandler(ExplainWindow window) {
        this.window = window;
        this.refreshPlan = new RerunPlan(window);
    }


    @Override
    public boolean processRequest(CefRequest cefRequest, CefCallback cefCallback) {
        log.info("Processing request: {}", cefRequest.getURL());

        try {
            boolean handled;
            if(refreshPlan.isApplicable(cefRequest)){
                var result = refreshPlan.execute(cefRequest);
                handled = result.isHandled();
            } else {
                var result = loadStatic.execute(cefRequest);
                this.state = result.isHandled() ? result.getResult() : new ClosedConnection();

                handled = result.isHandled();
            }

            if(handled){
                cefCallback.Continue();
                return true;
            } else {
                cefCallback.cancel();
                return false;
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
