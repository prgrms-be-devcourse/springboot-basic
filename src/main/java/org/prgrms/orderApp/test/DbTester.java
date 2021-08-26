package org.prgrms.orderApp.test;

import org.prgrms.orderApp.config.component.AppConfiguration;
import org.prgrms.orderApp.infrastructure.library.monguDb.service.DbManagement;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class DbTester {
    public static void main(String[] args) throws IOException {
        var monguContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        var dbManagement = monguContext.getBean(DbManagement.class);

        //dbManagement.getConnection().DbConnection().createdCollection("test");
        System.out.println(dbManagement.getConnection().DbConnection().dropCollection("test"));
    }
}
