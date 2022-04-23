package org.prgrms.kdt.repository;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.domain.Customer;
import org.prgrms.kdt.domain.FixedAmountVoucher;
import org.prgrms.kdt.domain.PercentDiscountVoucher;
import org.prgrms.kdt.domain.Voucher;
import org.springframework.beans.factory.annotation.Autowired;

class JdbcVoucherRepositoryTest extends DatabaseIntegrationTest {

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private VoucherRepository voucherRepository;

  @AfterEach
  public void cleanUp() {
    voucherRepository.deleteAll();
    customerRepository.deleteAll();
  }

  @Test
  @DisplayName("고객 아이디로 바우처를 조회한다.")
  public void find_vouchers_by_customer_id() {
    var customer = new Customer("John", "john@gmail.com");
    customerRepository.save(customer);
    var voucher1 = new FixedAmountVoucher(UUID.randomUUID(), customer.getCustomerId(), 100L);
    var voucher2 = new PercentDiscountVoucher(UUID.randomUUID(), customer.getCustomerId(), 10L);
    voucherRepository.save(voucher1);
    voucherRepository.save(voucher2);

    var sut = voucherRepository.findByCustomerId(customer.getCustomerId());

    assertThat(sut.size()).isEqualTo(2);
    assertThat(sut).containsExactlyInAnyOrder(voucher1, voucher2);
  }

  @Test
  @DisplayName("바우처 정보를 수정한다.")
  public void update_voucher() {
    var customer = new Customer("John", "john@gmail.com");
    customer = customerRepository.save(customer);

    Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), customer.getCustomerId(), 100L);
    voucher = voucherRepository.save(voucher);
    voucher.assign(customer.getCustomerId());

    var sut = voucherRepository.update(voucher);
    assertThat(sut).isNotNull();
    assertThat(sut.getCustomerId()).isEqualTo(customer.getCustomerId());
    assertThat(sut).usingRecursiveComparison().isEqualTo(voucher);
  }

  @Test
  @DisplayName("바우처를 생성한다.")
  public void save_voucher() {
    var voucher = new FixedAmountVoucher(UUID.randomUUID(), null, 100L);

    var sut = voucherRepository.save(voucher);

    assertThat(sut).isNotNull();
    assertThat(sut.getVoucherId()).isEqualTo(voucher.getVoucherId());
    assertThat(sut).usingRecursiveComparison().isEqualTo(voucher);
  }
}