package com.example.kdtspringmission.voucher.repository;

import com.example.kdtspringmission.voucher.domain.FixedAmountVoucher;
import com.example.kdtspringmission.voucher.domain.RateAmountVoucher;
import com.example.kdtspringmission.voucher.domain.Voucher;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileVoucherRepository implements VoucherRepository{

    private static final String AMOUNT_DELIM = "=";
    private static final String VOUCHERS_PATH = "src/main/resources/vouchers/";
    private static final File resources = new File(VOUCHERS_PATH);
    private static final Pattern amountPattern = Pattern.compile("amount=(\\d*)|percent=(\\d*)");
    private static final Pattern idPattern = Pattern.compile("id=(.*?),");



    @Override
    public UUID insert(Voucher voucher) {
        Path file = Paths.get(VOUCHERS_PATH + voucher.toString() + ".txt");
        try {
            if (!Files.exists(file)) {
                Files.createFile(file);
            }
        } catch (IOException e) {
            throw new IllegalStateException("File Creation Error");
        }
        return voucher.getId();
    }

    @Override
    public Voucher findById(UUID id) {
        String fileName = Arrays.stream(Objects.requireNonNull(resources.list()))
            .filter(filename -> filename.contains(id.toString()))
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);

        return findVoucher(id, fileName);
    }

    private Voucher findVoucher(UUID id, String fileName) {
        Matcher matcher = amountPattern.matcher(fileName);
        if (fileName.contains("Fix")) {
            if (matcher.find()) {
                return new FixedAmountVoucher(id, getAmount(matcher));
            }
        }
        if (fileName.contains("Rate")) {
            if (matcher.find()) {
                return new RateAmountVoucher(id, getAmount(matcher));
            }
        }
        return null;
    }

    private long getAmount(Matcher matcher) {
        long amount;
        String group = matcher.group();
        String[] split = group.split(AMOUNT_DELIM);  // split = {amount, 500}
        amount = Long.parseLong(split[1]);
        return amount;
    }

    private UUID getId(Matcher matcher) {
        UUID id;
        String group = matcher.group();
        String[] split = group.split(AMOUNT_DELIM);  // split = {amount, 500}
        String uuid = String.join("", split[1].split(","));
        id = UUID.fromString(uuid);
        return id;
    }

    @Override
    public List<Voucher> findAll() {
        List<Voucher> vouchers = new ArrayList<>();
        String[] fileNames = resources.list();
        UUID id;
        for (String fileName : fileNames) {
            Matcher matcher = idPattern.matcher(fileName);
            if (matcher.find()) {
                id = getId(matcher);
                vouchers.add(findById(id));
            }
        }

        return vouchers;
    }

    @Override
    public void clear() {
        for (File file : resources.listFiles()) {
            if (!file.isDirectory())
                file.delete();
        }
    }
}
