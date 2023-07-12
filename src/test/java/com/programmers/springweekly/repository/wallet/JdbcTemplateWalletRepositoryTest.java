package com.programmers.springweekly.repository.wallet;

import static org.assertj.core.api.Assertions.assertThat;

import com.programmers.springweekly.domain.customer.Customer;
import com.programmers.springweekly.domain.customer.CustomerType;
import com.programmers.springweekly.domain.voucher.Voucher;
import com.programmers.springweekly.domain.voucher.VoucherFactory;
import com.programmers.springweekly.domain.voucher.VoucherType;
import com.programmers.springweekly.domain.wallet.Wallet;
import com.programmers.springweekly.repository.customer.CustomerRepository;
import com.programmers.springweekly.repository.voucher.VoucherRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class JdbcTemplateWalletRepositoryTest {

    @Autowired
    private WalletRepository walletRepository;

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
                .customerName("changhyeon1")
                .customerEmail("changhyeon1.h@kakao.com")
                .customerType(CustomerType.NORMAL)
                .build();

        customer2 = Customer.builder()
                .customerId(UUID.randomUUID())
                .customerName("changhyeon2")
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
    @DisplayName("바우처 지갑을 저장하고 고객 ID로 할당된 바우처 지갑을 가져올 수 있다.")
    void saveAndFindByCustomerId() {
        // given
        UUID walletId = UUID.randomUUID();
        Wallet wallet = new Wallet(walletId, customer1.getCustomerId(), voucher1.getVoucherId());
        walletRepository.save(wallet);

        // when
        Wallet walletActual = walletRepository.findByCustomerId(customer1.getCustomerId())
                .orElseThrow(() -> new NoSuchElementException("찾는 바우처 지갑이 없습니다."));

        // then
        assertThat(walletActual).usingRecursiveComparison().isEqualTo(wallet);
    }

    @Test
    @DisplayName("바우처 지갑을 여러 개 저장하고 특정 바우처가 할당된 고객들을 조회할 수 있다.")
    void saveAndFindByVoucherId() {
        // given
        UUID walletId1 = UUID.randomUUID();
        UUID walletId2 = UUID.randomUUID();
        Wallet wallet1 = new Wallet(walletId1, customer1.getCustomerId(), voucher1.getVoucherId());
        Wallet wallet2 = new Wallet(walletId2, customer2.getCustomerId(), voucher1.getVoucherId());
        walletRepository.save(wallet1);
        walletRepository.save(wallet2);

        // when
        List<Wallet> walletListActual = walletRepository.findByVoucherId(voucher1.getVoucherId());

        // then
        assertThat(walletListActual).usingRecursiveFieldByFieldElementComparator().isEqualTo(List.of(wallet1, wallet2));
    }

    @Test
    @DisplayName("지갑 ID로 바우처 지갑을 삭제할 수 있다.")
    void deleteByWalletId() {
        // given
        UUID walletId = UUID.randomUUID();
        Wallet wallet = new Wallet(walletId, customer1.getCustomerId(), voucher1.getVoucherId());
        walletRepository.save(wallet);

        // when
        walletRepository.deleteByWalletId(walletId);
        Optional<Wallet> walletActual = walletRepository.findByCustomerId(customer1.getCustomerId());
        // then

        assertThat(walletActual).isEmpty();
    }

    @Test
    @DisplayName("저장된 바우처 지갑을 모두 조회할 수 있다.")
    void findAll() {
        // given
        UUID walletId1 = UUID.randomUUID();
        UUID walletId2 = UUID.randomUUID();
        Wallet wallet1 = new Wallet(walletId1, customer1.getCustomerId(), voucher1.getVoucherId());
        Wallet wallet2 = new Wallet(walletId2, customer2.getCustomerId(), voucher1.getVoucherId());
        walletRepository.save(wallet1);
        walletRepository.save(wallet2);

        // when
        List<Wallet> walletListActual = walletRepository.findAll();

        // then
        assertThat(walletListActual).usingRecursiveFieldByFieldElementComparator().isEqualTo(List.of(wallet1, wallet2));
    }
}
