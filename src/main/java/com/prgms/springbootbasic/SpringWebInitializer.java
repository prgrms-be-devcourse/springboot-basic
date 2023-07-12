package com.prgms.springbootbasic;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class SpringWebInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        var applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.register(AppConfig.class);

        var dispatcherServlet = new DispatcherServlet(applicationContext);
        ServletRegistration.Dynamic spring = servletContext.addServlet("spring", dispatcherServlet);
        spring.addMapping("/");
    }

}
