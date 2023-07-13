package com.programmers.springmission.voucher.repository;

import com.programmers.springmission.ManagementController;
import com.programmers.springmission.customer.domain.Customer;
import com.programmers.springmission.customer.repository.JdbcCustomerRepository;
import com.programmers.springmission.voucher.domain.FixedAmountPolicy;
import com.programmers.springmission.voucher.domain.PercentDiscountPolicy;
import com.programmers.springmission.voucher.domain.Voucher;
import com.programmers.springmission.voucher.domain.enums.VoucherType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@ActiveProfiles({"test", "jdbc"})
@Import(JdbcVoucherRepository.class)
class JdbcVoucherRepositoryTest {

    @Autowired
    JdbcVoucherRepository repository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @MockBean
    ManagementController managementController;

    @DisplayName("Voucher 가 repository 에 저장 성공하는지 테스트")
    @Test
    void repository_save_success() {
        // given
        Voucher voucher1 = new Voucher(new FixedAmountPolicy(VoucherType.FIXED_AMOUNT), 10L);
        Voucher voucher2 = new Voucher(new PercentDiscountPolicy(VoucherType.PERCENT_DISCOUNT), 10L);

        // when
        repository.save(voucher1);
        repository.save(voucher2);

        // then
        List<Voucher> all = repository.findAll();

        assertThat(all.size()).isEqualTo(2);
        assertThat(all).contains(voucher1, voucher2);
    }

    @DisplayName("Voucher 수정 성공하는지 테스트")
    @Test
    void repository_update_success() {
        // given
        Voucher voucher1 = new Voucher(new FixedAmountPolicy(VoucherType.FIXED_AMOUNT), 10L);
        repository.save(voucher1);

        // when
        voucher1.updateAmount(50L);
        repository.updateAmount(voucher1);

        // then
        List<Voucher> all = repository.findAll();
        assertThat(all.get(0).getVoucherId()).isEqualTo(voucher1.getVoucherId());
    }

    @DisplayName("Voucher 단건 삭제 성공하는지 테스트")
    @Test
    void repository_delete_byId_success() {
        // given
        Voucher voucher1 = new Voucher(new FixedAmountPolicy(VoucherType.FIXED_AMOUNT), 10L);
        Voucher voucher2 = new Voucher(new PercentDiscountPolicy(VoucherType.PERCENT_DISCOUNT), 10L);

        // when
        repository.save(voucher1);
        repository.save(voucher2);
        repository.deleteById(voucher2.getVoucherId());

        // then
        List<Voucher> all = repository.findAll();
        assertThat(all.size()).isEqualTo(1);
        assertThat(all.get(0).getVoucherId()).isEqualTo(voucher1.getVoucherId());
    }

    @DisplayName("Voucher 전부 삭제 성공하는지 테스트")
    @Test
    void repository_delete_all_success() {
        // given
        Voucher voucher1 = new Voucher(new FixedAmountPolicy(VoucherType.FIXED_AMOUNT), 10L);
        Voucher voucher2 = new Voucher(new PercentDiscountPolicy(VoucherType.PERCENT_DISCOUNT), 10L);

        // when
        repository.save(voucher1);
        repository.save(voucher2);
        repository.deleteAll();

        // then
        List<Voucher> all = repository.findAll();
        assertThat(all.size()).isEqualTo(0);
    }

    @DisplayName("바우처에 고객 할당 성공하는지 테스트")
    @Test
    void assign_voucher_to_customer() {

        // given
        Voucher voucher = new Voucher(new FixedAmountPolicy(VoucherType.FIXED_AMOUNT), 10L);
        Customer customer = new Customer("신재윤", "abc@gmail.com");
        JdbcCustomerRepository jdbcCustomerRepository = new JdbcCustomerRepository(jdbcTemplate);
        jdbcCustomerRepository.save(customer);

        // when
        repository.save(voucher);
        voucher.updateCustomer(customer.getCustomerId());
        repository.updateCustomer(voucher);

        // then
        List<Voucher> all = repository.findAll();
        assertThat(all.get(0).getCustomerId()).isEqualTo(customer.getCustomerId());
    }
}