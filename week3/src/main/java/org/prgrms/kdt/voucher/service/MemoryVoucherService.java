package org.prgrms.kdt.voucher.service;

import org.prgrms.kdt.voucher.model.*;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class MemoryVoucherService implements VoucherService {

    private final VoucherRepository voucherRepository;

    public MemoryVoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Override
    public void create(Voucher voucher) throws IOException {
        voucherRepository.insert(voucher);
    }

    @Override
    public List<Voucher> list() throws IOException {
         return voucherRepository.findAll();
    }
}