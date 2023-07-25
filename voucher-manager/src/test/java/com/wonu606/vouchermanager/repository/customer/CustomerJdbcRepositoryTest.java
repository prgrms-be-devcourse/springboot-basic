package com.wonu606.vouchermanager.repository.customer;

import static org.assertj.core.api.Assertions.assertThat;

import com.wonu606.vouchermanager.domain.customer.Customer;
import com.wonu606.vouchermanager.domain.customer.email.Email;
import com.wonu606.vouchermanager.repository.customer.query.CustomerCreateQuery;
import com.wonu606.vouchermanager.repository.customer.reader.CustomerJdbcReader;
import com.wonu606.vouchermanager.repository.customer.reader.CustomerReader;
import com.wonu606.vouchermanager.repository.customer.reader.rowmapper.CustomerReaderRowMapperManager;
import com.wonu606.vouchermanager.repository.customer.resultset.CustomerCreateResultSet;
import com.wonu606.vouchermanager.repository.customer.resultset.CustomerResultSet;
import com.wonu606.vouchermanager.repository.customer.store.CustomerJdbcStore;
import com.wonu606.vouchermanager.repository.customer.store.CustomerStore;
import com.wonu606.vouchermanager.repository.voucher.query.VoucherInsertQuery;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@JdbcTest
@DisplayName("CustomerResultSetJdbcRepository 테스트")
class CustomerJdbcRepositoryTest {

    private CustomerRepository repository;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @BeforeEach
    void setUp() {
        CustomerReader reader = new CustomerJdbcReader(namedParameterJdbcTemplate,
                new CustomerReaderRowMapperManager());
        CustomerStore store = new CustomerJdbcStore(namedParameterJdbcTemplate);
        repository = new CustomerJdbcRepository(reader, store);
    }

    @ParameterizedTest
    @MethodSource("givenCustomerCreateQuery")
    @DisplayName("insert_새 Customer이면_저장된다.")
    void insert_NewCustomer_ReturnInsertedCustomer(CustomerCreateQuery query) {
        // given
        CustomerCreateResultSet expected = new CustomerCreateResultSet(1);

        // when
        CustomerCreateResultSet actual = repository.insert(
                new CustomerCreateQuery(query.getEmail(), query.getNickname()));

        // then
        assertThat(actual.getTaskSuccess()).isEqualTo(expected.getTaskSuccess());
    }

    @ParameterizedTest
    @MethodSource("givenCustomerCreateQuery")
    @DisplayName("findAll_조건 없음_저장된 모든 Customer들을 반환한다.")
    void findAll_NoConditions_ReturnAllCustomers(CustomerCreateQuery query) {
        // given
        repository.insert(query);
        CustomerResultSet expectedResultSet = new CustomerResultSet(query.getEmail(),
                query.getNickname());

        // when
        List<CustomerResultSet> allCustomers = repository.findAll();

        // then
        assertThat(allCustomers).hasSize(1);
        assertThat(allCustomers).usingRecursiveComparison().isEqualTo(List.of(expectedResultSet));
    }

    @ParameterizedTest
    @MethodSource("givenCustomerCreateQuery")
    @DisplayName("deleteByCustomerId_존재하는 Customer이면_Customer를 제거한다.")
    void deleteByCustomerId_ExistingCustomer_CustomerIsDeleted(CustomerCreateQuery query) {
        // given
        repository.insert(query);

        // then
        repository.deleteByCustomerId(query.getEmail());
        List<CustomerResultSet> actualAllList = repository.findAll();

        // when
        assertThat(actualAllList).hasSize(0);
    }

    static Stream<Arguments> givenCustomerCreateQuery() {
        CustomerCreateQuery query = new CustomerCreateQuery("Linlin@onepiece.org", "Big Mom");
        return Stream.of(Arguments.of(query));
    }
}
