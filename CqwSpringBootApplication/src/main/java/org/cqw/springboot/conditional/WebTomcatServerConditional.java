package org.cqw.springboot.conditional;

import org.apache.catalina.startup.Tomcat;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class WebTomcatServerConditional implements Condition{
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        // 存在tomcat就返回true
        try {
            // 加载Tomcat类，如果加载成功，则返回true
            conditionContext.getClassLoader().loadClass("org.apache.catalina.startup.Tomcat");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
