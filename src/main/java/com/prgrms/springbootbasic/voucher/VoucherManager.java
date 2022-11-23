package com.prgrms.springbootbasic.voucher;

import com.prgrms.springbootbasic.voucher.domain.Voucher;
import com.prgrms.springbootbasic.voucher.factory.VoucherFactory;
import com.prgrms.springbootbasic.voucher.storage.VoucherStorage;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class VoucherManager {
    private final Map<VoucherType, VoucherFactory> voucherFactoryMap = new EnumMap<>(VoucherType.class);

    private final VoucherStorage voucherStorage;

    public VoucherManager(VoucherStorage voucherStorage, List<VoucherFactory> voucherFactories) {
        this.voucherStorage = voucherStorage;
        voucherFactories.forEach(factory -> this.voucherFactoryMap.put(factory.getType(), factory));
    }

    public void create(VoucherType voucherType, String amountInput) {
        VoucherFactory voucherFactory = voucherFactoryMap.get(voucherType);
        Voucher voucher = voucherFactory.generateVoucher(amountInput);
        voucherStorage.save(voucher);
    }

    public List<Voucher> list() {
        return voucherStorage.findAll();
    }
}
