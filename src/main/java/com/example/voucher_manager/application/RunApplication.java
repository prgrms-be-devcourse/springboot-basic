package com.example.voucher_manager.application;

import com.example.voucher_manager.io.CommandType;
import com.example.voucher_manager.io.Console;
import org.springframework.stereotype.Component;

@Component
public class RunApplication implements Runnable{
    private final Console console;
    private ApplicationStatus status = ApplicationStatus.IDLE;

    public RunApplication(Console console) {
        this.console = console;
    }

    @Override
    public void run() {
        while (!status.equals(ApplicationStatus.STOP)) {
            CommandType command = console.inputCommand();
            changeStatus(command);
        }
    }

    private void changeStatus(CommandType command){
        if (command.equals(CommandType.EXIT)) {
            status = ApplicationStatus.STOP;
            return;
        }
        status = ApplicationStatus.RUNNING;
    }
}
