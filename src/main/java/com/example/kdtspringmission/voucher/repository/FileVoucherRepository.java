package com.example.kdtspringmission.voucher.repository;

import com.example.kdtspringmission.voucher.domain.FixedAmountVoucher;
import com.example.kdtspringmission.voucher.domain.RateAmountVoucher;
import com.example.kdtspringmission.voucher.domain.Voucher;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileVoucherRepository implements VoucherRepository{

    private final String path = "src/main/resources/vouchers/";
    private final File resources = new File(path);
    private final Pattern pattern = Pattern.compile("amount=(\\d*)|percent=(\\d*)");



    @Override
    public UUID insert(Voucher voucher) {
        File file = new File(path + voucher.toString() + ".txt");
        try {
            if (file.createNewFile()) { }
        } catch (IOException e) {
            throw new IllegalStateException("File Not Created");
        }
        return voucher.getId();
    }

    @Override
    public Voucher findById(UUID id) {
        String[] fileNames = resources.list();
        for (String fileName : fileNames) {
            if (fileName.contains(id.toString())) {
                Matcher matcher = pattern.matcher(fileName);
                long amount = 0;

                if (fileName.contains("Fix")) {
                    if (matcher.find()) {
                        String group = matcher.group();
                        String[] split = group.split("=");
                        amount = Long.parseLong(split[1]);
                        return new FixedAmountVoucher(id, amount);
                    }
                }
                if (fileName.contains("Rate")) {
                    if (matcher.find()) {
                        String group = matcher.group();
                        System.out.println("group =" + group);
                        String[] split = group.split("=");
                        amount = Long.parseLong(split[1]);
                        return new RateAmountVoucher(id, amount);
                    }
                }
            }
        }
        return null;
    }

    @Override
    public List<Voucher> findAll() {
        return null;
    }
}
