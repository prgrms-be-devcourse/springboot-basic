package me.programmers.springboot.basic.springbootbasic.io;

import me.programmers.springboot.basic.springbootbasic.voucher.model.Voucher;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public interface FileInput {
    Map<UUID, Voucher> fileInput(String fileName) throws IOException;
}
