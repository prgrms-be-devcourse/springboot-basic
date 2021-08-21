package com.example.kdtspringmission;

import com.example.kdtspringmission.view.ConsoleInputView;
import com.example.kdtspringmission.view.ConsoleOutputView;
import com.example.kdtspringmission.view.InputView;
import com.example.kdtspringmission.view.OutputView;
import com.example.kdtspringmission.voucher.repository.MemoryVoucherRepository;
import com.example.kdtspringmission.voucher.repository.VoucherRepository;
import com.example.kdtspringmission.voucher.service.VoucherService;

public class AppConfig {

    public VoucherRepository voucherRepository() {
        return new MemoryVoucherRepository();
    }

    public InputView inputView() {
        return new ConsoleInputView();
    }

    public OutputView outputView() {
        return new ConsoleOutputView();
    }

    public VoucherService voucherService() {
        return new VoucherService(voucherRepository());
    }
}
