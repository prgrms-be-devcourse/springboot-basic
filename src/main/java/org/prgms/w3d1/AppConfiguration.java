package org.prgms.w3d1;


import org.prgms.w3d1.model.order.Order;
import org.prgms.w3d1.model.order.OrderService;
import org.prgms.w3d1.model.voucher.Voucher;
import org.prgms.w3d1.model.voucher.VoucherService;
import org.prgms.w3d1.repository.OrderRepository;
import org.prgms.w3d1.repository.VoucherRepository;
import org.prgms.w3d1.repository.VoucherRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"org.prgms.w3d1.model", "org.prgms.w3d1.repository"})
public class AppConfiguration {

}
