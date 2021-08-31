package org.prgrms.dev.voucher.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.dev.voucher.domain.FixedAmountVoucher;
import org.prgrms.dev.voucher.domain.Voucher;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;


class FileVoucherRepositoryTest {
    private static final File path = new File(System.getProperty("user.dir"), "src/main/resources/voucher.csv");

    @Test
    @DisplayName("파일에 바우처 저장")
    void create() {
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 1000L);

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path, true))) {
            bufferedWriter.write(voucher.toString());
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}