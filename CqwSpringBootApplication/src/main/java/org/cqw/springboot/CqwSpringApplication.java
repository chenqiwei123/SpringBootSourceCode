package org.cqw.springboot;

import org.apache.catalina.*;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardEngine;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.startup.Tomcat;

import java.util.concurrent.locks.Condition;

public class CqwSpringApplication {
    public static void run(Class clazz) {
        // 启动tomcat
        startTomcat(clazz);
    }

    private static void startTomcat(Class clazz) {
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

        String contextPath = clazz.getPackage().getName().replace(".", "/");
        Context context = new StandardContext();
        context.setPath(contextPath);
        context.addLifecycleListener(new Tomcat.FixContextListener());
        host.addChild(context);
        engine.addChild(host);

        service.setContainer(engine);
        service.addConnector(connector);

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
