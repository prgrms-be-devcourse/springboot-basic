package com.example.voucherproject.voucher.repository;

import com.example.voucherproject.voucher.enums.VoucherType;
import com.example.voucherproject.common.file.MyReader;
import com.example.voucherproject.common.file.MyWriter;
import com.example.voucherproject.voucher.domain.Voucher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Slf4j
@RequiredArgsConstructor
public class VoucherFileRepository implements VoucherRepository{

    private final MyReader reader;
    private final MyWriter writer;

    @Value(value = "${kdt.path.voucher}")
    private String VOUCHER_LIST_PATH;

    @Override
    public List<Voucher> findAll() {
        List<Voucher> vouchers = new ArrayList<>();
        var readVouchers = reader.readLists(VOUCHER_LIST_PATH);

        readVouchers.forEach(voucher ->{
            var voucherId = UUID.fromString(voucher.get(0));
            var voucherType = VoucherType.valueOf(voucher.get(1));
            var amount = Long.valueOf(voucher.get(2));
            var createdAt = LocalDateTime.parse(voucher.get(3), DateTimeFormatter.ISO_LOCAL_DATE_TIME).truncatedTo(ChronoUnit.MILLIS);
            vouchers.add(new Voucher(voucherId, voucherType, amount,createdAt));

        });
        return vouchers;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.empty();
    }

    @Override
    public int deleteAll() {
        return 0;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public Voucher insert(Voucher voucher) {
        return writer.writeVoucher(voucher, VOUCHER_LIST_PATH);
    }

    @Override
    public List<Voucher> findHavingTypeAll(VoucherType type) {
        return null;
    }
}


