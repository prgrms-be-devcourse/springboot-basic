package org.prgrms.kdt;

import org.prgrms.kdt.command.Command;
import org.prgrms.kdt.command.CommandTypes;
import org.prgrms.kdt.exception.InvalidIOMessageException;
import org.prgrms.kdt.io.Input;
import org.prgrms.kdt.io.Output;

import java.util.Map;

public class CommandLineApplication {
    private final Input inputStream;
    private final Output outputStream;

    public CommandLineApplication(Input inputStream, Output outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public void run(String startMessage, Map<CommandTypes, Command> commandMap) throws InvalidIOMessageException {
        String commandStr = "";

        while (true) {
            outputStream.write(startMessage);
            commandStr = inputStream.readLine().toUpperCase();

            if (CommandTypes.EXIT.toString().equals(commandStr)) // EXIT 일 경우
                break;

            try{
                commandMap.get(CommandTypes.valueOf(commandStr)).doCommands();
            }
            catch (IllegalArgumentException e){
                outputStream.write("command not found\n\n");
            }
        }
    }

}
