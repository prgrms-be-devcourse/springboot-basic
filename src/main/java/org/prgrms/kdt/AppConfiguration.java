package org.prgrms.kdt;

import org.prgrms.kdt.engine.order.Order;
import org.prgrms.kdt.engine.order.OrderRepository;
import org.prgrms.kdt.engine.order.OrderService;
import org.prgrms.kdt.engine.voucher.Voucher;
import org.prgrms.kdt.engine.voucher.VoucherRepository;
import org.prgrms.kdt.engine.voucher.VoucherService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Configuration
@ComponentScan(basePackages = {"org.prgrms.kdt.engine.order", "org.prgrms.kdt.engine.voucher"})
public class AppConfiguration {

}
