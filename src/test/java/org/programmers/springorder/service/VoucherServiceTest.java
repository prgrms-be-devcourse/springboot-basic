package org.programmers.springorder.service;


import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.springorder.customer.model.Customer;
import org.programmers.springorder.customer.model.CustomerType;
import org.programmers.springorder.customer.repository.CustomerRepository;
import org.programmers.springorder.customer.repository.JdbcCustomerRepository;
import org.programmers.springorder.voucher.dto.VoucherRequestDto;
import org.programmers.springorder.voucher.dto.VoucherResponseDto;
import org.programmers.springorder.voucher.model.Voucher;
import org.programmers.springorder.voucher.model.VoucherType;
import org.programmers.springorder.voucher.repository.JdbcVoucherRepository;
import org.programmers.springorder.voucher.repository.VoucherRepository;
import org.programmers.springorder.voucher.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig
class VoucherServiceTest {

    static EmbeddedMysql embeddedMysql;

    @Configuration
    static class Config{
        @Bean
        public DataSource dataSource(){
            var dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test_voucher")
                    .username("test")
                    .password("test1234!")
                    .type(HikariDataSource.class)
                    .build();
            dataSource.setMaximumPoolSize(1000);
            dataSource.setMinimumIdle(100);
            return dataSource;
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource){
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate){
            return new NamedParameterJdbcTemplate(jdbcTemplate);
        }

        @Bean
        public VoucherRepository voucherRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate){
            return new JdbcVoucherRepository(namedParameterJdbcTemplate);
        }

        @Bean
        public CustomerRepository customerRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate){
            return new JdbcCustomerRepository(namedParameterJdbcTemplate);
        }

        @Bean
        public VoucherService voucherService(VoucherRepository voucherRepository, CustomerRepository customerRepository){
            return new VoucherService(voucherRepository, customerRepository);
        }
    }

    @Autowired
    VoucherRepository voucherRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    VoucherService voucherService;


    @BeforeAll
    static void setUp(){
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
    void clear(){
        embeddedMysql.executeScripts("test_voucher", List.of(() ->"delete from vouchers; delete from customers;"));
    }




    @Test
    @DisplayName("모든 Voucher 리스트를 가져오는 Service 로직")
    void getAllVoucher() {
        List<UUID> uuids = Arrays.asList(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());
        voucherRepository.save(Voucher.toVoucher(uuids.get(0),10, VoucherType.PERCENT));
        voucherRepository.save(Voucher.toVoucher(uuids.get(1),5, VoucherType.PERCENT));
        voucherRepository.save(Voucher.toVoucher(uuids.get(2),1000, VoucherType.FIXED));
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

    @Test
    @DisplayName("Voucher 주인을 할당하는 서비스 로직")
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
    @DisplayName("Voucher 주인을 할당하는 서비스 로직, 사용자가 없는 경우")
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
        Assertions.assertThatThrownBy(() ->voucherService.update(voucherId, UUID.randomUUID()))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("해당 고객을 찾을 수 없습니다.");

    }

    @Test
    @DisplayName("Voucher 주인을 할당하는 서비스 로직, 바우처 없는 경우")
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
        Assertions.assertThatThrownBy(() ->voucherService.update(UUID.randomUUID(), customerId))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("해당 바우처를 찾을 수 없습니다.");

    }
}