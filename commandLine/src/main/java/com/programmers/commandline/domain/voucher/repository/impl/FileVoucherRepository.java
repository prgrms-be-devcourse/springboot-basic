package com.programmers.commandline.domain.voucher.repository.impl;

import com.programmers.commandline.domain.voucher.entity.Voucher;
import com.programmers.commandline.domain.voucher.repository.VoucherRepository;
import com.programmers.commandline.global.factory.LoggerFactory;
import com.programmers.commandline.global.io.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@Profile("prod")
public class FileVoucherRepository implements VoucherRepository {

    private final String filePath;
    private final File file;

    FileVoucherRepository(@Value("${file.voucherResourcesPath}") String filePath) {
        this.filePath = filePath;
        this.file = new File(filePath);
    }

    @Override
    public Voucher save(Voucher voucher) {
        LoggerFactory.getLogger().info("FileVoucherRepository save 실행");
        try {
            FileWriter fileWriter = new FileWriter(filePath, file.exists());

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write("Id: " + voucher.getVoucherId() +
                    " Type: " + voucher.getType() +
                    " Discount: " + voucher.getDiscount() +
                    " $");
            bufferedWriter.newLine();

            bufferedWriter.close();

            return voucher;
        } catch (IOException e) {
            LoggerFactory.getLogger().error("FileVoucherRepository save 에러발생");

            throw new IllegalArgumentException(Message.FILE_SAVE_ERROR.getMessage());
        }
    }

    @Override
    public List<Voucher> findAll() {
        LoggerFactory.getLogger().info("FileVoucherRepository findAll 실행");

        List<Voucher> voucherList = new ArrayList<>();

        try {
            FileReader fileReader = new FileReader(this.file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] findLine = line.split(" ");
                String uuid = findLine[1];
                String voucherType = findLine[3];
                String discount = findLine[5];

                Voucher voucher()

                voucherList.add(voucher);
            }
            return voucherList;
        } catch (IOException e) {
            LoggerFactory.getLogger().error("FileVoucherRepository findAll 에러발생");
            throw new IllegalArgumentException(Message.FILE_READ_ERROR.getMessage());
        }
    }
}
