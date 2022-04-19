package me.programmers.springboot.basic.springbootbasic.io;

public class Out implements ConsoleOutput {

    @Override
    public void output(String message) {
        System.out.println(message);
    }
}
