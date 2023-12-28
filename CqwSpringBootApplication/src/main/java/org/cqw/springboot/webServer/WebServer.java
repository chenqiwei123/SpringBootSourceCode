package org.cqw.springboot.webServer;

import org.springframework.web.context.WebApplicationContext;

public interface WebServer {
    public void start(WebApplicationContext webApplicationContext);
}
