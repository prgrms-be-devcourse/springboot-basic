package com.programmers.repository.voucher;

import com.programmers.domain.voucher.Voucher;
import com.programmers.domain.voucher.VoucherType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Repository
@Profile("dev")
public class FileVoucherRepository implements VoucherRepository {

    private final Path path;

    public FileVoucherRepository(@Value("${file.voucher.file-path}") String filePath) {
        this.path = Paths.get(filePath);
    }

    @Override
    public Voucher save(Voucher voucher) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile(), true)); //path?
            writer.write(voucher.toString());
            writer.newLine();

            writer.flush();
        } catch (IOException ignored) {
        }

        return voucher;
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

    @Override
    public Optional<Voucher> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public Voucher update(Voucher voucher) {
        return null;
    }

    @Override
    public void deleteById(UUID id) {

    }

    @Override
    public void deleteAll() {

    }

    public Voucher extractVoucher(String line) {
        line = line.replace(" ", "");
        String[] voucherInfo = line.split("\\[|\\]|=|,");

        String type = voucherInfo[2].toLowerCase();
        UUID id = UUID.fromString(voucherInfo[4]);
        long discountValue = Long.parseLong(voucherInfo[6]);
        String name = voucherInfo[8];

        return VoucherType.createVoucher(type, id, name, discountValue);
    }
}
