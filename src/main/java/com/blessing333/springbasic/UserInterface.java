package com.blessing333.springbasic;

public interface UserInterface {

    void printMessage(String message);

    String inputMessage();

    default void showHelpText(){}

    void showGuideText();
}
