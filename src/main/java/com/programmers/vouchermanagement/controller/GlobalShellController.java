package com.programmers.vouchermanagement.controller;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class GlobalShellController {
    @ShellMethod(key = "exit")
    public void exit() {
        System.exit(0);
    }
}
