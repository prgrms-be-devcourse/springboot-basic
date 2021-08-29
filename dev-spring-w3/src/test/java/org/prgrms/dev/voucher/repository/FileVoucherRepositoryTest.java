package org.prgrms.dev.voucher.repository;

import org.junit.jupiter.api.Test;
import org.prgrms.dev.voucher.domain.FixedAmountVoucher;
import org.prgrms.dev.voucher.domain.Voucher;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class FileVoucherRepositoryTest {
    private static final File path = new File(System.getProperty("user.dir"),"src/main/resources/voucher.csv");

    @Test
    void 파일에바우처저장() {
        Voucher voucher1 = new FixedAmountVoucher(UUID.randomUUID(),1000L);
        Voucher voucher2 = new FixedAmountVoucher(UUID.randomUUID(),2000L);
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path ,true));

            bufferedWriter.write(voucher1.toString());
            bufferedWriter.newLine();
            bufferedWriter.write(voucher2.toString());
            bufferedWriter.newLine();

            bufferedWriter.flush();
            bufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    void findById() {
    }

    @Test
    void findAll() {
    }
}