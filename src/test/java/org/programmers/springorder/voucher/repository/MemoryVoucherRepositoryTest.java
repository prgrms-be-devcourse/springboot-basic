package org.programmers.springorder.voucher.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.springorder.config.memory.MemoryConfig;
import org.programmers.springorder.customer.model.Customer;
import org.programmers.springorder.customer.model.CustomerType;
import org.programmers.springorder.customer.repository.CustomerRepository;
import org.programmers.springorder.customer.repository.JdbcCustomerRepository;
import org.programmers.springorder.voucher.model.Voucher;
import org.programmers.springorder.voucher.model.VoucherType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringJUnitConfig
class MemoryVoucherRepositoryTest {


    @Configuration
    @ComponentScan(basePackageClasses = MemoryConfig.class)
    static class Config {
    }

    @Autowired
    VoucherRepository voucherRepository;

    @Autowired
    CustomerRepository customerRepository;



    @AfterEach
    void clear(){
        ((MemoryVoucherRepository)voucherRepository).reset();
        ((JdbcCustomerRepository) customerRepository).clear();
    }

    @Test
    @DisplayName("voucher 생성 테스트")
    public void makeVoucherTest() {
        UUID uuid = UUID.randomUUID();
        Voucher voucher = Voucher.toVoucher(uuid, 100, VoucherType.FIXED);

        Voucher save = voucherRepository.save(voucher);
        Voucher foundVoucher = voucherRepository.findById(uuid).get();

        assertThat(voucher).isEqualTo(save);
        assertThat(voucher).isEqualTo(foundVoucher);
    }

    @Test
    @DisplayName("voucher 전체 조회 테스트")
    public void getAllVoucherTest() {
        Voucher voucher1 = Voucher.toVoucher(UUID.randomUUID(), 100, VoucherType.FIXED);
        Voucher voucher2 = Voucher.toVoucher(UUID.randomUUID(), 100, VoucherType.FIXED);
        Voucher voucher3 = Voucher.toVoucher(UUID.randomUUID(), 100, VoucherType.FIXED);

        Voucher save1 = voucherRepository.save(voucher1);
        Voucher save2 = voucherRepository.save(voucher2);
        Voucher save3 = voucherRepository.save(voucher3);

        List<Voucher> savedvoucherList = Arrays.asList(save1, save2, save3);
        List<Voucher> vouchers = voucherRepository.findAll();

        assertThat(vouchers).hasSize(3);
        assertThat(vouchers).containsAll(savedvoucherList);
    }

    @Test
    @DisplayName("voucher id로 조회 테스트")
    public void getVoucherByIdTest() {
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();
        UUID uuid3 = UUID.randomUUID();

        Voucher voucher1 = Voucher.toVoucher(uuid1, 100, VoucherType.FIXED);
        Voucher voucher2 = Voucher.toVoucher(uuid2, 100, VoucherType.FIXED);
        Voucher voucher3 = Voucher.toVoucher(uuid3, 100, VoucherType.FIXED);

        voucherRepository.save(voucher1);
        voucherRepository.save(voucher2);
        voucherRepository.save(voucher3);


        Voucher findVoucher1 = voucherRepository.findById(uuid1).get();
        Voucher findVoucher2 = voucherRepository.findById(uuid2).get();
        Voucher findVoucher3 = voucherRepository.findById(uuid3).get();

        assertThat(voucher1).isEqualTo(findVoucher1);
        assertThat(voucher2).isEqualTo(findVoucher2);
        assertThat(voucher3).isEqualTo(findVoucher3);
    }

    @Test
    @DisplayName("voucher를 고객에게 부여하는 테스트")
    public void setVoucherTest() {
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();
        UUID uuidCustomer = UUID.randomUUID();

        Voucher voucher1 = Voucher.toVoucher(uuid1, 100, VoucherType.FIXED);
        Voucher voucher2 = Voucher.toVoucher(uuid2, 100, VoucherType.FIXED);

        Customer customer = Customer.toCustomer(uuidCustomer, "owner", CustomerType.NORMAL);
        customerRepository.insert(customer);
        voucherRepository.save(voucher1);
        voucherRepository.save(voucher2);

        voucherRepository.updateVoucherOwner(voucher1, customer);


        Voucher findVoucher1 = voucherRepository.findById(uuid1).get();
        Voucher findVoucher2 = voucherRepository.findById(uuid2).get();


        assertThat(findVoucher1.getCustomerId()).isEqualTo(uuidCustomer);
        assertThat(findVoucher1.getCustomerId()).isEqualTo(customer.getCustomerId());
        assertThat(findVoucher2.getCustomerId()).isEqualTo(null);

    }

    @Test
    @DisplayName("고객 id로 그 고객이 소유한 voucher 조회하는 테스트")
    public void customerOwnedVoucherTest() {
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();
        UUID uuid3 = UUID.randomUUID();
        UUID uuidCustomer = UUID.randomUUID();

        Voucher voucher1 = Voucher.toVoucher(uuid1, 100, VoucherType.FIXED);
        Voucher voucher2 = Voucher.toVoucher(uuid2, 100, VoucherType.FIXED);
        Voucher voucher3 = Voucher.toVoucher(uuid3, 100, VoucherType.FIXED);

        Customer customer = Customer.toCustomer(uuidCustomer, "owner", CustomerType.NORMAL);
        customerRepository.insert(customer);
        voucherRepository.save(voucher1);
        voucherRepository.save(voucher2);
        voucherRepository.save(voucher3);

        Voucher updatedVoucher1 = voucherRepository.updateVoucherOwner(voucher1, customer);
        Voucher updatedVoucher2 = voucherRepository.updateVoucherOwner(voucher2, customer);
        List<Voucher> voucherWithCustomer = List.of(updatedVoucher1, updatedVoucher2);
        Customer findCustomer = customerRepository.findByID(uuidCustomer).get();

        List<Voucher> vouchersWithFindCustomer = voucherRepository.findAllByCustomerId(findCustomer);

        assertThat(vouchersWithFindCustomer).hasSize(2);
        assertThat(vouchersWithFindCustomer).containsAll(voucherWithCustomer);
    }

    @Test
    @DisplayName("id로 voucher 삭제 테스트")
    public void deleteVoucher() {
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();
        UUID uuid3 = UUID.randomUUID();

        Voucher voucher1 = Voucher.toVoucher(uuid1, 100, VoucherType.FIXED);
        Voucher voucher2 = Voucher.toVoucher(uuid2, 100, VoucherType.FIXED);
        Voucher voucher3 = Voucher.toVoucher(uuid3, 100, VoucherType.FIXED);

        voucherRepository.save(voucher1);
        voucherRepository.save(voucher2);
        voucherRepository.save(voucher3);

        assertThat(voucherRepository.findAll()).hasSize(3);

        voucherRepository.deleteVoucher(voucher1);
        assertThat(voucherRepository.findAll()).hasSize(2);
        assertThatThrownBy(() -> voucherRepository.findById(uuid1).get())
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("No value present");

    }
}