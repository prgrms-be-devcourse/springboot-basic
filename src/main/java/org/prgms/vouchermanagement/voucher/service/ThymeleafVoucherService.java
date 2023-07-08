package org.prgms.vouchermanagement.voucher.service;

import lombok.RequiredArgsConstructor;
import org.prgms.vouchermanagement.voucher.domain.entity.Voucher;
import org.prgms.vouchermanagement.voucher.domain.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ThymeleafVoucherService {

    private final VoucherRepository voucherRepository;

    public List<Voucher> getVouchers() {
        return voucherRepository.getVoucherList();
    }

}
