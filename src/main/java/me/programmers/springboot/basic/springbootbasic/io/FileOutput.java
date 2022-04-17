package me.programmers.springboot.basic.springbootbasic.io;

import me.programmers.springboot.basic.springbootbasic.voucher.model.Voucher;

import java.io.IOException;

public interface FileOutput {
    void fileOutput(String fileName, Voucher voucher) throws IOException;
}
