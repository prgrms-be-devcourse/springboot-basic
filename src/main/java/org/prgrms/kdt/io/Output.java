package org.prgrms.kdt.io;

/**
 * Created by yhh1056
 * Date: 2021/08/17 Time: 11:31 오후
 */
public interface Output {

    void guide();

    void successCreate();

    void printVoucherChoice();

    void commandError();

    void printLine(String line);

    void printNextCommand();

    void printExit();
}
