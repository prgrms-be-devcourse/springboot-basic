package com.blessing333.springbasic.common.ui;

public interface ServiceUserInterface {

    void printMessage(String message);

    String inputMessage();

    default void showHelpText(){}

    void showGuideText();
}
