package org.prgrms.voucherapplication.repository;

import org.prgrms.voucherapplication.entity.FixedAmountVoucher;
import org.prgrms.voucherapplication.entity.PercentDiscountVoucher;
import org.prgrms.voucherapplication.entity.Voucher;
import org.prgrms.voucherapplication.view.io.VoucherType;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class FileVoucherRepository implements VoucherRepository {
    private static final String PATH = "data/voucher_list.csv";

    @Override
    public Optional<Voucher> findById(UUID voucherId) throws IOException {
        return Optional.of(
                findAll().stream()
                        .filter(voucher -> voucher.getVoucherId().equals(voucherId))
                        .findFirst()
                        .orElse(null)
        );
    }

    @Override
    public Voucher insert(Voucher voucher) throws IOException {
        File file = new File(PATH);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
        String voucherType;
        if (voucher instanceof FixedAmountVoucher) {
            voucherType = VoucherType.FixedAmount.name();
        } else {
            voucherType = VoucherType.PercentDiscount.name();
        }

        writer.write(MessageFormat.format("{0},{1},{2}", voucherType, voucher.getVoucherId(), voucher.getDiscountValue()));
        writer.write(System.lineSeparator());
        writer.flush();
        writer.close();

        return voucher;
    }

    @Override
    public List<Voucher> findAll() throws IOException {
        File file = new File(PATH);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<Voucher> vouchers = new ArrayList<>();

        String line = "";
        while ((line = reader.readLine()) != null) {
            String[] voucherInfo = line.split(",");
            vouchers.add(restoreVoucher(voucherInfo[0], voucherInfo[1], voucherInfo[2]));
        }
        reader.close();

        return vouchers;
    }

    private Voucher restoreVoucher(String type, String id, String discountAmount) {
        if (type.equals(VoucherType.FixedAmount.name())) {
            return new FixedAmountVoucher(UUID.fromString(id), Long.valueOf(discountAmount));
        } else {
            return new PercentDiscountVoucher(UUID.fromString(id), Long.valueOf(discountAmount));
        }
    }
}
