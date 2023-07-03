package com.dev.voucherproject.model.storage.io;


import com.dev.voucherproject.model.voucher.Voucher;
import com.dev.voucherproject.model.voucher.VoucherDto;
import com.dev.voucherproject.model.voucher.VoucherPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystemNotFoundException;
import java.text.MessageFormat;

@Component
public class VoucherFileWriter {
    private static final Logger logger = LoggerFactory.getLogger(VoucherFileWriter.class);
    @Value("${voucher.path}")
    private String path;
    @Value("${voucher.filename}")
    private String filename;

    public Voucher write(Voucher voucher) {
        createDirectory();

        VoucherDto dto = voucher.conversionDto();
        csvFileWrite(dto);

        return voucher;
    }

    private void createDirectory() {
        File dir = new File(path);

        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    private void csvFileWrite(VoucherDto dto) {
        File file = new File(path, filename);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(voucherWriteFormatting(dto));
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            logger.warn("{} 파일을 찾을 수 없습니다.", filename);
            throw new FileSystemNotFoundException(MessageFormat.format("{0} 파일을 찾을 수 없습니다.", filename));
        }
    }

    private String voucherWriteFormatting(VoucherDto dto) {
        if (dto.getVoucherPolicy() == VoucherPolicy.FIXED_AMOUNT_VOUCHER) {
            return "FIXED_AMOUNT_VOUCHER,%d,%s".formatted(dto.getDiscountNumber(), dto.getVoucherId());
        }

        return "PERCENT_DISCOUNT_VOUCHER,%d,%s".formatted(dto.getDiscountNumber(), dto.getVoucherId());
    }
}
