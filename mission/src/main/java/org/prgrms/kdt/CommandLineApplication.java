package org.prgrms.kdt;

import org.prgrms.kdt.controller.Controller;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;


public class CommandLineApplication implements ApplicationRunner {

    Controller controller;

    public boolean checkInput(String inputString) {
        return true;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
