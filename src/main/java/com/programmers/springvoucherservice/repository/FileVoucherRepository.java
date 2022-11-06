package com.programmers.springvoucherservice.repository;

import com.programmers.springvoucherservice.domain.voucher.Voucher;
import com.programmers.springvoucherservice.domain.voucher.VoucherList;
import org.ini4j.Wini;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Profile("dev")
@Repository
public class FileVoucherRepository implements VoucherRepository {
    private static final String VOUCHER_TYPE = "type";
    private static final String VOUCHER_VALUE = "value";
    private final Wini wini;
    @Value("${kdt.voucher.save-path}")
    private String path;

    @PostConstruct
    public void setting() throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
        }

        wini = new Wini(file);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return getVoucherFromFile(voucherId);
    }

    @Override
    public List<Voucher> findAllVouchers() {
        List<Voucher> vouchers = new ArrayList<>();
        Set<String> voucherIdSet = wini.keySet();

        for (String voucherId : voucherIdSet) {
            Optional<Voucher> optionalVoucher = getVoucherFromFile(UUID.fromString(voucherId));
            optionalVoucher.ifPresent(opVoucher -> vouchers.add(opVoucher));
        }

        return vouchers;
    }

    @Override
    public Voucher registerVoucher(Voucher voucher) {
        UUID voucherId = voucher.getVoucherId();
        long voucherValue = voucher.getValue();
        String voucherType = voucher.getClass().getSimpleName().replaceAll("Voucher", "");

        wini.put(voucherId.toString(), VOUCHER_VALUE, voucherValue);
        wini.put(voucherId.toString(), VOUCHER_TYPE, voucherType);

        try {
            wini.store();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return voucher;
    }

    private Optional<Voucher> getVoucherFromFile(UUID voucherId) {
        String type = wini.get(voucherId.toString(), VOUCHER_TYPE);
        long value = wini.get(voucherId.toString(), VOUCHER_VALUE, long.class);
        Optional<VoucherList> voucherType = VoucherList.findVoucher(type);

        return voucherType.map(voucher -> voucher.createVoucher(voucherId, value));
    }

    void deleteAll() {
        wini.clear();
    }
}
