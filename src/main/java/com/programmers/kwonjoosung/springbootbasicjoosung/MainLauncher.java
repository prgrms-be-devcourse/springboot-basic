package com.programmers.kwonjoosung.springbootbasicjoosung;

import com.programmers.kwonjoosung.springbootbasicjoosung.console.Console;
import com.programmers.kwonjoosung.springbootbasicjoosung.controller.CommandType;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class MainLauncher implements InitializingBean, DisposableBean {
    private boolean isRunning = true;
    private final Console console;

    public MainLauncher(Console console){
        this.console = console;
    }

    public void run() {
        while (isRunning) {
            try {
                CommandType command = CommandType.getCommand(console.inputCommand());
                switch (command) {
                    case HELP -> console.showAllCommandSet();
                    case EXIT -> exit();
                }
            } catch (Exception e) {
                console.showError(e);
            }
        }
    }

    private void exit() {
        isRunning = false;
    }

    @Override
    public void afterPropertiesSet() {
        console.startMessage();
        console.showAllCommandSet();
    }

    @Override
    public void destroy() {
        console.ExitMessage();
    }
}
