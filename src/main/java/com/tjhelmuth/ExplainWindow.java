package com.tjhelmuth;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Disposer;
import com.intellij.ui.jcef.JBCefBrowser;
import com.intellij.ui.jcef.JBCefJSQuery;
import com.tjhelmuth.browser.LocalSchemeHandlerFactory;
import org.cef.CefApp;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.handler.CefLoadHandlerAdapter;

import javax.swing.*;
import java.awt.*;

public class ExplainWindow {
    private final Logger log = Logger.getInstance(ExplainWindow.class);
    private final Project project;
    private final String plan;

    private final boolean executed;

    private JBCefBrowser browser;

    public ExplainWindow(Project project, String plan, boolean executed) {
        this.executed = executed;
        this.project = project;
        this.plan = plan
                .replaceAll("[\r\n]+", " ")
                .replace("\"", "\\\"")
                .replace("'", "\\'");


        this.browser = new JBCefBrowser();
        registerAppSchemeHandler();
        browser.loadURL("http://myapp/index.html");

        browser.getJBCefClient().addLoadHandler(new CefLoadHandlerAdapter() {
            @Override
            public void onLoadEnd(CefBrowser cefBrowser, CefFrame cefFrame, int i) {
                String code = String.format("window.QUERY_PLAN = '%s'; if(window.setPlan) window.setPlan(window.QUERY_PLAN);", ExplainWindow.this.plan);
                String s = "";
                log.info(code);

                String cssCode = CustomStyles.INSTANCE.generateCssBlock()
                        .replaceAll("[\r\n]+", " ")
                        .replace("\"", "\\\"")
                        .replace("'", "\\'");

                System.out.println(cssCode);

                String injectCss = String.format("document.head.insertAdjacentHTML( 'beforeend', \"%s\" );", cssCode);
                System.out.println(injectCss);
                cefBrowser.executeJavaScript(injectCss, browser.getCefBrowser().getURL(), 0);
                cefBrowser.executeJavaScript(code, browser.getCefBrowser().getURL(), 0);

                selectAppropriateMetric(cefBrowser);
            }
        }, browser.getCefBrowser());

        Disposer.register(project, browser);
    }

    private void selectAppropriateMetric(CefBrowser browser){
        StringBuilder query = new StringBuilder();
        if(executed){
            query.append("Array.from(document.querySelectorAll('button'))\n" +
                    "  .filter(el => el.textContent === 'duration').forEach(btn => btn.click());\n");
            query.append("Array.from(document.querySelectorAll('button'))\n" +
                    "  .filter(el => el.textContent === 'time').forEach(btn => btn.click());\n");
        } else {
            query.append("Array.from(document.querySelectorAll('button'))\n" +
                    "  .filter(el => el.textContent === 'cost').forEach(btn => btn.click());");
        }

        log.info(query.toString());

        browser.executeJavaScript(query.toString(), "about:blank", 0);
    }

    public JComponent getContent(){
        return browser.getComponent();
    }

    private void registerAppSchemeHandler() {
        CefApp.getInstance()
                .registerSchemeHandlerFactory("http", "myapp", new LocalSchemeHandlerFactory());
    }


}
