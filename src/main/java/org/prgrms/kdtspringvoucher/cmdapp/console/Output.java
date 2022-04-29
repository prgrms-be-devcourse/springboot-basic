package org.prgrms.kdtspringvoucher.cmdapp.console;

import java.util.List;

public interface Output<T> {
    void printList (List<T> list);
}