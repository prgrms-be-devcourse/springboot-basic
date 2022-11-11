package com.example.springbootbasic.console;

import com.example.springbootbasic.controller.request.RequestBody;
import com.example.springbootbasic.controller.response.ResponseBody;
import com.example.springbootbasic.dto.VoucherDto;
import com.example.springbootbasic.dto.controller.VoucherController;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ConsoleManager {

    private final VoucherController voucherController;

    public ConsoleManager(VoucherController voucherController) {
        this.voucherController = voucherController;
    }

    public ResponseBody<VoucherDto> saveVoucher(RequestBody<VoucherDto> request) {
        return voucherController.saveVoucher(request);
    }

    public ResponseBody<List<VoucherDto>> selectAllVouchers() {
        return voucherController.selectAllVouchers();
    }

}
