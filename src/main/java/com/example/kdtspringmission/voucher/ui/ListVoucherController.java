package com.example.kdtspringmission.voucher.ui;

import com.example.kdtspringmission.Handler;
import com.example.kdtspringmission.ModelAndView;
import com.example.kdtspringmission.voucher.domain.Voucher;
import com.example.kdtspringmission.voucher.domain.VoucherRepository;
import java.util.List;
import java.util.Scanner;
import org.springframework.stereotype.Controller;

@Controller
public class ListVoucherController implements Handler {

    private final VoucherRepository voucherRepository;

    public ListVoucherController(
        VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Override
    public ModelAndView process(Scanner scanner) {
        ModelAndView mv = new ModelAndView("Voucher List");
        List<Voucher> vouchers = voucherRepository.findAll();
        mv.getModel().put("vouchers", vouchers);
        return mv;
    }
}
