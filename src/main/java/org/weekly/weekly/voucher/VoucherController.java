package org.weekly.weekly.voucher;

import org.springframework.stereotype.Controller;
import org.weekly.weekly.ui.CommandLineApplication;
import org.weekly.weekly.util.DiscountMap;
import org.weekly.weekly.util.VoucherMenu;
import org.weekly.weekly.voucher.domain.Discount;
import org.weekly.weekly.voucher.dto.VoucherDto;
import org.weekly.weekly.voucher.model.ListResponse;
import org.weekly.weekly.voucher.service.VoucherService;

@Controller
public class VoucherController {
    private final VoucherService voucherService;
    private final CommandLineApplication commandLineApplication;

    public VoucherController(VoucherService voucherService, CommandLineApplication commandLineApplication) {
        this.voucherService = voucherService;
        this.commandLineApplication = commandLineApplication;
    }

    public void start() {
        boolean isStop = false;

        while(!isStop) {
            try {
                isStop = selectMenu(this.commandLineApplication.readMenu());
            } catch (RuntimeException runtimeException) {
                this.commandLineApplication.printErrorMsg(runtimeException.getMessage());
            }
        }
    }

    private boolean selectMenu(VoucherMenu voucherMenu) {
        if (VoucherMenu.CREATE.equals(voucherMenu)) {
            createVoucher();
            return false;
        }

        if (VoucherMenu.LIST.equals(voucherMenu)) {
            getList();
            return false;
        }

        return true;
    }

    private void createVoucher() {
        Discount discount = this.commandLineApplication.readDiscount();
        VoucherDto voucherDto = this.commandLineApplication.readVoucher(discount);

        this.voucherService.insertVoucher(voucherDto, discount);
    }

    private void getList() {
        ListResponse response = this.voucherService.getVouchers();

        this.commandLineApplication.printAllList(response);
    }




}
