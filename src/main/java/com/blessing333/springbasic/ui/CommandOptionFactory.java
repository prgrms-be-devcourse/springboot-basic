package com.blessing333.springbasic.ui;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommandOptionFactory {

    public static CommandOptions createCommandOptions(Map<String, ? extends CommandOptionType> optionMap) {
        CommandOptions supportedCommandOptions = new CommandOptions();
        optionMap.values()
                .forEach(t -> supportedCommandOptions.addOption(CommandOption.createNewOption(t)));
        return supportedCommandOptions;
    }
}
