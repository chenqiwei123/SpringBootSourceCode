package com.runwsh;

import org.cqw.springboot.CqwSpringApplication;
import org.cqw.springboot.CqwSpringBootApplication;
import org.cqw.springboot.config.WebServerAutoConfiguration;
import org.cqw.springboot.webServer.TomcatWebServerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@CqwSpringBootApplication

public class UserCodeApplication {
    public static void main(String[] args) {
        CqwSpringApplication.run(UserCodeApplication.class);
    }
}
