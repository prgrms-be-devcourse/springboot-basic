package com.programmers.repository;

import com.programmers.domain.Voucher;
import com.programmers.domain.VoucherType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Repository
@Profile("dev")
public class FileVoucherRepository implements VoucherRepository {

    private final Path path;

    public FileVoucherRepository(@Value("${file.voucher.file-path}") String filePath) {
        this.path = Paths.get(filePath);
    }

    @Override
    public void save(Voucher voucher) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile(), true)); //path?
            writer.write(voucher.toString());
            writer.newLine();

            writer.flush();
        } catch (IOException ignored) {
        }
    }

    @Override
    public List<Voucher> findAll() {
        List<Voucher> voucherList = new ArrayList<>();
        try {
            if (Files.exists(path)) {
                List<Voucher> vouchers = Files.readAllLines(path).stream()
                        .map(this::extractVoucher)
                        .toList();

                voucherList.addAll(vouchers);
            }
        } catch (IOException ignored) {
        }

        return Collections.unmodifiableList(voucherList);
    }

    public Voucher extractVoucher(String line) {
        line = line.replace(" ", "");
        String[] voucherInfo = line.split("\\[|\\]|=|,");

        String type = voucherInfo[2].toLowerCase();
        UUID id = UUID.fromString(voucherInfo[4]);
        long discountValue = Long.parseLong(voucherInfo[6]);
        String name = voucherInfo[8];

        return VoucherType.constructVoucher(type, id, name, discountValue);
    }
}
