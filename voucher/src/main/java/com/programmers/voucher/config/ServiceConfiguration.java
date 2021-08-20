package com.programmers.voucher.config;

import org.springframework.context.annotation.ComponentScan;
import com.programmers.voucher.entity.voucher.factory.FixedAmountVoucherFactory;
import com.programmers.voucher.entity.voucher.factory.PercentDiscountVoucherFactory;
import com.programmers.voucher.entity.voucher.factory.VoucherFactory;
import com.programmers.voucher.repository.voucher.InMemoryVoucherRepository;
import com.programmers.voucher.repository.voucher.VoucherRepository;
import com.programmers.voucher.service.voucher.BasicVoucherService;
import com.programmers.voucher.service.voucher.VoucherService;
import org.springframework.context.annotation.Bean;

@ComponentScan(basePackages = "com.programmers.voucher")
public class ServiceConfiguration {

}
