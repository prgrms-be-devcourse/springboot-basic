package com.programmers.springvoucherservice.repository;

import com.programmers.springvoucherservice.domain.voucher.Voucher;
import com.programmers.springvoucherservice.domain.voucher.VoucherList;
import org.ini4j.Wini;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;

@Repository
public class FileVoucherRepository implements VoucherRepository{
    private Wini wini;
    private final String path = "./sample.ini";

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
        String type = wini.get(voucherId.toString(), "type");
        long value = wini.get(voucherId.toString(), "value", long.class);
        Optional<VoucherList> voucherList = VoucherList.findVoucher(type);

        System.out.println(MessageFormat.format("type =>{0}", type));
        System.out.println(MessageFormat.format("value =>{0}", value));
        return voucherList.map(voucher -> voucher.createVoucher(value));
    }

    @Override
    public List<Voucher> findAllVouchers() {
        List<Voucher> vouchers = new ArrayList<>();
        Set<String> voucherIdSet = wini.keySet();

        for (String voucherId : voucherIdSet) {
            String type = wini.get(voucherId, "type");
            long value = wini.get(voucherId, "value", long.class);

            Optional<VoucherList> voucherType = VoucherList.findVoucher(type);

            Optional<Voucher> optionalVoucher = voucherType.map(voucher -> voucher.createVoucher(value));

            optionalVoucher.ifPresent(opVoucher -> vouchers.add(opVoucher));

        }
        return vouchers;
    }

    @Override
    public UUID registerVoucher(Voucher voucher) {
        UUID voucherId = voucher.getVoucherId();
        long voucherValue = voucher.getValue();

        wini.put(voucherId.toString(), "value", voucherValue);
        wini.put(voucherId.toString(), "type", voucher.getClass().getSimpleName().replaceAll("Voucher", ""));

        try{
            wini.store();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return voucherId;
    }

}
