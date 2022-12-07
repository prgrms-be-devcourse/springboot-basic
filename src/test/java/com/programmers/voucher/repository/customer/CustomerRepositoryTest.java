package com.programmers.voucher.repository.customer;

import com.programmers.voucher.MysqlTestContainer;
import com.programmers.voucher.controller.customer.dto.CustomerCreateRequest;
import com.programmers.voucher.model.customer.Customer;
import com.programmers.voucher.model.voucher.FixedAmountVoucher;
import com.programmers.voucher.model.voucher.Voucher;
import com.programmers.voucher.repository.voucher.VoucherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class CustomerRepositoryTest extends MysqlTestContainer {

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
    @DisplayName("데이터베이스에 고객을 저장한다.")
    @Sql("classpath:schema.sql")
    void save() {
        //given
        String email = "minji@gmail.com";
        CustomerCreateRequest customerCreateRequest = new CustomerCreateRequest("minji", email);

        //when
        customerRepository.save(customerCreateRequest);

        //then
        Customer result = customerRepository.findByEmail(email).get();
        assertThat(result.getEmail())
                .isEqualTo(email);
    }

    @Test
    @DisplayName("데이터베이스에서 이메일을 통해 고객을 조회한다.")
    @Sql("classpath:schema.sql")
    void findByEmail() {
        //given
        String email = "taehee@gmail.com";

        //when
        Customer result = customerRepository.findByEmail(email).get();

        //then
        assertThat(result.getEmail())
                .isEqualTo(email);
    }

    @Test
    @DisplayName("데이터베이스에서 고객이 가지고 있는 바우처 아이디를 통해 고객을 조회한다.")
    @Sql("classpath:schema.sql")
    void findByVoucher() {
        //given
        String email = "taehee@gmail.com";
        Customer customer = customerRepository.findByEmail(email).get();
        Voucher voucher = voucherRepository.findById(voucherId).get();
        voucher.setCustomer(customer);
        voucherRepository.assign(voucher);

        //when
        Customer result = customerRepository.findByVoucher(voucher.getVoucherId()).get();

        //then
        assertThat(result.getEmail())
                .isEqualTo(email);
    }
}
