package com.prgms.springbootbasic.util;

import com.prgms.springbootbasic.AppConfig;
import com.prgms.springbootbasic.controller.MenuController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;

public class VoucherApplication {

    private static final ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    public MenuController getController() {
        return ac.getBean(MenuController.class);
    }

    public File getFile() throws IOException {
        Resource resource = ac.getResource("data.csv");
        return resource.getFile();
    }

}
