package org.prgrms.kdt.controller;

import java.util.Scanner;

public class InputController {

    public static Scanner sc = new Scanner(System.in);

    public CommandType getUserInput(){
        return CommandType.getCommandType(sc.next().toUpperCase());
    }
    
}