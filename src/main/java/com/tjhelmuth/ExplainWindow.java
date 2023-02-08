package com.tjhelmuth;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.Disposer;
import com.intellij.ui.jcef.JBCefBrowser;
import com.tjhelmuth.browser.LocalSchemeHandlerFactory;
import lombok.Getter;
import org.cef.CefApp;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.handler.CefLoadHandlerAdapter;

import javax.swing.*;

public class ExplainWindow {
    private final Logger log = Logger.getInstance(ExplainWindow.class);

    @Getter
    private final ExplainContext context;

    @Getter
    private JBCefBrowser browser;

    private String plan;


    /**
     * The actual window that contains the rendered query plan
     *
     * //TODO: cleanup this code 😩😩😩😩
     */
    public ExplainWindow(String plan, ExplainContext context) {
        this.context = context;
        this.setPlan(plan);

        this.browser = new JBCefBrowser();
        registerAppSchemeHandler();
        browser.loadURL("http://myapp/index.html");

        browser.getJBCefClient().addLoadHandler(new CefLoadHandlerAdapter() {
            @Override
            public void onLoadEnd(CefBrowser cefBrowser, CefFrame cefFrame, int i) {
                String code = String.format("window.QUERY_PLAN = '%s'; if(window.setPlan) window.setPlan(window.QUERY_PLAN);", ExplainWindow.this.plan);
                log.info(code);

//                String cssCode = CustomStyles.INSTANCE.generateCssBlock()
//                        .replaceAll("[\r\n]+", " ")
//                        .replace("\"", "\\\"")
//                        .replace("'", "\\'");

//                System.out.println(cssCode);

//                String injectCss = String.format("document.head.insertAdjacentHTML( 'beforeend', \"%s\" );", cssCode);
//                System.out.println(injectCss);
//                cefBrowser.executeJavaScript(injectCss, browser.getCefBrowser().getURL(), 0);

                runJavascript(code);

                selectAppropriateMetric(cefBrowser);
            }
        }, browser.getCefBrowser());

        Disposer.register(context.getEditor(), browser);
    }

    public void updatePlan(String plan){
        log.debug("Updating plan");
        this.setPlan(plan);

        String code = String.format("window.QUERY_PLAN = '%s'; if(window.setPlan) window.setPlan(window.QUERY_PLAN); "
                + "window.setLoading(false);", ExplainWindow.this.plan);
        runJavascript(code);
    }


    private void selectAppropriateMetric(CefBrowser browser){
        StringBuilder query = new StringBuilder();
        if(context.didExecute()){
            query.append("Array.from(document.querySelectorAll('button'))\n" +
                    "  .filter(el => el.textContent === 'duration').forEach(btn => btn.click());\n");
            query.append("Array.from(document.querySelectorAll('button'))\n" +
                    "  .filter(el => el.textContent === 'time').forEach(btn => btn.click());\n");
        } else {
            query.append("Array.from(document.querySelectorAll('button'))\n" +
                    "  .filter(el => el.textContent === 'cost').forEach(btn => btn.click());");
        }

        log.info(query.toString());

        runJavascript(query.toString());
    }

    private void runJavascript(String code){
        browser.getCefBrowser().executeJavaScript(code, null, 0);
    }

    public JComponent getContent(){
        return browser.getComponent();
    }

    private void setPlan(String plan){
        this.plan = plan
                .replaceAll("[\r\n]+", " ")
                .replace("\"", "\\\"")
                .replace("'", "\\'");
    }

    private void registerAppSchemeHandler() {
        log.info("Registering scheme handler monkaW");

        CefApp.getInstance()
                .registerSchemeHandlerFactory("http", "myapp", new LocalSchemeHandlerFactory(this));
    }
}