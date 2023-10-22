package com.programmers.vouchermanagement.view;

public enum Command {
    EXIT, CREATE, LIST, BLACKLIST;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
