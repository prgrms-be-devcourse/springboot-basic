package org.prgrms.kdt.config;

import org.prgrms.kdt.order.repository.OrderRepository;
import org.prgrms.kdt.order.application.OrderService;
import org.prgrms.kdt.voucher.repository.MemoryRepository;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.prgrms.kdt.voucher.application.VoucherService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
@ComponentScan(basePackages = {"org.prgrms.kdt.voucher", "org.prgrms.kdt.order"})
public class AppConfiguration {

}
