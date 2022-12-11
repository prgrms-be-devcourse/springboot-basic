package org.programmers.weekly.mission.util.io;

public interface Output <T> {
    void printMessage(String message);
    void printObject(T object);
}
