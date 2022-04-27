package com.blessing333.springbasic.console_app.ui;

public interface CommandOptionType {
    String getOptionName();

    boolean isArgumentRequired();

    String getDescription();
}
