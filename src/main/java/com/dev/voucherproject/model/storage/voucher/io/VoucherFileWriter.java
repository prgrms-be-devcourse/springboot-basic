package com.dev.voucherproject.model.storage.voucher.io;


import com.dev.voucherproject.model.voucher.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystemNotFoundException;

@Component
public class VoucherFileWriter {
    private static final Logger logger = LoggerFactory.getLogger(VoucherFileWriter.class);
    @Value("${voucher.path}")
    private String path;
    @Value("${voucher.filename}")
    private String filename;

    public Voucher write(Voucher voucher) {
        createDirectory();
        csvFileWrite(voucher);

        return voucher;
    }

    private void createDirectory() {
        File dir = new File(path);

        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    private void csvFileWrite(Voucher voucher) {
        File file = new File(path, filename);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(voucher.toString());
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            logger.warn("{} 파일을 찾을 수 없습니다.", path);
            throw new FileSystemNotFoundException("파일을 찾을 수 없습니다.");
        }
    }
}
