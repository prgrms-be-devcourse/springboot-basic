package org.programmers;

import org.programmers.order.Order;
import org.programmers.order.OrderRepository;
import org.programmers.order.OrderService;
import org.programmers.voucher.Voucher;
import org.programmers.voucher.VoucherRepository;
import org.programmers.voucher.VoucherService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Configuration
public class AppConfiguration {

    @Bean
    public VoucherRepository voucherRepository() {
        return new VoucherRepository() {
            ArrayList<Optional<Voucher>> voucherList = new ArrayList<>();

            @Override
            public Optional<Voucher> findById(UUID voucherId) {
                return Optional.empty();
            }

            @Override
            public void insert(Voucher voucher) {
                voucherList.add(Optional.of(voucher));
            }

            public void getVoucherList() {
                voucherList.forEach(voucher -> System.out.println(voucher.toString()));
            }
        };
    }

    @Bean
    public OrderRepository orderRepository() {
        return new OrderRepository() {
            @Override
            public void insert(Order order) {

            }
        };
    }

    @Bean
    public VoucherService voucherService() {
        return new VoucherService(voucherRepository());
    }

    @Bean
    public OrderService orderService() {
        return new OrderService(voucherService(), orderRepository());
    }

}
