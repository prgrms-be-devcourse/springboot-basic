package com.programmers.kdtspringorder.command;

public class ExitCommandAction implements CommandAction {

    @Override
    public void act() {
        System.exit(0);
    }
}
