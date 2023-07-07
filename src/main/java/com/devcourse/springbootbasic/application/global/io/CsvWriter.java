package com.devcourse.springbootbasic.application.global.io;

import com.devcourse.springbootbasic.application.global.exception.ErrorMessage;
import com.devcourse.springbootbasic.application.voucher.VoucherConverter;
import com.devcourse.springbootbasic.application.voucher.model.Voucher;
import com.devcourse.springbootbasic.application.global.exception.InvalidDataException;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Component
public class CsvWriter {

    public Voucher writeFile(String filepath, Voucher voucher) {
        createFileIfNotExist(filepath);
        write(filepath, voucher);
        return voucher;
    }

    private void write(String filepath, Voucher voucher) {
        File file = new File(filepath);
        try (
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true))
        ) {
            bufferedWriter.write(VoucherConverter.convertVoucherToCsv(voucher));
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            throw new InvalidDataException(ErrorMessage.INVALID_FILE_ACCESS.getMessageText(), e.getCause());
        }
    }

    private void createFileIfNotExist(String filepath) {
        File file = new File(filepath);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            throw new InvalidDataException(ErrorMessage.INVALID_FILE_ACCESS.getMessageText(), e.getCause());
        }
    }

}
