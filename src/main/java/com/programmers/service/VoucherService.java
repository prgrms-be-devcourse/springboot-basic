package com.programmers.service;

import com.programmers.domain.Voucher;
import com.programmers.io.Console;
import com.programmers.repository.MemoryVoucherRepository;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {

    private MemoryVoucherRepository memoryVoucherRepository = new MemoryVoucherRepository();
    private Console console = new Console();

    public void save(Voucher voucher) {
        memoryVoucherRepository.save(voucher);
    }
}
