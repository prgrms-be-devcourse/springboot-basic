package org.prgrms.kdt.kdtspringorder;

import java.util.Optional;
import java.util.UUID;

/**
 * 각 객체의 생성을 담당함
 * 각 객체의 의존관계 맺기를 담당함
 */
public class OrderContext {

    public VoucherRepository voucherRepository() {
        return
        new VoucherRepository() {
            @Override
            public Optional<Voucher> findById(UUID voucherId) {
                return Optional.empty();
            }
        };
    }

    public OrderRepositiry orderRepositiry() {
        return new OrderRepositiry() {
            @Override
            public void insert(Order order) {

            }
        };
    }

    public VoucherService voucherService() {
        return new VoucherService(voucherRepository());
    }

    public OrderService orderService() {
        return new OrderService(voucherService(), orderRepositiry());
    }

}
