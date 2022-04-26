package org.prgrms.part1.engine.repository;

import org.prgrms.part1.engine.domain.Customer;
import org.prgrms.part1.engine.domain.Voucher;
import org.prgrms.part1.exception.VoucherException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    default List<Customer> findAll() {
        throw new VoucherException("미완성 기능입니다.");
    }

    default List<Customer> findBlackStatus(Boolean isBlack) {
        throw new VoucherException("미완성 기능입니다.");
    }

    default Optional<Customer> findById(UUID customerId) {
        throw new VoucherException("미완성 기능입니다.");
    }

    default Optional<Customer> findByName(String name) {
        throw new VoucherException("미완성 기능입니다.");
    }

    default Optional<Customer> findByEmail(String email) {
        throw new VoucherException("미완성 기능입니다.");
    }

    default Customer insert(Customer customer) {
        throw new VoucherException("미완성 기능입니다.");
    }

    default Customer update(Customer customer) {
        throw new VoucherException("미완성 기능입니다.");
    }

    default void deleteAll() {
        throw new VoucherException("미완성 기능입니다.");
    }

    default void deleteById(UUID customerId)  {
        throw new VoucherException("미완성 기능입니다.");
    }
}
