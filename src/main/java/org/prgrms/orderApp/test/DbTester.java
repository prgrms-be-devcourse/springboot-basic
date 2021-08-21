package org.prgrms.orderApp.test;

import org.prgrms.orderApp.CMDProgramForOrderApp.service.CMDProgramService;
import org.prgrms.orderApp.config.AppConfiguration;
import org.prgrms.orderApp.config.MonguDbConfiguragtion;
import org.prgrms.orderApp.monguDb.service.DbManagement;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class DbTester {
    public static void main(String[] args) throws IOException {
        new AnnotationConfigApplicationContext(MonguDbConfiguragtion.class);
        var monguContext = new AnnotationConfigApplicationContext(MonguDbConfiguragtion.class);
        var dbManagement = monguContext.getBean(DbManagement.class);

        //dbManagement.getConnection().DbConnection().createdCollection("test");
        System.out.println(dbManagement.getConnection().DbConnection().dropCollection("test"));
    }
}
