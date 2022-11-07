package org.prgrms.kdt.io;

import org.springframework.stereotype.Component;

@Component
public class IOManager {
    private final Console console;

    public IOManager(Console console) {
        this.console = console;
    }

    public String getInput() {
        return console.read().toLowerCase().strip();
    }

    public void doStartMessage(){
        console.write(MessageType.CONSOLE_START.getMessage());
    }

    public void doErrorMessage(String message){
        console.write(message);
    }

    public void doEndMessage(){
        console.write(MessageType.CONSOLE_END.getMessage());
    }
}
