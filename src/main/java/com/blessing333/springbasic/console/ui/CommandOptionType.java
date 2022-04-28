package com.blessing333.springbasic.console.ui;

public interface CommandOptionType {
    String getOptionName();

    boolean isArgumentRequired();

    String getDescription();
}
