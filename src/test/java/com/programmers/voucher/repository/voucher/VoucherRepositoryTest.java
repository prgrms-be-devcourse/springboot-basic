package com.programmers.voucher.repository.voucher;

import com.programmers.voucher.MysqlTestContainer;
import com.programmers.voucher.controller.customer.dto.CustomerCreateRequest;
import com.programmers.voucher.model.customer.Customer;
import com.programmers.voucher.model.voucher.FixedAmountVoucher;
import com.programmers.voucher.model.voucher.PercentDiscountVoucher;
import com.programmers.voucher.model.voucher.Voucher;
import com.programmers.voucher.repository.customer.CustomerJdbcRepository;
import com.programmers.voucher.repository.customer.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(SpringExtension.class)
@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({VoucherJdbcRepository.class, CustomerJdbcRepository.class})
class VoucherRepositoryTest extends MysqlTestContainer {

    @Autowired
    private VoucherRepository voucherRepository;
    @Autowired
    private CustomerRepository customerRepository;

    private static final UUID voucherId = UUID.randomUUID();

    @BeforeEach
    void insertCustomerData() {
        CustomerCreateRequest customerCreateRequest = new CustomerCreateRequest("taehee", "taehee@gmail.com");
        customerRepository.save(customerCreateRequest);
    }

    @BeforeEach
    void insertVoucherData() {
        voucherRepository.save(new FixedAmountVoucher(voucherId, 5000));
    }

    @Test
    @DisplayName("데이터베이스에 바우처를 저장한다.")
    @Sql("classpath:schema.sql")
    void save() {
        //given
        Voucher newVoucher = new FixedAmountVoucher(UUID.randomUUID(), 5000);

        //when
        Voucher result = voucherRepository.save(newVoucher);

        //then
        assertThat(result.getVoucherId())
                .isEqualTo(newVoucher.getVoucherId());
    }

    @Test
    @DisplayName("데이터베이스에서 모든 바우처 목록을 조회한다.")
    @Sql("classpath:schema.sql")
    void findAll() {
        //given
        Voucher newVoucher = new FixedAmountVoucher(UUID.randomUUID(), 5000);
        voucherRepository.save(newVoucher);

        //when
        List<Voucher> result = voucherRepository.findAll(null);

        //then
        assertThat(result)
                .hasSize(2);
    }

    @Test
    @DisplayName("데이터베이스에 저장된 바우처를 고객에게 할당한다.")
    @Sql("classpath:schema.sql")
    void assign() {
        //given
        String email = "taehee@gmail.com";
        Voucher voucher = voucherRepository.findById(voucherId).get();
        Customer customer = customerRepository.findByEmail(email).get();
        voucher.setCustomer(customer);

        //when
        voucherRepository.assign(voucher);

        //then
        assertThat(voucherRepository.findAll(email))
                .hasSize(1);
    }

    @Test
    @DisplayName("데이터베이스에서 해당 고객이 가진 모든 바우처 목록을 조회한다.")
    @Sql("classpath:schema.sql")
    void findAllByEmail() {
        //given
        String email = "taehee@gmail.com";
        Voucher voucher = voucherRepository.findById(voucherId).get();
        Customer customer = customerRepository.findByEmail(email).get();
        voucher.setCustomer(customer);
        voucherRepository.assign(voucher);

        //when
        List<Voucher> result = voucherRepository.findAll(email);

        //then
        assertThat(result)
                .hasSize(1);
    }

    @Test
    @DisplayName("데이터베이스에서 바우처 아이디를 통해 조회한다.")
    @Sql("classpath:schema.sql")
    void findById() {
        //given

        //when
        Voucher result = voucherRepository.findById(voucherId).get();

        //then
        assertThat(result)
                .isNotNull();
    }

    @Test
    @DisplayName("데이터베이스에서 없는 바우처 아이디를 통해 조회 시 Optional empty를 반환한다.")
    @Sql("classpath:schema.sql")
    void findByIdWhenNull() {
        //given

        //when
        Optional<Voucher> result = voucherRepository.findById(UUID.randomUUID());

        //then
        assertThat(result)
                .isEmpty();
    }

    @Test
    @DisplayName("데이터베이스에서 바우처 아이디를 통해 수정한다.")
    @Sql("classpath:schema.sql")
    void update() {
        //given
        Voucher updatedVoucher = new PercentDiscountVoucher(voucherId, 30);

        //when
        voucherRepository.update(updatedVoucher);
        Voucher result = voucherRepository.findById(voucherId).get();

        //then
        assertThat(result.getDiscountValue())
                .isEqualTo(30);
    }

    @Test
    @DisplayName("데이터베이스에서 모든 바우처 목록을 삭제한다.")
    @Sql("classpath:schema.sql")
    void deleteALL() {
        //given

        //when
        voucherRepository.deleteAll();

        //then
        assertThat(voucherRepository.findAll(""))
                .isEmpty();
    }

    @Test
    @DisplayName("데이터베이스에서 없는 바우처 아이디를 통해 조회 시 예외를 발생시킨다.")
    @Sql("classpath:schema.sql")
    void deleteByEmail() {
        assertThatThrownBy(() -> voucherRepository.deleteByEmail("hello@gmail.com"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
