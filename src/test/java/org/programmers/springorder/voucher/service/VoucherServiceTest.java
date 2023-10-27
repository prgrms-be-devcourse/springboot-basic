package org.programmers.springorder.voucher.service;


import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.programmers.springorder.config.JdbcConfig;
import org.programmers.springorder.customer.model.Customer;
import org.programmers.springorder.customer.model.CustomerType;
import org.programmers.springorder.customer.repository.CustomerRepository;
import org.programmers.springorder.voucher.dto.VoucherRequestDto;
import org.programmers.springorder.voucher.dto.VoucherResponseDto;
import org.programmers.springorder.voucher.model.Voucher;
import org.programmers.springorder.voucher.model.VoucherType;
import org.programmers.springorder.voucher.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringJUnitConfig
class VoucherServiceTest {

    static EmbeddedMysql embeddedMysql;

    @Configuration
    @ComponentScan(basePackageClasses = JdbcConfig.class)
    static class Config {
    }

    @Autowired
    VoucherRepository voucherRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    VoucherService voucherService;


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
    @Test
    @DisplayName("모든 Voucher 리스트를 가져오는 Service 로직")
    void getAllVoucher() {
        List<UUID> uuids = Arrays.asList(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());
        voucherRepository.save(Voucher.toVoucher(uuids.get(0), 10, VoucherType.PERCENT));
        voucherRepository.save(Voucher.toVoucher(uuids.get(1), 5, VoucherType.PERCENT));
        voucherRepository.save(Voucher.toVoucher(uuids.get(2), 1000, VoucherType.FIXED));
        voucherRepository.save(Voucher.toVoucher(uuids.get(3), 2000, VoucherType.FIXED));

        List<VoucherResponseDto> allVoucher = voucherService.getAllVoucher();
        List<UUID> rs = allVoucher.stream().map(VoucherResponseDto::getVoucherId).toList();

        assertThat(allVoucher).hasSize(4);
        assertThat(rs.containsAll(uuids))
                .isTrue();
    }

    @Test
    @DisplayName("Voucher를 저장하는 Service 로직")
    void saveNewVoucher() {
        //given
        VoucherRequestDto requestDto = new VoucherRequestDto(100, VoucherType.FIXED);
        List<VoucherResponseDto> beforeSaveVoucher = voucherService.getAllVoucher();
        assertThat(beforeSaveVoucher).hasSize(0);

        //when
        voucherService.save(requestDto);
        List<VoucherResponseDto> allVoucher = voucherService.getAllVoucher();

        //then
        assertThat(allVoucher).hasSize(1);
    }

    @Nested
    @DisplayName("voucher 주인 할당 로직")
    class allocateVoucher{


        @Test
        @DisplayName("성공")
        void allocateVoucherOwner() {
            //given
            UUID voucherId = UUID.randomUUID();
            UUID customerId = UUID.randomUUID();
            Voucher voucher = Voucher.toVoucher(voucherId, 1000, VoucherType.FIXED);
            Customer customer = Customer.toCustomer(customerId, "owner", CustomerType.NORMAL);

            //when
            voucherRepository.save(voucher);
            customerRepository.insert(customer);
            voucherService.update(voucherId, customerId);

            //then
            Voucher voucherWithOwner = voucherRepository.findById(voucherId).get();
            assertThat(voucherWithOwner.getCustomerId()).isEqualTo(customerId);

        }

        @Test
        @DisplayName("실패, 사용자가 없는 경우")
        void allocateVoucherOwnerWithoutValidUser() {
            //given
            UUID voucherId = UUID.randomUUID();
            UUID customerId = UUID.randomUUID();
            Voucher voucher = Voucher.toVoucher(voucherId, 1000, VoucherType.FIXED);
            Customer customer = Customer.toCustomer(customerId, "owner", CustomerType.NORMAL);

            //when
            voucherRepository.save(voucher);
            customerRepository.insert(customer);

            //then
            Assertions.assertThatThrownBy(() -> voucherService.update(voucherId, UUID.randomUUID()))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessage("해당 고객을 찾을 수 없습니다.");

        }

        @Test
        @DisplayName("실패, 바우처 없는 경우")
        void allocateVoucherOwnerWithoutValidVoucher() {
            //given
            UUID voucherId = UUID.randomUUID();
            UUID customerId = UUID.randomUUID();
            Voucher voucher = Voucher.toVoucher(voucherId, 1000, VoucherType.FIXED);
            Customer customer = Customer.toCustomer(customerId, "owner", CustomerType.NORMAL);

            //when
            voucherRepository.save(voucher);
            customerRepository.insert(customer);

            //then
            Assertions.assertThatThrownBy(() -> voucherService.update(UUID.randomUUID(), customerId))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessage("해당 바우처를 찾을 수 없습니다.");

        }

    }


    @Nested
    @DisplayName("고객 id로 voucher 조회")
    class findByUserId {

        @Test
        @DisplayName("성공")
        public void customerOwnedVoucherServiceTest() {
            UUID uuid1 = UUID.randomUUID();
            UUID uuid2 = UUID.randomUUID();
            UUID uuid3 = UUID.randomUUID();
            UUID uuidCustomer = UUID.randomUUID();

            Voucher voucher1 = Voucher.toVoucher(uuid1, 100, VoucherType.FIXED);
            Voucher voucher2 = Voucher.toVoucher(uuid2, 100, VoucherType.FIXED);
            Voucher voucher3 = Voucher.toVoucher(uuid3, 100, VoucherType.FIXED);

            Customer customer = Customer.toCustomer(uuidCustomer, "owner", CustomerType.NORMAL);
            customerRepository.insert(customer);
            voucherRepository.save(voucher1);
            voucherRepository.save(voucher2);
            voucherRepository.save(voucher3);

            Voucher updatedVoucher1 = voucherRepository.updateVoucherOwner(voucher1, customer);
            Voucher updatedVoucher2 = voucherRepository.updateVoucherOwner(voucher2, customer);
            List<UUID> voucherWithCustomer = Stream.of(updatedVoucher1, updatedVoucher2)
                    .map(Voucher::getVoucherId)
                    .toList();
            List<VoucherResponseDto> customerOwnedVouchers = voucherService.getCustomerOwnedVouchers(customer.getCustomerId());
            List<UUID> foundVoucherId = customerOwnedVouchers
                    .stream()
                    .map(VoucherResponseDto::getVoucherId)
                    .toList();

            assertThat(customerOwnedVouchers).hasSize(2);
            assertThat(voucherWithCustomer).containsAll(foundVoucherId);
        }

        @Test
        @DisplayName("실패, 고객이 존재하지 않는 경우")
        public void customerOwnedVoucherServiceWithAnonymousUserTest() {
            assertThatThrownBy(() -> voucherService.getCustomerOwnedVouchers(UUID.randomUUID()))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessage("해당 고객을 찾을 수 없습니다.");
        }
    }
}