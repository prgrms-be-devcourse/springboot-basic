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
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

//@Repository
//@Profile("dev")
public class FileVoucherRepository implements VoucherRepository{

    private static final String AMOUNT_DELIM = "=";
    private static final String VOUCHERS_PATH = "src/main/resources/vouchers/";
    private static final File resources = new File(VOUCHERS_PATH);
    private static final String VOUCHER_TYPE_FIXED = "Fixed";
    private static final String VOUCHER_TYPE_RATE = "Rate";
    private static final Pattern amountPattern = Pattern.compile("amount=(\\d*)|percent=(\\d*)");
    private static final Pattern idPattern = Pattern.compile("id=(.*?),");
    private static final Pattern typePattern = Pattern.compile("\\w+");

    @Override
    public UUID insert(Voucher voucher) {
        Path file = Paths.get(VOUCHERS_PATH + voucher + ".txt");
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
            .orElseThrow(() -> new IllegalArgumentException("No Such File, id = " + id));

        return findVoucher(id, fileName);
    }

    @Override
    public Voucher update(Voucher voucher) {
        return null;
    }

    private Voucher findVoucher(UUID id, String fileName) {
        Matcher matcher = amountPattern.matcher(fileName);
        if (fileName.contains(VOUCHER_TYPE_FIXED)) {
            if (matcher.find()) {
                return new FixedAmountVoucher(id, getAmount(matcher));
            }
        }
        if (fileName.contains(VOUCHER_TYPE_RATE)) {
            if (matcher.find()) {
                return new RateAmountVoucher(id, getAmount(matcher));
            }
        }
        throw new IllegalArgumentException("No Such Voucher Type");
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
        String[] split = group.split(AMOUNT_DELIM);  // split = {amount, a12ea-12qw-adw2}
        String uuid = String.join("", split[1].split(","));
        id = UUID.fromString(uuid);
        return id;
    }

    @Override
    public List<Voucher> findAll() {
        List<Voucher> vouchers = new ArrayList<>();

        Arrays.stream(Objects.requireNonNull(resources.list()))
            .map(idPattern::matcher)
            .filter(Matcher::find)
            .forEach(matcher -> vouchers.add(findById(getId(matcher))));

        return vouchers;
    }

    @Override
    public List<Voucher> findByOwnerId(UUID ownerId) {
        return null;
    }

    @Override
    public void deleteAll() {
        Arrays.stream(resources.listFiles())
            .filter(file -> !file.isDirectory())
            .forEach(File::delete);
    }
}
