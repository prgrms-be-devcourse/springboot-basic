package com.programmers.service;

import com.programmers.domain.Voucher;
import com.programmers.io.Console;
import com.programmers.repository.MemoryVoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {

    private final MemoryVoucherRepository memoryVoucherRepository = new MemoryVoucherRepository();
    private final Console console = new Console();

    public void save(Voucher voucher) {
        memoryVoucherRepository.save(voucher);
    }

    public List<Voucher> findAll() {
        List<Voucher> vouchers = memoryVoucherRepository.findAll();

        console.printVouchers(vouchers);

        return vouchers;
    }
}
