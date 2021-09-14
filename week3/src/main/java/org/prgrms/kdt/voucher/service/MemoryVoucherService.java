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
    public void create(Voucher voucher)  {
        voucherRepository.insert(voucher);
    }

    @Override
    public List<Voucher> list()  {
         return voucherRepository.findAll();
    }
}