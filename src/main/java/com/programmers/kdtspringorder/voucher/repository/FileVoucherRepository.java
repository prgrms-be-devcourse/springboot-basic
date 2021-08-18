package com.programmers.kdtspringorder.voucher.repository;

import com.programmers.kdtspringorder.voucher.domain.FixedAmountVoucher;
import com.programmers.kdtspringorder.voucher.domain.PercentDiscountVoucher;
import com.programmers.kdtspringorder.voucher.domain.Voucher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@Qualifier("file")
public class FileVoucherRepository implements VoucherRepository {

    private final File file;
    private final BufferedWriter writer;

    public FileVoucherRepository() throws IOException {
        file = new File("voucher.txt");
        if (!file.exists()) {
            file.createNewFile();
        }

        writer = new BufferedWriter(new FileWriter(file, true));
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            List<String> strings = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
            return strings.stream()
                    .map(str -> str.split(" "))
                    .filter(arr -> arr[1].equals(voucherId.toString()))
                    .map(this::createConcreteVoucher)
                    .findFirst();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Voucher> findAll() {
        try {
            List<String> strings = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
            return strings.stream()
                    .map(str -> str.split(" "))
                    .map(this::createConcreteVoucher)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private Voucher createConcreteVoucher(String[] voucherText) {
        if ("FixedAmountVoucher".equals(voucherText[0])) {
            return new FixedAmountVoucher(UUID.fromString(voucherText[1]), Long.parseLong(voucherText[2]));
        } else {
            return new PercentDiscountVoucher(UUID.fromString(voucherText[1]), Long.parseLong(voucherText[2]));
        }
    }

    @Override
    public Voucher save(Voucher voucher) {
        try {
            writer.write(voucher.getClass().getSimpleName() + " " + voucher.getVoucherId().toString() + " " + voucher.getValue());
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return voucher;
    }
}
