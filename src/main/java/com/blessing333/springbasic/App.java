package com.blessing333.springbasic;

import com.blessing333.springbasic.ui.CommandOptionType;
import com.blessing333.springbasic.ui.UserInterface;
import com.blessing333.springbasic.ui.exception.CommandNotSupportedException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class App {
    private final UserInterface userInterface;

    public void run() {
        boolean isSelectedQuit = false;
        userInterface.showWelcomeText();
        while (!isSelectedQuit) {
            String input = userInterface.inputMessage();
            try {
                CommandOptionType type = CommandOptionType.find(input);
                switch (type) {
                    case CREATE -> doSomething();
                    case LIST -> doSomething();
                    case QUIT -> isSelectedQuit = true;
                    default -> userInterface.showHelpText();
                }
            } catch (CommandNotSupportedException e) {
                userInterface.printMessage(e.getMessage());
                userInterface.showHelpText();
            }
        }
    }

    private void doSomething() {

    }


}
