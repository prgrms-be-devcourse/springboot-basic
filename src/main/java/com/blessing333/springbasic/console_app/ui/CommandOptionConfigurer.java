package com.blessing333.springbasic.console_app.ui;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommandOptionConfigurer {

    public static CommandOptions configSupportedCommandOptions(Map<String, ? extends CommandOptionType> optionMap) {
        CommandOptions supportedCommandOptions = new CommandOptions();
        optionMap.values()
                .forEach(t -> supportedCommandOptions.addOption(CommandOption.createNewOption(t)));
        return supportedCommandOptions;
    }
}
