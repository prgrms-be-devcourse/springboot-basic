package com.blessing333.springbasic.ui;

public interface ServiceUserInterface {

    void printMessage(String message);

    String inputMessage();

    default void showHelpText(){}

    void showGuideText();
}
