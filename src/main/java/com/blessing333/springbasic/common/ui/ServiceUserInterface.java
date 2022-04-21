package com.blessing333.springbasic.common.ui;

public interface ServiceUserInterface {
    default void printDivider(){
        printMessage("=========================");
    }
    void printMessage(String message);

    String inputMessage();

    default void showHelpText(){}

    void showGuideText();
}
