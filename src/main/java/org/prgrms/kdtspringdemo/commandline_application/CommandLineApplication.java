package org.prgrms.kdtspringdemo.commandline_application;

import org.prgrms.kdtspringdemo.io.Console;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineApplication implements ApplicationRunner {
    private boolean isRunning = true;
    private final Console console;
    public CommandLineApplication(Console console) {
        this.console = console;
    }

    @Override
    public void run(ApplicationArguments args) {
        while(isRunning){
            //output 책임
            console.showMenu();
            String myString = console.getInputWithPrompt("");
            switchModeByInput(myString);
        }
    }

    //분기하는 책임
    private void switchModeByInput(String myString) {
        switch (Command.getCommand(myString)) {
            case EXIT -> {
                //종료하는 책임
                System.out.println("종료하기");
                this.isRunning = false;
            }
            case CREATE -> {
                //생성하는 책임
                System.out.println("create 로직");
            }
            case LIST -> {
                //list를 보여주는 책임
                System.out.println("List 로직");
            }
            case ERROR -> {
                //에러를 처리하는 책임
                console.showError(new Exception("잘못된 입력 입니다."));
            }
        }
    }

}
