package com.tjhelmuth.browser.commands;

import com.intellij.ui.jcef.JBCefBrowser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cef.network.CefRequest;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class RefreshPlan implements SimpleCommandHandler<Void> {
    private final JBCefBrowser browser;

    @Override
    public Void execute(CefRequest request) throws IOException {
        log.warn("Refreshing plan");
        return null;
    }

    @Override
    public boolean isApplicable(CefRequest request) {
        return request.getURL().contains("myapp/refresh");
    }
}
