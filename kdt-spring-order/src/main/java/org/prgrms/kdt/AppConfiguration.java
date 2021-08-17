package org.prgrms.kdt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Configuration
public class AppConfiguration {
    @Bean
    public VoucherRepository voucherRepository(){
        return new VoucherRepository() {
            public final List<Voucher> voucherList = new ArrayList<>();

            @Override
            public Optional<Voucher> findById(UUID voucherId) {
                return Optional.empty();
            }

            @Override
            public List<Voucher> getVoucherList() {
                return voucherList;
            }

            @Override
            public void insert(Voucher voucher) {
                voucherList.add(voucher);
            }
        };
    }

    @Bean
    public OrderRepository orderRepository(){
        return new OrderRepository() {
            @Override
            public void insert(Order order) {

            }
        };
    }

    @Bean
    public VoucherService voucherService(VoucherRepository voucherRepository){
        return new VoucherService(voucherRepository);
    }

    @Bean
    public OrderService orderService(VoucherService voucherService, OrderRepository orderRepository){
        return new OrderService(voucherService, orderRepository);
    }
}
