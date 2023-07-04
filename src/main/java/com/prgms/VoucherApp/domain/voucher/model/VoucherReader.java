package com.prgms.VoucherApp.domain.voucher.model;

import com.prgms.VoucherApp.domain.voucher.Voucher;
import com.prgms.VoucherApp.domain.voucher.dto.VoucherResDto;
import com.prgms.VoucherApp.domain.voucher.storage.VoucherStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class VoucherReader {
    private static final Logger log = LoggerFactory.getLogger(VoucherReader.class);
    private final VoucherStorage voucherStorage;

    public VoucherReader(VoucherStorage voucherStorage) {
        this.voucherStorage = voucherStorage;
    }

    public Voucher readOne(UUID voucherId) {
        return voucherStorage.findByVoucherId(voucherId)
            .orElseThrow(() -> {
                log.warn("Voucher Id {} does not exist", voucherId);
                return new NoSuchElementException("Voucher Id + " + voucherId + " does not exist");
            });
    }

    public List<VoucherResDto> readVoucherList() {
        List<Voucher> vouchers = voucherStorage.findAll();

        List<VoucherResDto> voucherResDtos = vouchers.stream()
            .map(VoucherResDto::new)
            .toList();
        return voucherResDtos;
    }
}
