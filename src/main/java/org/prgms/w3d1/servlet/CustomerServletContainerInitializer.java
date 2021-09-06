package org.prgms.w3d1.servlet;

import org.prgms.w3d1.configuration.AppConfiguration;
import org.prgms.w3d1.configuration.ServletConfiguration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.util.Set;

public class CustomerServletContainerInitializer implements ServletContainerInitializer {

    @Override
    public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
        var applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.register(ServletConfiguration.class);

        DispatcherServlet dispatcherServlet = new DispatcherServlet(applicationContext);
        ServletRegistration.Dynamic customerRegistration = ctx.addServlet("customer", dispatcherServlet);
        customerRegistration.addMapping("/");
        customerRegistration.setLoadOnStartup(1);
    }
}
