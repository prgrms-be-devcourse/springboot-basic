package com.programmers.service;

import com.programmers.domain.Voucher;
import com.programmers.io.Console;
import com.programmers.repository.MemoryVoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {

    private static final Logger log = LoggerFactory.getLogger(MemoryVoucherRepository.class);

    private final MemoryVoucherRepository memoryVoucherRepository;
    private final Console console;

    public VoucherService(MemoryVoucherRepository memoryVoucherRepository, Console console) {
        this.memoryVoucherRepository = memoryVoucherRepository;
        this.console = console;
    }

    public void save(Voucher voucher) {
        memoryVoucherRepository.save(voucher);
        log.info("The voucher has been saved. voucher name = {}", voucher.getVoucherName());
    }

    public List<Voucher> findAll() {
        List<Voucher> vouchers = memoryVoucherRepository.findAll();
        console.printVouchers(vouchers);
        log.info("The voucher list has been printed.");

        return vouchers;
    }
}
