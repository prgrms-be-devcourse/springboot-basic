package com.prgrms.vouchermanager.handler;

import com.prgrms.vouchermanager.exception.MyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ApplicationRunner implements CommandLineRunner {

    private final CommandHandler commandHandler;

    public ApplicationRunner(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @Override
    public void run(String... args) {
        while(true){
            boolean flag = true;
            try {
                flag = commandHandler.selectProgram();
            } catch (MyException e) {
                log.error(e.getMessage());
                System.out.println(e.consoleMessage());
            }
            if(!flag) break;
        }
    }
}
