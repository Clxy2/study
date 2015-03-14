package cn.clxy.tools.webdriver.service.impl;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultRedirectHandler;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;

public class SkyRedirectHandler extends DefaultRedirectHandler {

    @Override
    public boolean isRedirectRequested(HttpResponse response, HttpContext context) {

        boolean result = super.isRedirectRequested(response, context);

        // For MS redirect by 302 on post.
        int statusCode = response.getStatusLine().getStatusCode();
        switch (statusCode) {
        case HttpStatus.SC_MOVED_TEMPORARILY:
            HttpRequest request = (HttpRequest) context.getAttribute(ExecutionContext.HTTP_REQUEST);
            String method = request.getRequestLine().getMethod();
            if (method.equalsIgnoreCase(HttpPost.METHOD_NAME)) {
                return true;
            }
        }

        return result;
    }

}
