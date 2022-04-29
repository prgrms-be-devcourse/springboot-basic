package org.prgrms.kdt.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.domain.Customer;
import org.prgrms.kdt.dto.VoucherDto;
import org.prgrms.kdt.exception.EntityNotFoundException;
import org.prgrms.kdt.type.VoucherType;
import org.springframework.beans.factory.annotation.Autowired;

class JdbcCustomerRepositoryTest extends DatabaseIntegrationTest {

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
  @DisplayName("중복된 이메일을 검사한다.")
  public void check_duplicated_email() {
    var existsCustomer = new Customer("John", "john@gmail.com");
    customerRepository.save(existsCustomer);

    var exists = customerRepository.existsByEmail("john@gmail.com");

    assertThat(exists).isTrue();
  }

  @Test
  @DisplayName("고객을 저장한다.")
  public void save_customer() {
    var customer = new Customer("John", "jonh@gmail.com");
    var savedCustomer = customerRepository.save(customer);

    assertThat(savedCustomer).isNotEmpty();
    assertThat(savedCustomer.get()).usingRecursiveComparison().isEqualTo(customer);
  }

  @Test
  @DisplayName("전체 고객을 조회한다.")
  public void find_all_customer() {
    var customer1 = new Customer("John", "john@gmail.com");
    var customer2 = new Customer("Jane", "jane@gmail.com");
    customerRepository.save(customer1);
    customerRepository.save(customer2);

    var customers = customerRepository.findAll();

    assertThat(customers.size()).isEqualTo(2);
    assertThat(customers).containsExactlyInAnyOrder(customer1, customer2);
  }

  @Test
  @DisplayName("바우처 아이디에 해당하는 고객을 반환한다.")
  public void find_customer_by_voucherId() {
    var customer = new Customer("John", "john@gmail.com");
    customer = customerRepository.save(customer).orElseThrow(EntityNotFoundException::new);
    var voucherDto = new VoucherDto(UUID.randomUUID(), customer.getCustomerId(), 20L,
        VoucherType.of(1),
        LocalDateTime.now());
    var voucher = VoucherType.FIXED.create(voucherDto);
    voucherRepository.save(voucher);

    var customerByVoucherId = customerRepository.findCustomerByVoucherId(
        voucher.getVoucherId());

    assertThat(customerByVoucherId.isPresent()).isTrue();
    assertThat(customerByVoucherId.get().getCustomerId()).isEqualTo(customer.getCustomerId());
  }
}