package org.programmers.springorder.customer.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.programmers.springorder.config.jdbc.JdbcConfig;
import org.programmers.springorder.customer.dto.CustomerResponseDto;
import org.programmers.springorder.customer.model.Customer;
import org.programmers.springorder.customer.model.CustomerType;
import org.programmers.springorder.customer.repository.CustomerRepository;
import org.programmers.springorder.customer.repository.JdbcCustomerRepository;
import org.programmers.springorder.voucher.model.Voucher;
import org.programmers.springorder.voucher.model.VoucherType;
import org.programmers.springorder.voucher.repository.JdbcVoucherRepository;
import org.programmers.springorder.voucher.repository.VoucherRepository;
import org.programmers.springorder.voucher.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringJUnitConfig
class CustomerServiceTest {

    @Configuration
    @ComponentScan(basePackageClasses = JdbcConfig.class)
    static class AppConfig{ }

    @Autowired
    VoucherService voucherService;

    @Autowired
    VoucherRepository voucherRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerService customerService;


    @AfterEach
    void clear(){
        JdbcVoucherRepository jdbcVoucherRepository = (JdbcVoucherRepository) voucherRepository;
        jdbcVoucherRepository.clear();
        JdbcCustomerRepository jdbcCustomerRepository = (JdbcCustomerRepository) customerRepository;
        jdbcCustomerRepository.clear();

    }


    @Nested
    @DisplayName("특정 voucher 가진 고객 조회 서비스 로직 테스트")
    class FindVoucherOwner{
        @Test
        @DisplayName("성공")
        public void findVoucherOwner(){
            //given
            UUID voucherId = UUID.randomUUID();
            UUID customerId = UUID.randomUUID();
            Voucher voucher = Voucher.toVoucher(voucherId, 1000, VoucherType.FIXED);
            Customer customer = Customer.toCustomer(customerId, "owner", CustomerType.NORMAL);

            //when
            voucherRepository.save(voucher);
            customerRepository.insert(customer);
            voucherService.allocateVoucher(voucherId, customerId);

            UUID customerId1 = customerService.findOwnerOfVoucher(voucherId).getCustomerId();

            assertThat(customerId1).isEqualTo(customerId);
        }

        @Test
        @DisplayName("black list를 불러오는 test")
        public void findBlacklist(){
            Customer customer1 = Customer.toCustomer(UUID.randomUUID(), "owner", CustomerType.NORMAL);
            Customer customer2 = Customer.toCustomer(UUID.randomUUID(), "owner", CustomerType.BLACK);
            Customer customer3 = Customer.toCustomer(UUID.randomUUID(), "owner", CustomerType.BLACK);

            customerRepository.insert(customer1);
            customerRepository.insert(customer2);
            customerRepository.insert(customer3);

            List<CustomerResponseDto> blackList = customerService.getBlackList();
            assertThat(blackList).hasSize(2);
        }
        @Test
        @DisplayName("실패, voucher id로 검색을 실패한 경우")
        public void findVoucherOwnerNoVoucher(){
            //given
            UUID voucherId = UUID.randomUUID();
            UUID customerId = UUID.randomUUID();
            Voucher voucher = Voucher.toVoucher(voucherId, 1000, VoucherType.FIXED);
            Customer customer = Customer.toCustomer(customerId, "owner", CustomerType.NORMAL);

            //when
            voucherRepository.save(voucher);
            customerRepository.insert(customer);
            voucherService.allocateVoucher(voucherId, customerId);

            assertThatThrownBy(() -> customerService.findOwnerOfVoucher(UUID.randomUUID()))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessage("찾으시는 voucher가 존재하지 않습니다.");
        }

        @Test
        @DisplayName("실패, voucher 주인이 존재하지 않을 경우")
        public void findVoucherOwnerNoOwner(){
            //given
            UUID voucherId = UUID.randomUUID();
            UUID customerId = UUID.randomUUID();
            Voucher voucher = Voucher.toVoucher(voucherId, 1000, VoucherType.FIXED);
            Customer customer = Customer.toCustomer(customerId, "owner", CustomerType.NORMAL);

            //when
            voucherRepository.save(voucher);
            customerRepository.insert(customer);
//            voucherService.allocateVoucher(voucherId, customerId);

            assertThatThrownBy(() -> customerService.findOwnerOfVoucher(voucherId))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessage("해당 voucher는 주인이 존재하지 않습니다.");
        }

    }


}