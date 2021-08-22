package org.prgrms.orderApp;

import org.prgrms.orderApp.CMDApplication.CMDApplication;
import org.prgrms.orderApp.CMDApplication.util.CheckInvalid;
import org.prgrms.orderApp.config.AppConfiguration;
import org.prgrms.orderApp.CMDApplication.service.BasicCMDService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CommandLineApplication {

    public static void main(String[] args) {
        new AnnotationConfigApplicationContext(AppConfiguration.class);
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        var cmdApplication = applicationContext.getBean(CMDApplication.class);
        // app start
        cmdApplication.CMDAppStart();
    }



}
