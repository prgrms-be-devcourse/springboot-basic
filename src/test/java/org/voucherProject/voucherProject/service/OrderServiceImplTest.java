package org.voucherProject.voucherProject.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.voucherProject.voucherProject.entity.order.Order;
import org.voucherProject.voucherProject.entity.voucher.FixedAmountVoucher;
import org.voucherProject.voucherProject.entity.voucher.Voucher;
import org.voucherProject.voucherProject.entity.voucher.VoucherType;
import org.voucherProject.voucherProject.repository.OrderRepositoryImpl;
import org.voucherProject.voucherProject.repository.VoucherRepositoryImpl;

import java.util.List;
import java.util.UUID;


@SpringBootTest
class OrderServiceImplTest {

    @Autowired
    OrderRepositoryImpl orderRepository;
    @Autowired
    VoucherServiceImpl voucherService;
    @Autowired
    VoucherRepositoryImpl voucherRepository;
    @Autowired
    OrderService orderService;

    @Test
    public void createOrderTest() throws Exception {
        UUID voucherId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        Voucher fixedAmountVoucher = new FixedAmountVoucher(voucherId, 2, VoucherType.FIXED);
        System.out.println("fixedAmountVoucher = " + fixedAmountVoucher);

        voucherRepository.save(fixedAmountVoucher);
        voucherRepository.save(new FixedAmountVoucher(UUID.randomUUID(), 2, VoucherType.FIXED));
        voucherRepository.save(new FixedAmountVoucher(UUID.randomUUID(), 3, VoucherType.PERCENT));
        voucherRepository.save(new FixedAmountVoucher(UUID.randomUUID(), 4, VoucherType.PERCENT));
        voucherRepository.save(new FixedAmountVoucher(UUID.randomUUID(), 5, VoucherType.FIXED));
        voucherRepository.save(new FixedAmountVoucher(UUID.randomUUID(), 6, VoucherType.FIXED));
        Order order = orderService.createOrder(userId, null, voucherId);

        System.out.println("fixedAmountVoucher = " + fixedAmountVoucher);

        List<Voucher> all = voucherRepository.findAll();
        for (Voucher voucher : all) {
            System.out.println("voucher = " + voucher);
        }


    }

}