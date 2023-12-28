package org.cqw.springboot;

import org.apache.catalina.*;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardEngine;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.startup.Tomcat;
import org.cqw.springboot.webServer.WebServer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.util.Map;

public class CqwSpringApplication {
    public static void run(Class clazz) {
        // 创建spring容器
        AnnotationConfigWebApplicationContext annotationConfigApplicationContextcontext = new AnnotationConfigWebApplicationContext();
        annotationConfigApplicationContextcontext.register(clazz);
        annotationConfigApplicationContextcontext.refresh();
        // 启动tomcat
//        startTomcat(clazz, annotationConfigApplicationContextcontext);
        // 启动web服务器
        WebServer webServer = getWebServer(annotationConfigApplicationContextcontext);
        webServer.start(annotationConfigApplicationContextcontext);
    }

    /**
     * 获取web启动类
     *
     * @param webApplicationContext
     * @return
     */
    private static WebServer getWebServer(WebApplicationContext webApplicationContext) {
        Map<String, WebServer> beansOfType = webApplicationContext.getBeansOfType(WebServer.class);
        if (beansOfType.isEmpty()) {
            throw new NullPointerException("您需要设置一个web启动类");
        }
        if (beansOfType.size() > 1) {
            throw new NullPointerException("您设置的web启动类有多个");
        }
        // 返回唯一一个
        return beansOfType.values().iterator().next();
    }

    private static void startTomcat(Class clazz, WebApplicationContext webApplicationContext) {
        // 创建 Tomcat 服务器实例
        Tomcat tomcat = new Tomcat();
        Server server = tomcat.getServer();
        Service service = server.findService("Tomcat");
        Connector connector = new Connector();
        // 设置 Tomcat 服务器的端口号
        connector.setPort(8080);
        Engine engine = new StandardEngine();
        engine.setDefaultHost("localhost");

        Host host = new StandardHost();
        host.setName("localhost");

        String contextPath = "";
        Context context = new StandardContext();
        context.setPath(contextPath);
        context.addLifecycleListener(new Tomcat.FixContextListener());

        host.addChild(context);
        engine.addChild(host);

        service.setContainer(engine);
        service.addConnector(connector);

        tomcat.addServlet(context, "dispatcher", new DispatcherServlet(webApplicationContext));
        context.addServletMappingDecoded("/*", "dispatcher");

        // 启动 Tomcat 服务器
        try {
            tomcat.start();
        } catch (LifecycleException e) {
            throw new RuntimeException(e);
        }

        // 等待 Tomcat 服务器停止
        tomcat.getServer().await();
    }
}
