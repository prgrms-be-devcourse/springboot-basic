package org.prgrms.kdt.io;

import org.prgrms.kdt.voucher.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileIO {

    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private static final String VOUCHER_FILE_URL = "Vouchers.txt";
    private final File vouchersFile = new File(VOUCHER_FILE_URL);
    private static final Logger logger = LoggerFactory.getLogger(FileIO.class);

    public List<String> read() {
        try {
            List<String> savedVouchers = new ArrayList<>();
            bufferedReader = new BufferedReader(new FileReader(vouchersFile));
            bufferedReader.lines().forEach(savedVouchers::add);
            return savedVouchers;
        } catch (FileNotFoundException e) {
            logger.error("파일이 존재하지 않아 읽어오는 데에 실패하였습니다. 파일 경로를 다시 확인해주세요.");
            throw new RuntimeException(e.getMessage());
        }
    }

    public void write(Voucher voucher) {
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(VOUCHER_FILE_URL, true));
            String voucherInfo = MessageFormat.format("{0}/{1}/{2}\n", voucher.getVoucherId().toString(), voucher.getClass().getSimpleName(), voucher.getAmount());
            logger.info("voucher 객체를 string으로 변환 -> {}", voucherInfo);
            bufferedWriter.write(voucherInfo);
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            logger.error("파일에 바우처를 저장하는 것을 실패하였습니다.");
        }
    }
}
