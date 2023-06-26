package com.programmers.service;

import com.programmers.domain.Voucher;
import com.programmers.io.Console;
import com.programmers.repository.MemoryVoucherRepository;
import com.programmers.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {

    private static final Logger log = LoggerFactory.getLogger(MemoryVoucherRepository.class);

    private final VoucherRepository voucherRepository;
    private final Console console;

    public VoucherService(VoucherRepository voucherRepository, Console console) {
        this.voucherRepository = voucherRepository;
        this.console = console;
    }

    public void save(Voucher voucher) {
        voucherRepository.save(voucher);
        log.info("The voucher has been saved. voucher name = {}", voucher.getVoucherName());
    }

    public List<Voucher> findAll() {
        List<Voucher> vouchers = voucherRepository.findAll();
        console.printVouchers(vouchers);
        log.info("The voucher list has been printed.");

        return vouchers;
    }
}
