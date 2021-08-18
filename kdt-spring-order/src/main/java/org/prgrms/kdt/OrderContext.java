package org.prgrms.kdt;

import java.util.Optional;
import java.util.UUID;

/*
* 레포지토리 생성에 대한 책임을 가지고 있음.
* 각 서비스들이 어떤 레포지토리를 사용하는지 관계에 대한 책임도 가지고 있음.
* */
public class OrderContext {
    public VoucherRepository voucherRepository() {
        return new VoucherRepository() {
            @Override
            public Optional<Voucher> findById(UUID voucherId) {
                return Optional.empty();
            }
        };
    }

    public OrderRepository orderRepository() {
        return new OrderRepository() {
            @Override
            public void insert(Order order) {

            }
        };
    }

    public VoucherService voucherService() {
        return new VoucherService(voucherRepository());
    }

    public OrderService orderService() {
        return new OrderService(voucherService(), orderRepository());
    }
}
