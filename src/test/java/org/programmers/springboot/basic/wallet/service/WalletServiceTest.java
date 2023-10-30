package org.programmers.springboot.basic.wallet.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.programmers.springboot.basic.aop.LoggerAspect;
import org.programmers.springboot.basic.config.DataSourceConfig;
import org.programmers.springboot.basic.config.DataSourceProperties;
import org.programmers.springboot.basic.config.VoucherConfig;
import org.programmers.springboot.basic.domain.customer.dto.CustomerRequestDto;
import org.programmers.springboot.basic.domain.customer.exception.CustomerNotFoundException;
import org.programmers.springboot.basic.domain.customer.mapper.CustomerEntityMapper;
import org.programmers.springboot.basic.domain.customer.mapper.CustomerEntityMapperImpl;
import org.programmers.springboot.basic.domain.customer.service.CustomerService;
import org.programmers.springboot.basic.domain.voucher.dto.VoucherRequestDto;
import org.programmers.springboot.basic.domain.voucher.dto.VoucherResponseDto;
import org.programmers.springboot.basic.domain.voucher.entity.VoucherType;
import org.programmers.springboot.basic.domain.voucher.mapper.VoucherEntityMapper;
import org.programmers.springboot.basic.domain.voucher.mapper.VoucherEntityMapperImpl;
import org.programmers.springboot.basic.domain.voucher.service.VoucherService;
import org.programmers.springboot.basic.domain.wallet.dto.WalletRequestDto;
import org.programmers.springboot.basic.domain.wallet.dto.WalletResponseDto;
import org.programmers.springboot.basic.domain.wallet.mapper.WalletEntityMapper;
import org.programmers.springboot.basic.domain.wallet.mapper.WalletEntityMapperImpl;
import org.programmers.springboot.basic.domain.wallet.repository.JdbcWalletRepository;
import org.programmers.springboot.basic.domain.wallet.repository.WalletRepository;
import org.programmers.springboot.basic.domain.wallet.service.WalletService;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(SpringExtension.class)
@ComponentScan(
        basePackages = {"org.programmers.springboot.basic"},
        basePackageClasses = WalletService.class
)
@ContextConfiguration(classes = {
        LoggerAspect.class,
        DataSourceConfig.class,
        DataSourceProperties.class,
        UUIDRandomGenerator.class,
        WalletEntityMapperImpl.class,
        CustomerEntityMapperImpl.class,
        VoucherEntityMapperImpl.class,
        JdbcWalletRepository.class,
        WalletService.class,
        VoucherConfig.class
})
@EnableConfigurationProperties(value = DataSourceProperties.class)
@TestPropertySource(properties = {"spring.config.location=classpath:application-test.yaml"})
@SpringBootTest
public class WalletServiceTest {

    @Autowired
    private WalletService walletService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private VoucherService voucherService;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private WalletEntityMapper walletEntityMapper;

    @Autowired
    private VoucherEntityMapper voucherEntityMapper;

    @Autowired
    private CustomerEntityMapper customerEntityMapper;

    private final String name;
    private final String email;

    public WalletServiceTest() {
        this.name = "이유노";
        this.email = "test@naver.com";
    }

    @BeforeEach
    public void init() {
        walletRepository.deleteAll();
        customerService.deleteAll();
        voucherService.deleteAll();
    }

    @Test
    @DisplayName("email에 대해 voucher list 조회 로직 성공 검증")
    public void successFindVoucherListByEmail() {

        WalletRequestDto requestDto = walletEntityMapper.mapToDtoWithEmail(email);
        assertThatThrownBy(() -> walletService.walletListByEmail(requestDto)).isInstanceOf(CustomerNotFoundException.class)
                        .hasMessageContaining("No matching customers found!");

        CustomerRequestDto customerRequestDto = customerEntityMapper.mapToRequestDto(name, email);
        customerService.create(customerRequestDto);

        VoucherRequestDto voucherRequestDtoA = voucherEntityMapper.entityToDtoWithDetails(VoucherType.FIXED, 2300L);
        voucherService.create(voucherRequestDtoA);

        VoucherRequestDto voucherRequestDtoB = voucherEntityMapper.entityToDtoWithDetails(VoucherType.FIXED, 2100L);
        voucherService.create(voucherRequestDtoB);

        List<VoucherResponseDto> list = voucherService.findAll();
        UUID voucherAId = list.get(0).voucherId();
        UUID voucherBId = list.get(1).voucherId();

        WalletRequestDto walletRequestDtoA = walletEntityMapper.mapToDto(email, voucherAId.toString());
        walletService.createWallet(walletRequestDtoA);

        WalletRequestDto walletRequestDtoB = walletEntityMapper.mapToDto(email, voucherBId.toString());
        walletService.createWallet(walletRequestDtoB);

        List<WalletResponseDto> walletList = walletService.walletListByEmail(requestDto);
        assertThat(walletList.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("voucherId에 대해 보유중인 customer list 조회 로직 성공 검증")
    public void successFindCustomerListByVoucherId() {

        CustomerRequestDto customerRequestDtoA = customerEntityMapper.mapToRequestDto(name, email);
        customerService.create(customerRequestDtoA);

        CustomerRequestDto customerRequestDtoB = customerEntityMapper.mapToRequestDto("dev", "dev@gmail.com");
        customerService.create(customerRequestDtoB);

        VoucherRequestDto voucherRequestDto = voucherEntityMapper.entityToDtoWithDetails(VoucherType.FIXED, 2300L);
        voucherService.create(voucherRequestDto);

        List<VoucherResponseDto> list = voucherService.findAll();
        UUID voucherId = list.get(0).voucherId();

        WalletRequestDto walletRequestDtoA = walletEntityMapper.mapToDto(email, voucherId.toString());
        walletService.createWallet(walletRequestDtoA);

        WalletRequestDto walletRequestDtoB = walletEntityMapper.mapToDto("dev@gmail.com", voucherId.toString());
        walletService.createWallet(walletRequestDtoB);

        WalletRequestDto requestDto = walletEntityMapper.mapToDtoWithUUID(voucherId.toString());
        List<WalletResponseDto> walletList = walletService.walletListByVoucherId(requestDto);
        assertThat(walletList.size()).isEqualTo(2);
    }
}
