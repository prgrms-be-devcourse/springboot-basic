package org.programmers.devcourse.voucher.engine.customer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest
@ActiveProfiles("dev")
class JdbcCustomerRepositoryTest {

  @Autowired
  private JdbcCustomerRepository repository;

  static Stream<Arguments> uuidSource() {
    return Stream.of(arguments(
        List.of(
            UUID.fromString("19d766d5-5617-435a-825c-2c8d2324f35d"),
            UUID.fromString("ac83c4be-79ff-4e08-80ce-7721bc7f7508"),
            UUID.fromString("eb601768-f7d0-439c-b7ae-0ee6ed2e310c"))
    ));
  }


  @ParameterizedTest
  @MethodSource("uuidSource")
  @DisplayName("customerId와 일치하는 Customer를 가져와야 한다.")
  void get_corresponding_customer_using_customer_id(List<UUID> uuids) {

    uuids.forEach(uuid -> {
      var customer = repository.getById(uuid);
      assertThat(customer).isNotEmpty();
      assertThat(customer.get().getCustomerId()).isIn(uuids);

    });

  }

}
