package org.prgrms.kdt.domain.voucher.repository;

import org.prgrms.kdt.domain.voucher.model.FixedAmountVoucher;
import org.prgrms.kdt.domain.voucher.model.PercentDiscountVoucher;
import org.prgrms.kdt.domain.voucher.model.Voucher;
import org.prgrms.kdt.domain.voucher.types.VoucherType;
import org.prgrms.kdt.util.CsvUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Profile("dev")
public class FileVoucherRepository implements VoucherRepository {
    @Value("${csv.voucher.path}")
    private String csvPath;
    @Value("${csv.voucher.file-name}")
    private String fileName;
    private static final int TYPE_INDEX = 0;
    private static final int ID_INDEX = 1;
    private static final int DISCOUNT_INDEX = 2;

    @Override
    public UUID save(Voucher voucher) {
        VoucherType voucherType = voucher.getVoucherType();
        String data = createCsvData(voucher, voucherType);
        CsvUtils.writeCsv(csvPath, fileName, data);
        return voucher.getVoucherId();
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        List<Voucher> vouchers = parseCsvToList(CsvUtils.readCsv(csvPath, fileName));
        return vouchers.stream()
                .filter(voucher -> voucher.getVoucherId().equals(voucherId))
                .findAny();
    }

    @Override
    public List<Voucher> findAll() {
        List<List<String>> csvData = CsvUtils.readCsv(csvPath, fileName);
        return parseCsvToList(csvData);
    }

    private String createCsvData(Voucher voucher, VoucherType voucherType) {
        StringBuilder data = new StringBuilder(VoucherType.getValue(voucherType))
                .append(",")
                .append(voucher.getVoucherId().toString())
                .append(",")
                .append(voucher.getDiscountValue());
        return data.toString();
    }

    private List<Voucher> parseCsvToList(List<List<String>> csvData) {
        List<Voucher> vouchers = new ArrayList<>();
        for (List<String> row : csvData) {
            VoucherType voucherType = VoucherType.findVoucherType(row.get(TYPE_INDEX));
            UUID voucherId = UUID.fromString(row.get(ID_INDEX));
            String discount = row.get(DISCOUNT_INDEX);
            if(voucherType == VoucherType.FIXED_AMOUNT){
                vouchers.add(new FixedAmountVoucher(voucherId, Long.parseLong(discount)));
            } else if(voucherType == VoucherType.PERCENT_DISCOUNT) {
                vouchers.add(new PercentDiscountVoucher(voucherId, Long.parseLong(discount)));
            }
        }
        return vouchers;
    }
}
