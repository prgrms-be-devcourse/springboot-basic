package org.prgrms.kdt.devcourse.voucher;

import java.io.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FileVoucherRepository implements VoucherRepository{
    private final String filePath = System.getProperty("user.dir")+"/voucher.csv";
    private File csvFile = new File(filePath);
    @Override
    public Optional<Voucher> findById(UUID voucherId) {

        return Optional.empty();
    }

    @Override
    public Voucher insert(Voucher voucher) {

        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return null;
    }
}
