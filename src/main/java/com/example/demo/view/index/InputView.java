package com.example.demo.view.index;

import com.example.demo.util.IndexMenuCommand;
import com.example.demo.view.validate.CommandValidator;
import java.util.Scanner;

public class InputView {

    public static Scanner sc = new Scanner(System.in);

    public IndexMenuCommand readCommandOption() {
        String input = sc.nextLine();
        CommandValidator.validateCommandNumberOneToThree(input);
        return IndexMenuCommand.from(Integer.parseInt(input));
    }

}
