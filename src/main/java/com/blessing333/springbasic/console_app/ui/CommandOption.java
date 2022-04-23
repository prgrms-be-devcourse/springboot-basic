package com.blessing333.springbasic.console_app.ui;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.cli.Option;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter(AccessLevel.PRIVATE)
public class CommandOption {
    private Option option;

    public static CommandOption createNewOption(CommandOptionType optionType){
        CommandOption instance = new CommandOption();
        Option option = new Option(optionType.getOptionName(),optionType.isArgumentRequired(),optionType.getDescription());
        instance.setOption(option);
        return instance;
    }

}
