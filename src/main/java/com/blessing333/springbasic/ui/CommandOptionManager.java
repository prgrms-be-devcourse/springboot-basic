package com.blessing333.springbasic.ui;

import org.apache.commons.cli.Options;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CommandOptionManager {
    private static final CommandOptions supportedCommandOptions =
            initSupportedCommandOptions(CommandOptionType.getAvailableCommandOptionType());

    private static CommandOptions initSupportedCommandOptions(Map<String, CommandOptionType> optionMap) {
        CommandOptions supportedCommandOptions = new CommandOptions();
        optionMap.values()
                .forEach(t -> supportedCommandOptions.addOption(CommandOption.createNewOption(t)));
        return supportedCommandOptions;
    }

    public CommandOptions getSupportedOptions() {
        return supportedCommandOptions;
    }

    public Options getOptions(){
        return supportedCommandOptions.getOptions();
    }
}
