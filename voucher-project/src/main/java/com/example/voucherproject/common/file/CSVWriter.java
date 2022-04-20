package com.example.voucherproject.common.file;

import com.example.voucherproject.user.domain.User;
import com.example.voucherproject.voucher.domain.Voucher;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Slf4j
public class CSVWriter implements MyWriter {

    private String saveDirectory = "./src/main/resources/";

    @Override
    public User writeUser(User user, String fileName) {
        try {
            var file = getFile(saveDirectory + fileName);
            writeUser(file, user);
        } catch (IOException e) {
            log.error("Can't write User To CSV", e);
        }
        return user;
    }

    @Override
    public Voucher writeVoucher(Voucher voucher, String fileName) {
        try {
            var file = getFile(saveDirectory + fileName);
            writeVoucher(file, voucher);
        } catch (IOException e) {
            log.error("Can't write Voucher To CSV", e);
        }
        return voucher;
    }

    private User writeUser(File file, User user) throws IOException{
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {

            var id = user.getId();
            var type = user.getType();
            var name = user.getName();
            var createdAt = user.getCreatedAt();

            writer.write(id.toString() + "," + type.toString() + "," + name + "," + createdAt.toString());
            writer.newLine();
        }
        return user;
    }

    private Voucher writeVoucher(File file, Voucher voucher) throws IOException{
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            var id = voucher.getId();
            var type = voucher.getType();
            var amount = voucher.getAmount();
            var createdAt = voucher.getCreatedAt();

            writer.write(id.toString() + "," + type.toString()+","+amount.toString()+ ","+createdAt.toString());
            writer.newLine();
        }
        return voucher;
    }

    private File getFile(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }
}
