package org.programmers.kdt.customer.service;

import org.programmers.kdt.customer.Customer;
import org.programmers.kdt.voucher.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    // 회원 가입(등록)
    default Customer signUp(String name, String email) {
        return signUp(UUID.randomUUID(), name, email);
    };
    Customer signUp(UUID customerId, String name, String email);

    // 회원 검색
    Optional<Customer> findCustomerById(UUID customerId);
    List<Customer> findCustomersByName(String name);
    Optional<Customer> findCustomerByEmail(String email);
    List<Customer> findAll();
    List<Customer> findAllBlacklistCustomer();

    // 블랙리스트 등록
    Customer addToBlacklist(Customer customer);

    // 회원 탈퇴
    default void removeCustomer(Customer customer) {
        removeCustomer(customer.getCustomerId());
    }
    void removeCustomer(UUID customerId);

    // 블랙리스트 멤버십 테스트
    boolean isOnBlacklist(Customer customer);

    // 회원 정보 출력
    String getPrintFormat(Customer customer);

    // 회원 바우처 관련 기능
    boolean addVoucherToCustomer(Customer customer, Voucher voucher);

    default void removeVoucherFromCustomer(Customer customer, Voucher voucher) {
        removeVoucherFromCustomer(customer, voucher.getVoucherId());
    }
    void removeVoucherFromCustomer(Customer customer, UUID voucherId);

    default Optional<Customer> findByVoucher(Voucher voucher) {
        return findByVoucher(voucher.getVoucherId());
    }
    Optional<Customer> findByVoucher(UUID voucherId);

    List<Voucher> getAllVoucherOf(Customer customer);

}
