package org.prgrms.voucher.repository;

import org.prgrms.voucher.models.Voucher;
import org.prgrms.voucher.models.VoucherType;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Profile("file")
public class VoucherFileRepository implements VoucherRepository {

    @Override
    public Voucher save(Voucher voucher) {

        if (voucher == null) {
            throw new IllegalArgumentException("Voucher is null");
        }

        if (voucher.getVoucherId() == null) {
            voucher = setIdVoucher(voucher);
        }

        String saveFile = voucher.getVoucherId() + ", "
                + voucher.getDiscountValue() + ", "
                + voucher.getVoucherType();
        FileUtils.saveEntity(saveFile);

        return voucher;
    }

    @Override
    public List<Voucher> findAll() {

        return getVouchers();
    }

    private List<Voucher> getVouchers() {

        List<Voucher> vouchers = new ArrayList<>();
        List<String> readLine = FileUtils.readEntity();

        readLine.stream()
                .map(v -> v.split(", "))
                .forEach(v -> vouchers.add(getVoucher(v)));

        return vouchers;
    }

    private Voucher getVoucher(String[] lineSplit) {

        Long voucherId = Long.parseLong(lineSplit[0]);
        long discountValue = Long.parseLong(lineSplit[1]);
        VoucherType voucherType = VoucherType.valueOf(lineSplit[2]);

        return voucherType.createVoucher(voucherId, discountValue, voucherType);
    }

    private Voucher setIdVoucher(Voucher voucher) {

        voucher = voucher.getVoucherType().createVoucher(
                IdGenerator.idGenerate(),
                voucher.getDiscountValue(),
                voucher.getVoucherType()
        );

        return voucher;
    }
}