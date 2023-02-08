package com.tjhelmuth.browser;

import com.intellij.ui.jcef.JBCefBrowser;
import com.tjhelmuth.ExplainWindow;
import lombok.RequiredArgsConstructor;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.callback.CefSchemeHandlerFactory;
import org.cef.handler.CefResourceHandler;
import org.cef.network.CefRequest;

@RequiredArgsConstructor
public class LocalSchemeHandlerFactory implements CefSchemeHandlerFactory {
    private final ExplainWindow window;

    @Override
    public CefResourceHandler create(CefBrowser cefBrowser, CefFrame cefFrame, String s, CefRequest cefRequest) {
        return new LocalResourceHandler(window);
    }
}
