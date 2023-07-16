package com.programmers.springweekly.service.wallet;

import static org.assertj.core.api.Assertions.assertThat;

import com.programmers.springweekly.domain.customer.Customer;
import com.programmers.springweekly.domain.customer.CustomerType;
import com.programmers.springweekly.domain.voucher.Voucher;
import com.programmers.springweekly.domain.voucher.VoucherFactory;
import com.programmers.springweekly.domain.voucher.VoucherType;
import com.programmers.springweekly.dto.wallet.request.WalletCreateRequest;
import com.programmers.springweekly.dto.wallet.response.WalletResponse;
import com.programmers.springweekly.dto.wallet.response.WalletsResponse;
import com.programmers.springweekly.repository.customer.CustomerRepository;
import com.programmers.springweekly.repository.voucher.VoucherRepository;
import com.programmers.springweekly.service.WalletService;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@Transactional
@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
class WalletServiceTest {

    @Autowired
    private WalletService walletService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    Customer customer1;
    Customer customer2;
    Voucher voucher1;
    Voucher voucher2;

    @BeforeAll
    void beforeAll() {
        customer1 = Customer.builder()
                .customerId(UUID.randomUUID())
                .customerName("changhyeonh")
                .customerEmail("changhyeon1.h@kakao.com")
                .customerType(CustomerType.NORMAL)
                .build();

        customer2 = Customer.builder()
                .customerId(UUID.randomUUID())
                .customerName("changhyeonhh")
                .customerEmail("changhyeon2.h@kakao.com")
                .customerType(CustomerType.BLACKLIST)
                .build();

        voucher1 = VoucherFactory.createVoucher(UUID.randomUUID(), VoucherType.PERCENT, 50);
        voucher2 = VoucherFactory.createVoucher(UUID.randomUUID(), VoucherType.FIXED, 1000);

        customerRepository.save(customer1);
        customerRepository.save(customer2);

        voucherRepository.save(voucher1);
        voucherRepository.save(voucher2);
    }

    @AfterAll
    void afterAll() {
        customerRepository.deleteAll();
        voucherRepository.deleteAll();
    }

    @Test
    @DisplayName("고객에게 바우처를 할당할 수 있다.")
    void save() {
        // given
        WalletCreateRequest walletCreateRequest = WalletCreateRequest.builder()
                .customerId(customer1.getCustomerId())
                .voucherId(voucher1.getVoucherId())
                .build();

        // when
        walletService.save(walletCreateRequest);
        WalletsResponse walletsResponse = walletService.findAll();

        // then
        assertThat(walletsResponse.getWalletList().get(0).getCustomerId()).isEqualTo(customer1.getCustomerId());
    }

    @Test
    @DisplayName("고객 아이디로 고객에게 할당된 바우처를 조회할 수 있다.")
    void findByCustomerId() {
        // given
        WalletCreateRequest walletCreateRequest = WalletCreateRequest.builder()
                .customerId(customer1.getCustomerId())
                .voucherId(voucher1.getVoucherId())
                .build();

        // when
        walletService.save(walletCreateRequest);
        WalletResponse walletResponse = walletService.findByCustomerId(customer1.getCustomerId());

        // then
        assertThat(walletResponse.getVoucherId()).isEqualTo(walletCreateRequest.getVoucherId());
    }

    @Test
    @DisplayName("바우처 ID를 조회하여 해당 바우처가 할당된 고객 리스트를 조회할 수 있다.")
    void findByVoucherId() {
        // given
        WalletCreateRequest walletCreateRequest1 = WalletCreateRequest.builder()
                .customerId(customer1.getCustomerId())
                .voucherId(voucher1.getVoucherId())
                .build();
        WalletCreateRequest walletCreateRequest2 = WalletCreateRequest.builder()
                .customerId(customer2.getCustomerId())
                .voucherId(voucher1.getVoucherId())
                .build();

        walletService.save(walletCreateRequest1);
        walletService.save(walletCreateRequest2);

        // when
        WalletsResponse walletsResponse = walletService.findByVoucherId(voucher1.getVoucherId());

        // then
        assertThat(walletsResponse.getWalletList()).hasSize(2);
    }

    @Test
    @DisplayName("바우처 지갑 ID로 바우처 지갑을 삭제할 수 있다.")
    void deleteByWalletId() {
        // given
        WalletCreateRequest walletCreateRequest = WalletCreateRequest.builder()
                .customerId(customer1.getCustomerId())
                .voucherId(voucher1.getVoucherId())
                .build();

        walletService.save(walletCreateRequest);
        WalletsResponse walletsResponse = walletService.findAll();

        // when
        walletService.deleteByWalletId(walletsResponse.getWalletList().get(0).getWalletId());

        // then
        Assertions.assertThatThrownBy(() -> walletService.findByCustomerId(customer1.getCustomerId()))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("해당 고객에게 할당된 바우처가 없습니다.");
    }

    @Test
    @DisplayName("바우처 지갑에 저장된 리스트를 모두 불러올 수 있다.")
    void findAll() {
        // given
        WalletCreateRequest walletCreateRequest1 = WalletCreateRequest.builder()
                .customerId(customer1.getCustomerId())
                .voucherId(voucher1.getVoucherId())
                .build();
        WalletCreateRequest walletCreateRequest2 = WalletCreateRequest.builder()
                .customerId(customer2.getCustomerId())
                .voucherId(voucher1.getVoucherId())
                .build();

        walletService.save(walletCreateRequest1);
        walletService.save(walletCreateRequest2);

        // when
        WalletsResponse walletsResponse = walletService.findAll();

        // then
        assertThat(walletsResponse.getWalletList()).hasSize(2);
    }
}
