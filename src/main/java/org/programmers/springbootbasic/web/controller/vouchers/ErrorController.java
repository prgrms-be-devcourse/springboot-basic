package org.programmers.springbootbasic.web.controller.vouchers;

import org.programmers.springbootbasic.voucher.domain.IllegalVoucherTypeException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping("/illegal")
    public String illegal() {
        throw new IllegalArgumentException();
    }
    @GetMapping("/voucherex")
    public String voucherex() {
        throw new IllegalVoucherTypeException("");
    }
    @GetMapping("/other")
    public String other() {
        throw new NoSuchBeanDefinitionException("");
    }
}
