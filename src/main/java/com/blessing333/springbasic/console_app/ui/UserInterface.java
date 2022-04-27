package com.blessing333.springbasic.console_app.ui;

public interface UserInterface {
    default void printDivider() {
        printMessage("=========================");
    }

    void printMessage(String message);

    String requestMessage();

    default void printHelp() {
    }

    void printGuide();
}
