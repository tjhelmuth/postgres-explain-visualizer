package com.tjhelmuth.browser;

import org.cef.callback.CefCallback;
import org.cef.handler.CefLoadHandler;
import org.cef.misc.IntRef;
import org.cef.misc.StringRef;
import org.cef.network.CefResponse;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

public class OpenedConnection implements ResourceHandlerState {
    private final URLConnection connection;
    private final InputStream inputStream;

    public OpenedConnection(URLConnection connection) {
        this.connection = connection;

        try {
            inputStream = connection.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void getResponseHeaders(CefResponse response, IntRef responseLength, StringRef redirectUrl) {
        try {
            String url = connection.getURL().toString();
            String mimeType = connection.getContentType();
            if(url.contains(".css")) mimeType = "text/css";
            if(url.contains(".js")) mimeType = "text/javascript";
            if(url.contains(".html")) mimeType = "text/html";

            response.setMimeType(mimeType);
            responseLength.set(inputStream.available());
            response.setStatus(200);
        } catch (IOException e){
            response.setError(CefLoadHandler.ErrorCode.ERR_FILE_NOT_FOUND);
            response.setStatusText(e.getLocalizedMessage());
            response.setStatus(404);
        }
    }

    @Override
    public boolean readResponse(byte[] dataOut, int designedBytesToRead, IntRef totalRead, CefCallback callback) {
        try {
            int availableSize = inputStream.available();
            if (availableSize <= 0) {
                this.close();
                return false;
            }

            int maxBytesToRead = Math.min(availableSize, designedBytesToRead);
            int bytesRead = inputStream.read(dataOut, 0, maxBytesToRead);
            totalRead.set(bytesRead);
            return true;

        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void close() {
        try {
            inputStream.close();
        } catch (IOException e) {
            //ignored
        }
    }
}
