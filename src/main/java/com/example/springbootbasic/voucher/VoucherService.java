package com.example.springbootbasic.voucher;

import com.example.springbootbasic.io.Console;
import com.example.springbootbasic.io.Output;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;
    private final Output output;

    public VoucherService(VoucherRepository voucherRepository, Output output) {
        this.voucherRepository = voucherRepository;
        this.output = output;
    }

    public void commandExit() {
        Console.print("Terminate Program...");
    }

    public void commandCreate() {
        
    }

    public void commandList() {
        List<Voucher> all = voucherRepository.findAll();
        output.printAllVouchers(all);
    }
}
