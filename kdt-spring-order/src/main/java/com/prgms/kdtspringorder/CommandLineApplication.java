package com.prgms.kdtspringorder;

import com.prgms.kdtspringorder.adapter.controller.VoucherController;

public class CommandLineApplication {

    public static void main(String[] args) {
        VoucherController voucherController = new VoucherController();
        voucherController.manageVouchers();
    }
}
