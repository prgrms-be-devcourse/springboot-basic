package com.blessing333.springbasic.voucher.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("vouchers")
@Profile("web")
@Slf4j
public class WebVoucherController {
    @GetMapping("/")
    public String get(){
        return "voucher/vouchers";
    }

    @GetMapping("/registry-form")
    public String create(){
        return "voucher/voucher-registry";
    }


}
