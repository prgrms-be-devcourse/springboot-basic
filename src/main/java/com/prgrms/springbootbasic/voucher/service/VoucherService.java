package com.prgrms.springbootbasic.voucher.service;

import com.prgrms.springbootbasic.voucher.VoucherType;
import com.prgrms.springbootbasic.voucher.controller.VoucherForm;
import com.prgrms.springbootbasic.voucher.domain.Voucher;
import com.prgrms.springbootbasic.voucher.factory.VoucherFactory;
import com.prgrms.springbootbasic.voucher.storage.VoucherStorage;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.prgrms.springbootbasic.voucher.controller.VoucherResponse.*;

@Profile("prod | test")
@Transactional
@Service
public class VoucherService {
    private final Map<VoucherType, VoucherFactory> voucherFactoryMap = new EnumMap<>(VoucherType.class);

    private final VoucherStorage voucherStorage;

    public VoucherService(VoucherStorage voucherStorage, List<VoucherFactory> voucherFactories) {
        this.voucherStorage = voucherStorage;
        voucherFactories.forEach(factory -> this.voucherFactoryMap.put(factory.getType(), factory));
    }

    public void create(VoucherForm voucherForm) {
        VoucherType voucherType = VoucherType.fromInputValue(voucherForm.getVoucherType());
        VoucherFactory voucherFactory = voucherFactoryMap.get(voucherType);
        Voucher voucher = voucherFactory.createVoucher(voucherForm.getDiscountAmount());
        voucherStorage.save(voucher);
    }

    @Transactional(readOnly = true)
    public List<VoucherShortcut> list() {
        List<Voucher> vouchers = voucherStorage.findAll();
        return vouchers.stream()
                .map(VoucherShortcut::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public VoucherDetails findById(UUID id){
        Optional<Voucher> foundById = voucherStorage.findById(id);
        return VoucherDetails.from(foundById.get());
    }

    public void delete(String id) {
        voucherStorage.delete(UUID.fromString(id));
    }
}
