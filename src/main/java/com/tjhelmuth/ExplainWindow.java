package com.tjhelmuth;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Disposer;
import com.intellij.ui.jcef.JBCefBrowser;
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

    private JBCefBrowser browser;

    public ExplainWindow(Project project, String plan) {
        this.project = project;
        this.plan = plan
                .replaceAll("[\r\n]+", " ")
                .replace("\"", "\\\"")
                .replace("'", "\\'");


        this.browser = new JBCefBrowser();
        registerAppSchemeHandler();
        browser.loadURL("http://myapp/index.html");

        Color editorBackground = UIManager.getColor("EditorPane.background");
        Color editorForeground = UIManager.getColor("EditorPane.foreground");

        Color panelForeground = UIManager.getColor("Panel.foreground");
        Color panelBackground = UIManager.getColor("Panel.background");

        Color textColor = UIManager.getColor("Tree.textForeground");

        browser.getJBCefClient().addLoadHandler(new CefLoadHandlerAdapter() {
            @Override
            public void onLoadEnd(CefBrowser cefBrowser, CefFrame cefFrame, int i) {
                String code = String.format("window.QUERY_PLAN = '%s'; if(window.setPlan) window.setPlan(window.QUERY_PLAN);", ExplainWindow.this.plan);

                log.info(code);

                String cssCode = CustomStyles.INSTANCE.generateCssBlock()
                        .replaceAll("[\r\n]+", " ")
                        .replace("\"", "\\\"")
                        .replace("'", "\\'");

                System.out.println(cssCode);

                String injectCss = String.format("document.head.insertAdjacentHTML( 'beforeend', \"%s\" );", cssCode);
                System.out.println(injectCss);
                cefBrowser.executeJavaScript(injectCss, browser.getCefBrowser().getURL(), 0);

                System.out.println("EDITOR BG: " + editorBackground);
                System.out.println("EDITOR FG: " + editorForeground);

                System.out.println("PANEL BG: " + panelBackground);
                System.out.println("PANEL FG: " + panelForeground);

                System.out.println(textColor);

                cefBrowser.executeJavaScript(code, browser.getCefBrowser().getURL(), 0);
            }
        }, browser.getCefBrowser());

        Disposer.register(project, browser);
    }

    public JComponent getContent(){
        return browser.getComponent();
    }

    private void registerAppSchemeHandler() {
        CefApp.getInstance()
                .registerSchemeHandlerFactory("http", "myapp", new LocalSchemeHandlerFactory());
    }


}
