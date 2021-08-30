package org.prgrms.kdt.service;

import com.zaxxer.hikari.HikariDataSource;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.domain.voucher.VoucherType;
import org.prgrms.kdt.repository.voucher.JdbcVoucherRepository;
import org.prgrms.kdt.repository.voucher.MemoryVoucherRepository;
import org.prgrms.kdt.repository.voucher.VoucherRepository;
import org.prgrms.kdt.service.dto.RequestCreatVoucherDto;
import org.prgrms.kdt.service.dto.RequestUpdateVoucherDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("staging")
class VoucherServiceTest {

    @Configuration
    static class Config {

        @Bean
        public DataSource dataSource() {
            HikariDataSource dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost/order_mgmt")
                    .username("root")
                    .password("admin")
                    .type(HikariDataSource.class)
                    .build();
            return dataSource;
        }

        @Bean
        public JdbcTemplate jdbcTemplate() {
            return new JdbcTemplate(dataSource());
        }

        @Bean
        public VoucherRepository voucherRepository() {
            return new JdbcVoucherRepository(jdbcTemplate());
        }

        @Bean
        public VoucherService voucherService() {
            return new VoucherService(voucherRepository());
        }
    }


    @Autowired
    private VoucherService voucherService;

    @Autowired
    private VoucherRepository voucherRepository;

    @BeforeEach
    void clean() {
        voucherRepository.deleteAll();
    }

    @Test
    @DisplayName("바우처가 저장된다")
    public void testInsert() throws Exception {
        // given

        RequestCreatVoucherDto dto = new RequestCreatVoucherDto(VoucherType.FIX, 10);

        // when
        voucherService.insert(dto);
        // then

        assertThat(voucherService.vouchers().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("바우처의 값을 변경시킬 수 있다")
    public void testUpdate() throws Exception {
        // given
        RequestCreatVoucherDto dto = new RequestCreatVoucherDto(VoucherType.FIX, 10);
        Voucher inserted = voucherService.insert(dto);
        RequestUpdateVoucherDto updateDto = new RequestUpdateVoucherDto(inserted.getVoucherId(), 20);

        // when
        voucherService.update(updateDto);
        Voucher updated = voucherService.findById(updateDto.getVoucherId());

        // then

        assertThat(updated.getValue()).isEqualTo(updateDto.getValue());
    }

    @Test
    @DisplayName("음수로 정액 바우처 값을 변경시킬 수 없다")
    public void testUpdateFixVoucherNegativeValue() throws Exception {
        // given
        RequestCreatVoucherDto dto = new RequestCreatVoucherDto(VoucherType.FIX, 10);
        Voucher inserted = voucherService.insert(dto);
        RequestUpdateVoucherDto updateDto = new RequestUpdateVoucherDto(inserted.getVoucherId(), -1);

        // when
        try {
            voucherService.update(updateDto);
        } catch (IllegalArgumentException e) {

        }
        Voucher updated = voucherService.findById(updateDto.getVoucherId());

        // then
        assertThat(voucherRepository.findAll()).hasSize(1);
        assertThat(updated.getValue()).isEqualTo(inserted.getValue());
    }

    @Test
    @DisplayName("한계 값 이상으로 정액 바우처 값을 변경시킬 수 없다")
    public void testUpdateFixVoucherOverLimit() throws Exception {
        // given
        RequestCreatVoucherDto dto = new RequestCreatVoucherDto(VoucherType.FIX, 10);
        Voucher inserted = voucherService.insert(dto);
        RequestUpdateVoucherDto updateDto = new RequestUpdateVoucherDto(inserted.getVoucherId(), VoucherType.FIX.getMaxValue() + 1);

        // when
        try {
            voucherService.update(updateDto);
        } catch (IllegalArgumentException e) {

        }
        Voucher updated = voucherService.findById(updateDto.getVoucherId());

        // then
        assertThat(voucherRepository.findAll()).hasSize(1);
        assertThat(updated.getValue()).isEqualTo(inserted.getValue());
    }

    @Test
    @DisplayName("음수로 정률 바우처 값을 변경시킬 수 없다")
    public void testUpdatePercentVoucherNegativeValue() throws Exception {
        // given
        RequestCreatVoucherDto dto = new RequestCreatVoucherDto(VoucherType.PERCENT, 10);
        Voucher inserted = voucherService.insert(dto);
        RequestUpdateVoucherDto updateDto = new RequestUpdateVoucherDto(inserted.getVoucherId(), -1);

        // when
        try {
            voucherService.update(updateDto);
        } catch (IllegalArgumentException e) {

        }
        Voucher updated = voucherService.findById(updateDto.getVoucherId());

        // then
        assertThat(voucherRepository.findAll()).hasSize(1);
        assertThat(updated.getValue()).isEqualTo(inserted.getValue());
    }

    @Test
    @DisplayName("한계 값 이상으로 정률 바우처 값을 변경시킬 수 없다")
    public void testUpdatePercentVoucherOverLimit() throws Exception {
        // given
        RequestCreatVoucherDto dto = new RequestCreatVoucherDto(VoucherType.PERCENT, 10);
        Voucher inserted = voucherService.insert(dto);
        RequestUpdateVoucherDto updateDto = new RequestUpdateVoucherDto(inserted.getVoucherId(), VoucherType.PERCENT.getMaxValue() + 1);

        // when
        try {
            voucherService.update(updateDto);
        } catch (IllegalArgumentException e) {

        }
        Voucher updated = voucherService.findById(updateDto.getVoucherId());

        // then
        assertThat(voucherRepository.findAll()).hasSize(1);
        assertThat(updated.getValue()).isEqualTo(inserted.getValue());
    }

    @Test
    @DisplayName("바우처가 여러개 저장된다")
    public void saveMultipleVoucher() throws Exception {
        // given
        RequestCreatVoucherDto dto1 = new RequestCreatVoucherDto(VoucherType.FIX, 10);
        RequestCreatVoucherDto dto2 = new RequestCreatVoucherDto(VoucherType.FIX, 11);
        RequestCreatVoucherDto dto3 = new RequestCreatVoucherDto(VoucherType.FIX, 12);
        RequestCreatVoucherDto dto4 = new RequestCreatVoucherDto(VoucherType.FIX, 13);
        RequestCreatVoucherDto dto5 = new RequestCreatVoucherDto(VoucherType.FIX, 14);

        // when
        voucherService.insert(dto1);
        voucherService.insert(dto2);
        voucherService.insert(dto3);
        voucherService.insert(dto4);
        voucherService.insert(dto5);

        // then

        assertThat(voucherService.vouchers().size()).isEqualTo(5);
    }
}