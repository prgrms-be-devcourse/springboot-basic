package org.prms;

import org.prms.controller.cmdController;
import org.prms.service.AppConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CommandLineApplication {
    public static void main(String[] args) {

        cmdController controller=new cmdController();
        controller.run();

    }

}
