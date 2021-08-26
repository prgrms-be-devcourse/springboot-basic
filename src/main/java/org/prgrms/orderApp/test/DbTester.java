package org.prgrms.orderApp.test;

import org.prgrms.orderApp.infrastructure.library.monguDb.config.MonguDbConfiguragtion;
import org.prgrms.orderApp.infrastructure.library.monguDb.service.DbManagement;
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
