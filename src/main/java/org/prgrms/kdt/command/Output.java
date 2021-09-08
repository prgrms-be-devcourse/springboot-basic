package org.prgrms.kdt.command;

public interface Output {

    void printMessage(String message);

    void printError(String input);

    <E extends Enum<E>> void printEnumValues(E[] values);

}
