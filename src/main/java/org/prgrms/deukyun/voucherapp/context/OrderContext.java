package org.prgrms.deukyun.voucherapp.context;

import org.prgrms.deukyun.voucherapp.entity.Order;
import org.prgrms.deukyun.voucherapp.entity.Voucher;
import org.prgrms.deukyun.voucherapp.repository.OrderRepository;
import org.prgrms.deukyun.voucherapp.repository.VoucherRepository;
import org.prgrms.deukyun.voucherapp.service.OrderService;
import org.prgrms.deukyun.voucherapp.service.VoucherService;

import java.util.Optional;
import java.util.UUID;

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
