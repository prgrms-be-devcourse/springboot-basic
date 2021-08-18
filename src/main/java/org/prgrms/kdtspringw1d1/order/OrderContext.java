package org.prgrms.kdtspringw1d1.order;

import org.prgrms.kdtspringw1d1.voucher.Voucher;
import org.prgrms.kdtspringw1d1.voucher.VoucherMemoryRepository;
import org.prgrms.kdtspringw1d1.voucher.VoucherRepository;
import org.prgrms.kdtspringw1d1.voucher.VoucherService;

import java.util.Optional;
import java.util.UUID;

public class OrderContext {

    public VoucherRepository voucherRepository() {
        return new VoucherMemoryRepository();
    }

    public OrderRepository orderRepository() {
        return new OrderRepository() {
            @Override
            public void save(Order order) {

            }

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
