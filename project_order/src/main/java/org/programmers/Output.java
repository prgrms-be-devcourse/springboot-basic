package org.programmers;

import java.io.IOException;

public interface Output {

    void printPrompt() throws IOException;

    void printSign() throws IOException;

    void printVoucherTypes() throws IOException;

    void askAmount() throws IOException;

    void askPercentage() throws IOException;

}
