package org.cqw.springboot.webServer;

import org.springframework.web.context.WebApplicationContext;

public class JettyWebServerImpl implements WebServer{
    @Override
    public void start(WebApplicationContext webApplicationContext) {
        System.out.println("Jetty WebServer start");
    }
}
