package com.example.voucherproject.voucher.repository;

import com.example.voucherproject.common.enums.VoucherType;
import com.example.voucherproject.common.file.MyReader;
import com.example.voucherproject.common.file.MyWriter;
import com.example.voucherproject.voucher.domain.FixedDiscountVoucher;
import com.example.voucherproject.voucher.domain.PercentDiscountVoucher;
import com.example.voucherproject.voucher.domain.Voucher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
public class VoucherFileRpository implements VoucherRepository{

    private final MyReader reader;
    private final MyWriter writer;
    private final String VOUCHER_LIST_PATH = "voucher_list.csv";

    @Override
    public List<Voucher> getList() {
        List<Voucher> vouchers = new ArrayList<>();
        var readVouchers = reader.readLists(VOUCHER_LIST_PATH);

        readVouchers.forEach(voucher ->{
            var voucherId = UUID.fromString(voucher.get(0));
            var voucherType = VoucherType.valueOf(voucher.get(1));
            switch(voucherType){
                case FIXED:
                    vouchers.add(new FixedDiscountVoucher(voucherId, voucherType));
                    break;
                case PERCENT:
                    vouchers.add(new PercentDiscountVoucher(voucherId, voucherType));
                    break;
                default:
                    log.error("정의되지 않은 바우처 타입");
                    break;
            }
        });
        return vouchers;
    }

    @Override
    public Voucher save(Voucher voucher) {
        return writer.writeVoucher(voucher, VOUCHER_LIST_PATH);
    }
}
