package org.prgrms.kdt.config;

import org.prgrms.kdt.order.Order;
import org.prgrms.kdt.order.OrderRepository;
import org.prgrms.kdt.order.OrderService;
import org.prgrms.kdt.voucher.repository.MemoryVoucherRepository;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"org.prgrms.kdt.voucher"})
public class AppConfiguration {
}
