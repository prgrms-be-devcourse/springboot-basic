package org.prgrms.kdt.config;

import org.prgrms.kdt.KdtApplication;
import org.prgrms.kdt.domain.order.Order;
import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.repository.MemoryOrderRepository;
import org.prgrms.kdt.repository.MemoryVoucherRepository;
import org.prgrms.kdt.repository.OrderRepository;
import org.prgrms.kdt.repository.VoucherRepository;
import org.prgrms.kdt.service.OrderService;
import org.prgrms.kdt.service.VoucherService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;
import java.util.UUID;

@Configuration
@ComponentScan(basePackageClasses = {KdtApplication.class})
public class AppConfiguration {

}
