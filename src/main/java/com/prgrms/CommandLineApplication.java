package com.prgrms;

import com.prgrms.controller.VoucherController;
import com.prgrms.io.Menu;
import com.prgrms.io.ViewManager;
import com.prgrms.model.dto.VoucherRequest;
import com.prgrms.model.dto.VoucherResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommandLineApplication implements CommandLineRunner {
    private final VoucherController voucherController;
    private final ViewManager viewManager;

    public CommandLineApplication(VoucherController voucherController, ViewManager viewManager){
        this.voucherController = voucherController;
        this.viewManager = viewManager;
    }
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(String... args) {
        boolean isRunning = true;

        while (isRunning) {
            Menu menu = viewManager.guideStartVoucher();
            try {
                switch (menu) {
                    case EXIT -> {
                        isRunning = false;
                        viewManager.guideClose();
                    }
                    case CREATE -> {
                        VoucherRequest voucherRequest = viewManager.guideCreateVoucher();
                        voucherController.createVoucher(voucherRequest);
                    }
                    case LIST -> {
                        List<VoucherResponse> vouchers = voucherController.listVoucher();
                        viewManager.viewVoucherList(vouchers);
                    }
                }
            } catch (IllegalArgumentException e) {
                logger.error("사용자의 잘못된 입력이 발생하였습니다.");
                viewManager.viewError(e.getMessage());
            }
        }
    }
}
