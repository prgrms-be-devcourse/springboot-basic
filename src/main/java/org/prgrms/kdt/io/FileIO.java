package org.prgrms.kdt.io;


import org.prgrms.kdt.voucher.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.prgrms.kdt.utils.FileParser.getVoucherInfo;

@Component
public class FileIO {
    private static final String VOUCHER_FILE_DIRECTORY = "src/main/resources/vouchers/";
    private static final Logger logger = LoggerFactory.getLogger(FileIO.class);

    public String read(String voucherId) {
        File vouchersFile = getFile(voucherId);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(vouchersFile))) {
            return bufferedReader.readLine();
        } catch (IOException fileReadException) {
            throw new RuntimeException("파일에 읽어오는 것을 실패하였습니다.", fileReadException);
        }
    }

    public List<String> readFileList(){
        File voucherFolder = getDirectory();
        return Arrays.asList(
                Objects.requireNonNullElseGet(
                        voucherFolder.list(), () -> new String[]{}));
    }

    public void write(Voucher voucher) {
        String filePath = MessageFormat.format("{0}{1}.txt", VOUCHER_FILE_DIRECTORY, voucher.getVoucherId().toString());
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath, true))) {
            bufferedWriter.write(getVoucherInfo(voucher));
            bufferedWriter.flush();
        } catch (IOException fileWriteException) {
            logger.error("파일에 바우처를 저장하는 것을 실패하였습니다.", fileWriteException);
        }
    }

    private File getFile(String voucherId) {
        String filePath = MessageFormat.format("{0}{1}.txt", FileIO.VOUCHER_FILE_DIRECTORY, voucherId);
        return new File(filePath);
    }

    private File getDirectory() {
        return new File(FileIO.VOUCHER_FILE_DIRECTORY);
    }
}
