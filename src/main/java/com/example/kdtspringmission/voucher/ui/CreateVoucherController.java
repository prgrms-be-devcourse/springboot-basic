package com.example.kdtspringmission.voucher.ui;

import com.example.kdtspringmission.Handler;
import com.example.kdtspringmission.ModelAndView;
import com.example.kdtspringmission.voucher.application.VoucherService;
import com.example.kdtspringmission.voucher.domain.Voucher;
import com.example.kdtspringmission.voucher.domain.VoucherRepository;
import java.util.Scanner;
import org.springframework.stereotype.Controller;

@Controller
public class CreateVoucherController implements Handler {

    private final VoucherService voucherService;
    private final VoucherRepository voucherRepository;

    public CreateVoucherController(
        VoucherService voucherService,
        VoucherRepository voucherRepository) {
        this.voucherService = voucherService;
        this.voucherRepository = voucherRepository;
    }


    @Override
    public ModelAndView process(Scanner scanner) {
        System.out.println("Which type of voucher? 1.FixedAmountVoucher, 2.RateAmountVoucher");
        String typeName = scanner.nextLine();

        Voucher voucher = voucherService.create(typeName);
        voucherRepository.insert(voucher);

        ModelAndView mv = new ModelAndView("Successfully created");
        mv.getModel().put("voucher", voucher);
        return mv;
    }
}
