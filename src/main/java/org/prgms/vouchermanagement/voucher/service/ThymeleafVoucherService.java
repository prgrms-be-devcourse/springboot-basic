package org.prgms.vouchermanagement.voucher.service;

import lombok.RequiredArgsConstructor;
import org.prgms.vouchermanagement.voucher.domain.entity.Voucher;
import org.prgms.vouchermanagement.voucher.domain.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ThymeleafVoucherService {

    private final VoucherRepository voucherRepository;

    public List<Voucher> getVouchers() {
        Map<UUID, Voucher> voucherList = voucherRepository.getVoucherList();
        return voucherList.values().stream().toList();
    }

}
