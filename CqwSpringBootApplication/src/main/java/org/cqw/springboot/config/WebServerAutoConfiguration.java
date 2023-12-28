package org.cqw.springboot.config;

import org.cqw.springboot.conditional.WebJettyServerConditional;
import org.cqw.springboot.conditional.WebTomcatServerConditional;
import org.cqw.springboot.webServer.JettyWebServerImpl;
import org.cqw.springboot.webServer.TomcatWebServerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebServerAutoConfiguration {
    /**
     * Tomcat
     * @return
     */
    @Bean
    @Conditional(WebTomcatServerConditional.class)
    public TomcatWebServerImpl tomcatWebServer() {
        return new TomcatWebServerImpl();
    }

    /**
     * Jetty
     * @return
     */
    @Bean
    @Conditional(WebJettyServerConditional.class)
    public JettyWebServerImpl jettyWebServer() {
        return new JettyWebServerImpl();
    }
}
