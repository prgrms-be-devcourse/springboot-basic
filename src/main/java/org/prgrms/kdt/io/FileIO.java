package org.prgrms.kdt.io;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;

@Component
public class FileIO {
    private static final String VOUCHER_FILE_URL = "Vouchers.txt";
    private final File vouchersFile = new File(VOUCHER_FILE_URL);
    private static final Logger logger = LoggerFactory.getLogger(FileIO.class);

    public List<String> read() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(vouchersFile))) {
            return bufferedReader.lines().toList();
        } catch (IOException fileReadException) {
            throw new RuntimeException("파일에 읽어오는 것을 실패하였습니다.", fileReadException);
        }
    }

    public void write(String voucherInfo) {
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(VOUCHER_FILE_URL, true))) {
            bufferedWriter.write(voucherInfo);
            bufferedWriter.flush();
        } catch (IOException fileWriteException) {
            logger.error("파일에 바우처를 저장하는 것을 실패하였습니다.", fileWriteException);
        }
    }
}
