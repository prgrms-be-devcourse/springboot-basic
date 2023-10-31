package org.programmers.springboot.basic.voucher.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.programmers.springboot.basic.DataSourceConfig;
import org.programmers.springboot.basic.DataSourceProperty;
import org.programmers.springboot.basic.aop.LoggerAspect;
import org.programmers.springboot.basic.config.VoucherConfig;
import org.programmers.springboot.basic.domain.customer.repository.JdbcCustomerRepository;
import org.programmers.springboot.basic.domain.voucher.dto.VoucherRequestDto;
import org.programmers.springboot.basic.domain.voucher.entity.Voucher;
import org.programmers.springboot.basic.domain.voucher.entity.VoucherType;
import org.programmers.springboot.basic.domain.voucher.exception.DuplicateVoucherException;
import org.programmers.springboot.basic.domain.voucher.exception.IllegalDiscountException;
import org.programmers.springboot.basic.domain.voucher.exception.VoucherNotFoundException;
import org.programmers.springboot.basic.domain.voucher.mapper.VoucherEntityMapper;
import org.programmers.springboot.basic.domain.voucher.mapper.VoucherEntityMapperImpl;
import org.programmers.springboot.basic.domain.voucher.repository.JdbcVoucherRepository;
import org.programmers.springboot.basic.domain.voucher.repository.VoucherRepository;
import org.programmers.springboot.basic.domain.voucher.service.VoucherService;
import org.programmers.springboot.basic.util.generator.UUIDRandomGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(SpringExtension.class)
@ComponentScan(
        basePackages = {"org.programmers.springboot.basic"},
        basePackageClasses = JdbcCustomerRepository.class
)
@ContextConfiguration(classes = {
        LoggerAspect.class,
        UUIDRandomGenerator.class,
        VoucherEntityMapperImpl.class,
        JdbcVoucherRepository.class,
        VoucherService.class,
        VoucherConfig.class,
        DataSourceProperty.class,
        DataSourceConfig.class
})
@TestPropertySource(locations = "classpath:application-test.properties")
public class VoucherServiceTest {

    @Autowired
    private VoucherService voucherService;

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private VoucherEntityMapper voucherEntityMapper;

    private final VoucherType voucherType;
    private final Long discount;

    public VoucherServiceTest() {
        this.voucherType = VoucherType.FIXED;
        this.discount = 1000L;
    }

    @BeforeEach
    public void init() {
        voucherService.deleteAll();
    }

    @Test
    @DisplayName("voucher create 로직 성공 검증")
    public void successCreateVoucher() {

        VoucherRequestDto requestDto = voucherEntityMapper.entityToDtoWithDetails(voucherType, discount);
        voucherService.create(requestDto);

        Voucher voucher = voucherRepository.findByTypeNDiscount(voucherType, discount).orElse(null);
        assertThat(voucher).isNotNull();

        UUID voucherId = voucher.getVoucherId();
        Voucher expected = voucherRepository.get(voucherId).orElse(null);
        assertThat(expected).isNotNull();
        assertThat(expected.getVoucherType()).isEqualTo(voucher.getVoucherType());
        assertThat(expected.getDiscount()).isEqualTo(voucher.getDiscount());
    }

    @Test
    @DisplayName("Duplicate voucher 대해 로직 실패 검증")
    public void failByDuplicateVoucher() {

        VoucherRequestDto requestDto = voucherEntityMapper.entityToDtoWithDetails(voucherType, discount);
        voucherService.create(requestDto);
        assertThatThrownBy(() -> voucherService.create(requestDto))
                .isInstanceOf(DuplicateVoucherException.class)
                .hasMessageContaining("Duplicate voucher already exists!");
    }

    @Test
    @DisplayName("존재하지 않는 ID에 대한 로직 실패 검증")
    public void failByNonExistId() {

        VoucherRequestDto requestDtoA = voucherEntityMapper.entityToDtoWithDetails(voucherType, discount);
        voucherService.create(requestDtoA);

        Voucher voucher = voucherRepository.findByTypeNDiscount(voucherType, discount).orElse(null);
        assertThat(voucher).isNotNull();

        UUID voucherId = voucher.getVoucherId();
        String strVoucherId = voucherId.toString();
        String result = strVoucherId.substring(0, strVoucherId.length() - 1);

        VoucherRequestDto requestDtoB = voucherEntityMapper.entityToDtoWithUUID(result);
        assertThatThrownBy(() -> voucherService.findById(requestDtoB)).isInstanceOf(VoucherNotFoundException.class)
                .hasMessageContaining("No matching vouchers found!");
    }

    @Test
    @DisplayName("update 로직 성공 검증")
    public void successUpdateVoucher() {

        VoucherRequestDto requestDtoA = voucherEntityMapper.entityToDtoWithDetails(voucherType, discount);
        voucherService.create(requestDtoA);

        Voucher voucher = voucherRepository.findByTypeNDiscount(voucherType, discount).orElse(null);
        assertThat(voucher).isNotNull();

        UUID voucherId = voucher.getVoucherId();
        VoucherRequestDto requestDtoB = voucherEntityMapper.mapToDtoWithAllArgs(voucherId, voucherType, 7500L);
        voucherService.updateVoucher(requestDtoB);

        Voucher expected = voucherRepository.get(voucherId).orElse(null);
        assertThat(expected).isNotNull();
        assertThat(expected.getVoucherType()).isEqualTo(requestDtoB.getVoucherType());
        assertThat(expected.getDiscount()).isEqualTo(requestDtoB.getDiscount());
    }

    @Test
    @DisplayName("validate 로직 실패 검증")
    public void failValidationDiscount() {

        VoucherRequestDto requestDto = voucherEntityMapper.entityToDtoWithDetails(voucherType, -1L);
        assertThatThrownBy(() -> voucherService.create(requestDto)).isInstanceOf(IllegalDiscountException.class)
                .hasMessageContaining("Illegal Discount value! Possible from 0.");
    }
}
