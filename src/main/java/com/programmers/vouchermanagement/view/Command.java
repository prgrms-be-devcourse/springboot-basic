package com.programmers.vouchermanagement.view;

public enum Command {
    EXIT, CREATE, LIST;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
