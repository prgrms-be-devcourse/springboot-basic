package com.programmers.voucher.repository.voucher;

import com.programmers.voucher.JdbcConfig;
import com.programmers.voucher.controller.dto.CustomerDto;
import com.programmers.voucher.model.customer.Customer;
import com.programmers.voucher.model.voucher.FixedAmountVoucher;
import com.programmers.voucher.model.voucher.PercentDiscountVoucher;
import com.programmers.voucher.model.voucher.Voucher;
import com.programmers.voucher.model.voucher.VoucherType;
import com.programmers.voucher.repository.customer.CustomerRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VoucherRepositoryTest extends JdbcConfig {
    @Autowired
    private VoucherRepository voucherRepository;
    @Autowired
    private CustomerRepository customerRepository;

    private Customer insertSingleCustomerData() {
        String email = "taehee@gmail.com";
        CustomerDto customerDto = new CustomerDto("taehee", email);
        customerRepository.save(customerDto);
        return customerRepository.findByEmail(email).get();
    }

    private Voucher insertSingleVoucherData() {
        Voucher voucher = getVoucher();
        voucher.setVoucherType(getVoucherType());
        return voucherRepository.save(voucher);
    }

    private void insertAllVouchersData() {
        VoucherType voucherType = getVoucherType();
        for (Voucher voucher : getVouchers()) {
            voucher.setVoucherType(voucherType);
            voucherRepository.save(voucher);
        }
    }

    private VoucherType getVoucherType() {
        return VoucherType.toVoucherType("1");
    }

    private Voucher getVoucher() {
        return new FixedAmountVoucher(UUID.randomUUID(), 5000);
    }

    private List<Voucher> getVouchers() {
        return List.of(
                new FixedAmountVoucher(UUID.randomUUID(), 5000),
                new PercentDiscountVoucher(UUID.randomUUID(), 40)
        );
    }

    @Test
    @Order(1)
    @DisplayName("데이터베이스에 바우처를 저장한다.")
    void save() {
        //given
        VoucherType voucherType = VoucherType.toVoucherType("1");
        Voucher newVoucher = new FixedAmountVoucher(UUID.randomUUID(), 5000);
        newVoucher.setVoucherType(voucherType);

        //when
        Voucher result = voucherRepository.save(newVoucher);

        //then
        assertThat(result.getVoucherId())
                .isEqualTo(newVoucher.getVoucherId());
    }

    @Test
    @Order(2)
    @DisplayName("데이터베이스에서 모든 바우처 목록을 조회한다.")
    void findAll() {
        //given
        insertAllVouchersData();

        //when
        List<Voucher> result = voucherRepository.findAll();

        //then
        assertThat(result.size())
                .isEqualTo(3);
    }

    @Test
    @Order(3)
    @DisplayName("데이터베이스에 저장된 바우처를 고객에게 할당한다.")
    void assign() {
        //given
        Voucher voucher = insertSingleVoucherData();
        Customer customer = insertSingleCustomerData();
        voucher.setCustomer(customer);

        //when
        voucherRepository.assign(voucher);

        //then
        assertThat(voucherRepository.findAllByEmail("taehee@gmail.com").size())
                .isEqualTo(1);
    }

    @Test
    @Order(4)
    @DisplayName("데이터베이스에서 해당 고객이 가진 모든 바우처 목록을 조회한다.")
    void findAllByEmail() {
        //given
        String email = "taehee@gmail.com";

        //when
        List<Voucher> result = voucherRepository.findAllByEmail(email);

        //then
        assertThat(result.size())
                .isEqualTo(1);
    }

    @Test
    @DisplayName("데이터베이스에서 바우처 아이디를 통해 조회한다.")
    void findById() {
        //given
        Voucher voucher = insertSingleVoucherData();

        //when
        Voucher result = voucherRepository.findById(voucher.getVoucherId()).get();

        //then
        assertThat(result.getVoucherId())
                .isEqualTo(voucher.getVoucherId());
    }

    @Test
    @DisplayName("데이터베이스에서 바우처 아이디를 통해 수정한다.")
    void update() {
        //given
        Voucher voucher = insertSingleVoucherData();
        VoucherType updatedType = VoucherType.toVoucherType("2");
        Voucher updatedVoucher = new PercentDiscountVoucher(voucher.getVoucherId(), 30);
        updatedVoucher.setVoucherType(updatedType);

        //when
        voucherRepository.update(updatedVoucher);
        Voucher result = voucherRepository.findById(voucher.getVoucherId()).get();

        //then
        assertThat(result.getDiscountValue())
                .isEqualTo(30);
    }

    @Test
    @Order(5)
    @DisplayName("데이터베이스에서 모든 바우처 목록을 삭제한다.")
    void deleteALL() {
        //given

        //when
        voucherRepository.deleteAll();

        //then
        assertThat(voucherRepository.findAll().isEmpty())
                .isTrue();
    }
}
