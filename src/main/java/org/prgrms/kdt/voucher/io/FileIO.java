package org.prgrms.kdt.voucher.io;

import org.springframework.stereotype.Component;

import java.util.List;

public interface FileIO<E> {

    void write(List<E> outputList);

    List<E> readAllLines();

}
