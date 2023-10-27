package org.programmers.springorder.customer.service;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import org.junit.jupiter.api.*;
import org.programmers.springorder.config.JdbcConfig;
import org.programmers.springorder.customer.model.Customer;
import org.programmers.springorder.customer.model.CustomerType;
import org.programmers.springorder.customer.repository.CustomerRepository;
import org.programmers.springorder.voucher.model.Voucher;
import org.programmers.springorder.voucher.model.VoucherType;
import org.programmers.springorder.voucher.repository.VoucherRepository;
import org.programmers.springorder.voucher.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringJUnitConfig
class CustomerServiceTest {

    @Configuration
    @ComponentScan(basePackageClasses = JdbcConfig.class)
    static class AppConfig{ }

    static EmbeddedMysql embeddedMysql;

    @Autowired
    VoucherService voucherService;

    @Autowired
    VoucherRepository voucherRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerService customerService;

    @BeforeAll
    static void setUp() {
        MysqldConfig mysqldConfig = aMysqldConfig(v8_0_11)
                .withCharset(Charset.UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(mysqldConfig)
                .addSchema("test_voucher", classPathScript("/schema.sql"))
                .start();
    }

    @AfterEach
    void clear() {
        embeddedMysql.executeScripts("test_voucher", List.of(() -> "delete from vouchers; delete from customers;"));
    }

    @AfterAll
    static void finish() {
        embeddedMysql.stop();
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
            voucherService.update(voucherId, customerId);

            UUID customerId1 = customerService.findOwnerOfVoucher(voucherId).getCustomerId();

            assertThat(customerId1).isEqualTo(customerId);
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
            voucherService.update(voucherId, customerId);

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
//            voucherService.update(voucherId, customerId);

            assertThatThrownBy(() -> customerService.findOwnerOfVoucher(voucherId))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessage("해당 voucher는 주인이 존재하지 않습니다.");
        }

    }


}