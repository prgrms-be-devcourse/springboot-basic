package com.blessing333.springbasic.common.ui;

import lombok.Getter;
import org.apache.commons.cli.Options;

@Getter
public class CommandOptions {
    private final Options options = new Options();

    public void addOption(CommandOption option){
        options.addOption(option.getOption());
    }

    public boolean hasOption(String optionName){
        return options.hasOption(optionName);
    }
}
