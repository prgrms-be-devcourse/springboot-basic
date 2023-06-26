package com.dev.bootbasic;

import com.dev.bootbasic.view.Command;
import com.dev.bootbasic.view.ViewManager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineApplication implements CommandLineRunner {

    private final ViewManager viewManager;

    public CommandLineApplication(ViewManager viewManager) {
        this.viewManager = viewManager;
    }

    @Override
    public void run(String... args) throws Exception {
        Command command = viewManager.readCommand();
    }

}
