package org.prgrms.vouchermanager.console;

public interface Output {
    void print(String message);

    default void println(String message){
        print(message+"\n");
    }
}
