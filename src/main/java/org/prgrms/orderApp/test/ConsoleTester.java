package org.prgrms.orderApp.test;


import org.prgrms.orderApp.presentation.commandOperator.pages.MainPage;
import org.prgrms.orderApp.config.AppConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.text.MessageFormat;


public class ConsoleTester {


    public static void main(String[] args) throws InterruptedException {
        new AnnotationConfigApplicationContext(AppConfiguration.class);
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        var cmdProgramService = applicationContext.getBean(MainPage.class);

        String result = cmdProgramService.selectedMainMenu();
        Assert.isTrue(result.equals("CREATE_VOUCHER"), MessageFormat.format("result : {0}",result));


    }
}
