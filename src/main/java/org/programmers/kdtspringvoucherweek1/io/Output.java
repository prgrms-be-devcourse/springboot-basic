package org.programmers.kdtspringvoucherweek1.io;

import org.programmers.kdtspringvoucherweek1.log.LogLevel;

public interface Output {
    void mainMenu(String version);

    void msg(String msg);

    void createMenu();

    void listMenu();

    void logging(LogLevel warn, String msg);
}
