package com.wonu606.vouchermanager.repository.customer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.wonu606.vouchermanager.domain.customer.Customer;
import com.wonu606.vouchermanager.domain.customer.CustomerResultSet;
import com.wonu606.vouchermanager.domain.customer.EmailAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("MappingCustomerRepository 테스트")
class MappingCustomerRepositoryTest {

    private CustomerResultSetRepository customerResultSetRepository;
    private MappingCustomerRepository customerRepository;

    static Stream<Arguments> givenCustomers() {
        Customer customer1 = new Customer(
                new EmailAddress("Linlin@onepiece.org"), "Big Mom");
        Customer customer2 = new Customer(
                new EmailAddress("loopy@onepiece.org"), "Pirate King");

        return Stream.of(Arguments.of(customer1, customer2));
    }

    @BeforeEach
    void setUp() {
        customerResultSetRepository = mock(CustomerResultSetRepository.class);
        customerRepository = new MappingCustomerRepository(customerResultSetRepository);
    }

    @Test
    @DisplayName("save_Customer_Customer를 저장하고 반환한다.")
    void save_VoucherProvided_ReturnVoucher() {
        // given
        Customer customer = mock(Customer.class);

        // then
        customerRepository.save(customer);

        // when
        verify(customerResultSetRepository, times(1)).save(customer);
    }

    @Test
    @DisplayName("findByEmailAddress_존재하는 이메일 주소라면_Customer로 변환하여 반환한다.")
    void findByEmailAddress_ExistingEmailAddress_ReturnsExpectedCustomer() {
        // given
        Customer customer = new Customer(
                new EmailAddress("Linlin@onepiece.org"), "Big Mom");
        CustomerResultSet enteredResultSet = new CustomerResultSet(
                customer.getEmailAddress(),
                customer.getNickname(),
                null,
                null
        );

        given(customerResultSetRepository.findByEmailAddress(customer.getEmailAddress()))
                .willReturn(Optional.of(enteredResultSet));

        // when
        Optional<Customer> actualCustomer = customerRepository.findByEmailAddress(
                customer.getEmailAddress());

        // then
        assertThat(actualCustomer.isPresent()).isTrue();
        assertThat(actualCustomer.get()).isEqualTo(customer);
    }

    @ParameterizedTest
    @MethodSource("givenCustomers")
    @DisplayName("findAll_빈 인수_저장된 모든 Customer를 반환한다.")
    void findAll_EmptyArgument_SavedAllCustomer(Customer customer1, Customer customer2) {
        // given
        List<Customer> expectedCustomerList = new ArrayList<>();
        expectedCustomerList.add(customer1);
        expectedCustomerList.add(customer2);

        CustomerResultSet expectedResultSet1 = new CustomerResultSet(
                customer1.getEmailAddress(), customer1.getNickname(), null, null);
        CustomerResultSet expectedResultSet2 = new CustomerResultSet(
                customer2.getEmailAddress(), customer2.getNickname(), null, null);

        List<CustomerResultSet> expectedVoucherResultSetList = new ArrayList<>();
        expectedVoucherResultSetList.add(expectedResultSet1);
        expectedVoucherResultSetList.add(expectedResultSet2);

        given(customerResultSetRepository.findAll()).willReturn(expectedVoucherResultSetList);

        // then
        List<Customer> actualCustomerList = customerRepository.findAll();

        // when
        assertThat(actualCustomerList).hasSameSizeAs(expectedCustomerList);
        assertThat(actualCustomerList).containsExactlyInAnyOrderElementsOf(expectedCustomerList);
    }

    @Test
    @DisplayName("deleteByEmailAddress_이메일 주소_같은 이메일 주소를 반환한다.")
    void deleteByEmailAddress_EmailAddress_ReturnsSameEmailAddress() {
        // given
        String expectedEmailAddress = "Linlin@onepiece.org";

        // when
        customerRepository.deleteByEmailAddress(expectedEmailAddress);

        // then
        verify(customerResultSetRepository, times(1)).deleteByEmailAddress(expectedEmailAddress);
    }

    @Test
    @DisplayName("deleteAll_빈 인자_ResultSetRepository의 deleeteAll을 실행한다.")
    void deleteAll_EmptyArgument_ExecuteVoucherResultSetRepositoryDeleteAllMethod() {
        // when
        customerRepository.deleteAll();

        // then
        verify(customerResultSetRepository, times(1)).deleteAll();
    }
}
