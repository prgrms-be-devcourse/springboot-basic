package org.prgrms.kdtspringdemo.customer;

import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void printAll() {
        Stream<Customer> allCustomer = customerRepository.findAll();
        allCustomer.forEach(System.out::println);
    }

    public void printBlacklist() {
        Stream<Customer> allCustomer = customerRepository.findBlacklist();
        allCustomer.forEach(System.out::println);
    }

//    public Order createOrder(UUID customerId , List<OrderItem> orderItems) {
//        var order = new Order(UUID.randomUUID(), customerId, orderItems);
//        return orderRepository.insert(order);
//    }
//
//    public Order createOrder(UUID customerId , List<OrderItem> orderItems, UUID voucherId) {
//        var voucher = voucherService.getVoucher(voucherId);
//        var order = new Order(UUID.randomUUID(), customerId, orderItems, voucher);
//        orderRepository.insert(order);
//        voucherService.useVourcher(voucher);
//        return order;
//    }
}
