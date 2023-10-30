package org.programmers.springboot.basic.voucher.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.programmers.springboot.basic.aop.LoggerAspect;
import org.programmers.springboot.basic.config.DataSourceConfig;
import org.programmers.springboot.basic.config.DataSourceProperties;
import org.programmers.springboot.basic.config.VoucherConfig;
import org.programmers.springboot.basic.domain.voucher.dto.VoucherRequestDto;
import org.programmers.springboot.basic.domain.voucher.entity.Voucher;
import org.programmers.springboot.basic.domain.voucher.entity.VoucherType;
import org.programmers.springboot.basic.domain.voucher.mapper.VoucherEntityMapper;
import org.programmers.springboot.basic.domain.voucher.mapper.VoucherEntityMapperImpl;
import org.programmers.springboot.basic.domain.voucher.repository.JdbcVoucherRepository;
import org.programmers.springboot.basic.domain.voucher.repository.VoucherRepository;
import org.programmers.springboot.basic.util.generator.UUIDGenerator;
import org.programmers.springboot.basic.util.generator.UUIDRandomGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ComponentScan(
        basePackages = {"org.programmers.springboot.basic"},
        basePackageClasses = JdbcVoucherRepository.class
)
@ContextConfiguration(classes = {
        LoggerAspect.class,
        DataSourceConfig.class,
        DataSourceProperties.class,
        VoucherConfig.class,
        UUIDRandomGenerator.class,
        VoucherEntityMapperImpl.class,
        JdbcVoucherRepository.class,
})
@EnableConfigurationProperties(value = DataSourceProperties.class)
@TestPropertySource(properties = {"spring.config.location=classpath:application-test.yaml"})
@SpringBootTest
public class VoucherRepositoryTest {

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private VoucherEntityMapper voucherEntityMapper;

    @Autowired
    private UUIDGenerator uuidGenerator;

    private final VoucherType voucherType;
    private final Long discount;

    public VoucherRepositoryTest() {
        this.voucherType = VoucherType.FIXED;
        this.discount = 1000L;
    }

    @BeforeEach
    public void init() {
        voucherRepository.deleteAll();
    }

    @Test
    @DisplayName("voucher create and get 로직 성공 검증")
    public void successCreateVoucher() {

        VoucherRequestDto requestDto = voucherEntityMapper.entityToDtoWithDetails(voucherType, discount);
        Voucher voucher = voucherEntityMapper.mapToVoucherWithGenerator(requestDto, uuidGenerator);
        System.out.println(voucher.getVoucherId());

        voucherRepository.add(voucher);

        UUID voucherId = voucher.getVoucherId();

        Voucher expected = voucherRepository.get(voucherId).orElse(null);
        assertThat(expected).isNotNull();
        assertThat(expected).isInstanceOf(Voucher.class);
        assertThat(expected.getVoucherType()).isEqualTo(voucher.getVoucherType());
        assertThat(expected.getDiscount()).isEqualTo(voucher.getDiscount());
    }

    @Test
    @DisplayName("voucher update 로직 성공 검증")
    public void successUpdateVoucher() {

        Long discount = 9000L;
        VoucherRequestDto requestDto = voucherEntityMapper.entityToDtoWithDetails(voucherType, discount);
        Voucher voucher = voucherEntityMapper.mapToVoucherWithGenerator(requestDto, uuidGenerator);

        UUID voucherId = voucher.getVoucherId();

        voucherRepository.add(voucher);
        voucherRepository.update(voucher);

        Voucher expected = voucherRepository.get(voucherId).orElse(null);
        assertThat(expected).isNotNull();
        assertThat(expected.getVoucherId()).isEqualTo(voucherId);
        assertThat(expected.getDiscount()).isEqualTo(discount);
        assertThat(expected.getVoucherType()).isEqualTo(voucher.getVoucherType());
    }

    @Test
    @DisplayName("voucher delete 로직 성공 검증")
    public void successDeleteVoucher() {

        VoucherRequestDto requestDto = voucherEntityMapper.entityToDtoWithDetails(voucherType, discount);
        Voucher voucher = voucherEntityMapper.mapToVoucherWithGenerator(requestDto, uuidGenerator);

        UUID voucherId = voucher.getVoucherId();

        voucherRepository.add(voucher);
        voucherRepository.delete(voucher.getVoucherId());

        assertThat(voucherRepository.get(voucherId)).isNotPresent();
    }

    @Test
    @DisplayName("voucher findByTypeNDiscount 로직 성공 검증")
    public void successFindByTypeNDiscountVoucher() {

        VoucherRequestDto requestDto = voucherEntityMapper.entityToDtoWithDetails(voucherType, discount);
        Voucher voucher = voucherEntityMapper.mapToVoucherWithGenerator(requestDto, uuidGenerator);

        voucherRepository.add(voucher);
        Voucher expected = voucherRepository.findByTypeNDiscount(voucher.getVoucherType(), voucher.getDiscount()).orElse(null);
        assertThat(expected).isNotNull();
        assertThat(expected.getVoucherId()).isEqualTo(voucher.getVoucherId());
        assertThat(expected.getVoucherType()).isEqualTo(voucher.getVoucherType());
        assertThat(expected.getDiscount()).isEqualTo(voucher.getDiscount());
    }

    @Test
    @DisplayName("voucher findByTypeNDiscount 로직 실패 검증")
    public void failFindByTypeNDiscountVoucher() {

        VoucherRequestDto requestDto = voucherEntityMapper.entityToDtoWithDetails(voucherType, discount);
        Voucher voucher = voucherEntityMapper.mapToVoucherWithGenerator(requestDto, uuidGenerator);

        voucherRepository.add(voucher);
        Voucher expected = voucherRepository.findByTypeNDiscount(VoucherType.PERCENT, voucher.getDiscount()).orElse(null);
        assertThat(expected).isNull();
    }

    @Test
    @DisplayName("voucher getAll 로직 성공 검증")
    public void successGetAllVoucher() {

        VoucherRequestDto requestDtoA = voucherEntityMapper.entityToDtoWithDetails(voucherType, discount);
        Voucher voucherA = voucherEntityMapper.mapToVoucherWithGenerator(requestDtoA, uuidGenerator);

        VoucherRequestDto requestDtoB = voucherEntityMapper.entityToDtoWithDetails(VoucherType.PERCENT, 55L);
        Voucher voucherB = voucherEntityMapper.mapToVoucherWithGenerator(requestDtoB, uuidGenerator);

        voucherRepository.add(voucherA);
        voucherRepository.add(voucherB);

        List<Voucher> vouchers = voucherRepository.getAll();
        assertThat(vouchers.size()).isEqualTo(2);
        assertThat(voucherRepository.get(voucherA.getVoucherId())).isPresent();
        assertThat(voucherRepository.get(voucherB.getVoucherId())).isPresent();
    }
}
