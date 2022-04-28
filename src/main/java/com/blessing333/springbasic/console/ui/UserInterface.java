package com.blessing333.springbasic.console.ui;

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
