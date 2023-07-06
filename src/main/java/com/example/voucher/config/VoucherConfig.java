package com.example.voucher.config;

import com.example.voucher.CommandHandler;
import com.example.voucher.repository.InMemoryVoucherRepository;
import com.example.voucher.repository.VoucherRepository;
import com.example.voucher.ui.Input;
import com.example.voucher.ui.Output;

public class VoucherConfig {
    public CommandHandler commandHandler() {
        return new CommandHandler(input(), output());
    }

    public VoucherRepository voucherRepository() {
        return inMemoryVoucherRepository();
    }

    private Input input() {
        return new Input();
    }

    private Output output() {
        return new Output();
    }

    private InMemoryVoucherRepository inMemoryVoucherRepository() {
        return new InMemoryVoucherRepository();
    }
}
