package org.promgrammers.springbootbasic.view;

public class Writer implements Output {

    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public void print(String[] messages) {
        for (String message : messages) {
            System.out.println(message);
        }
    }
}
