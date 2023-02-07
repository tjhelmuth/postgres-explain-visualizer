package com.tjhelmuth.browser.commands;

import com.tjhelmuth.browser.OpenedConnection;
import com.tjhelmuth.browser.ResourceHandlerState;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.cef.network.CefRequest;

import java.io.IOException;
import java.net.URL;

/**
 * Load a static resource from our resources/webview directory
 *
 */
@Slf4j
public class LoadStatic implements SimpleCommandHandler<ResourceHandlerState> {

    /**
     * Load a static resource from our resources/webview directory
     * @param request - the request from embedded browser
     * @return - resource handler state for this resource
     */
    @Override
    public ResourceHandlerState execute(CefRequest request) throws IOException {
        String requestUrl = request.getURL();

        Validate.notBlank(requestUrl, "Request URL cannot be blank");

        String pathToResource = requestUrl.replace("http://myapp", "webview/");
        URL newUrl = getClass().getClassLoader().getResource(pathToResource);

        log.warn("Loading static resource: {}", pathToResource);

        if(newUrl == null){
            log.warn("Unable to find resource for URL: {}", requestUrl);
            return null;
        }

        log.warn("Loaded: {}", newUrl.getPath());

        return new OpenedConnection(newUrl.openConnection());
    }
}
