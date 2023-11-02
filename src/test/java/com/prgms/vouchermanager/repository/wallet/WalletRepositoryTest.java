package com.prgms.vouchermanager.repository.wallet;

import com.prgms.vouchermanager.domain.customer.Customer;
import com.prgms.vouchermanager.domain.voucher.FixedAmountVoucher;
import com.prgms.vouchermanager.domain.voucher.Voucher;
import com.prgms.vouchermanager.domain.wallet.Wallet;
import com.prgms.vouchermanager.repository.customer.CustomerRepository;
import com.prgms.vouchermanager.repository.voucher.VoucherRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@Transactional
public class WalletRepositoryTest {

    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private VoucherRepository voucherRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @DisplayName("save()실행시 wallets이 참조하는 customer_id와 voucher_id가 존재한다면, 정상적으로 저장된다. ")
    void saveWalletSuccess() {

        //given
        UUID id = UUID.randomUUID();
        Voucher voucher = new FixedAmountVoucher(id, 100000);
        Customer customer = new Customer(null, "ko", "kp@naver.com", true);
        voucherRepository.save(voucher);
        Customer savedCustomer = customerRepository.save(customer);

        Wallet wallet = new Wallet(6L, savedCustomer.getId(), id);

        //when
        Wallet savedWallet = walletRepository.save(wallet);

        //then
        Assertions.assertThat(wallet).isEqualTo(savedWallet);
    }


    @Test
    @DisplayName("save()실행시 wallets이 참조하는 customer_id와 voucher_id가 존재하지 않는다면, 예외가 발생한다. ")
    void saveWalletFail() {

        //given
        UUID id = UUID.randomUUID();
        Voucher voucher = new FixedAmountVoucher(id, 100000);
        Customer customer = new Customer(null, "ko", "kp@naver.com", true);
        Wallet wallet = new Wallet(10L, 1214L, UUID.randomUUID()); //존재하지 않는 참조데이터

        //when
        voucherRepository.save(voucher);
        customerRepository.save(customer);

        //then
        Assertions.assertThatThrownBy(() -> walletRepository.save(wallet)).isInstanceOf(DataIntegrityViolationException.class);
    }


    @Test
    @DisplayName("customer_id에 해당하는 쿠폰 조회에 성공한다.")
    void findByCustomerIdSuccess() {

        //given
        UUID id = UUID.randomUUID();
        Voucher voucher = new FixedAmountVoucher(id, 100000);
        Customer customer = new Customer(10L, "ko", "kp@naver.com", true);
        voucherRepository.save(voucher);
        customerRepository.save(customer);
        Wallet wallet = new Wallet(6L, customer.getId(), id);

        //when
        walletRepository.save(wallet);
        List<Wallet> wallets = walletRepository.findByCustomerId(customer.getId());

        //then
        Assertions.assertThat(wallets).isNotNull();
    }
    @Test
    @DisplayName("customer_id에 해당하는 쿠폰 조회에 실패한다.")
    void findByCustomerIdFail() {

        //given
        UUID id = UUID.randomUUID();
        Voucher voucher = new FixedAmountVoucher(id, 100000);
        Customer customer = new Customer(10L, "ko", "kp@naver.com", true);
        voucherRepository.save(voucher);
        customerRepository.save(customer);
        Wallet wallet = new Wallet(6L, customer.getId(), id);

        //when
        walletRepository.save(wallet);
        List<Wallet> wallets = walletRepository.findByCustomerId(123132L);

        //then
        Assertions.assertThat(wallets).isEmpty();
    }

    @Test
    @DisplayName("voucher_id에 해당하는 고객 조회에 성공한다.")
    void findByVoucherIdSuccess() {

        //given
        UUID id = UUID.randomUUID();
        Voucher voucher = new FixedAmountVoucher(id, 100000);
        Customer customer = new Customer(10L, "ko", "kp@naver.com", true);
        voucherRepository.save(voucher);
        customerRepository.save(customer);
        Wallet wallet = new Wallet(6L, customer.getId(), id);

        //when
        walletRepository.save(wallet);
        Optional<Wallet> findByVoucherIdwallet = walletRepository.findByVoucherId(id);

        //then
        Assertions.assertThat(findByVoucherIdwallet.get().getId()).isEqualTo(wallet.getId());
        Assertions.assertThat(findByVoucherIdwallet.get().getVoucherId()).isEqualTo(wallet.getVoucherId());
        Assertions.assertThat(findByVoucherIdwallet.get().getCustomerId()).isEqualTo(wallet.getCustomerId());
    }

    @Test
    @DisplayName("voucher_id에 해당하는 고객 조회에 실패한다.")
    void findByVoucherIdFail() {

        //given
        UUID id = UUID.randomUUID();
        Voucher voucher = new FixedAmountVoucher(id, 100000);
        Customer customer = new Customer(10L, "ko", "kp@naver.com", true);
        voucherRepository.save(voucher);
        customerRepository.save(customer);
        Wallet wallet = new Wallet(6L, customer.getId(), id);

        //when
        walletRepository.save(wallet);
        Optional<Wallet> findByVoucherIdwallet = walletRepository.findByVoucherId(UUID.randomUUID());

        //then
        Assertions.assertThat(findByVoucherIdwallet).isEmpty();
    }

    @Test
    @DisplayName("deleteByCustomerId()실행 시 지갑 정보를 성공적으로 삭제한다..")
    void deleteByCustomerIdSuccess() {

        //given
        UUID id = UUID.randomUUID();
        Voucher voucher = new FixedAmountVoucher(id, 100000);
        Customer customer = new Customer(10L, "ko", "kp@naver.com", true);
        voucherRepository.save(voucher);
        customerRepository.save(customer);
        Wallet wallet = new Wallet(6L, customer.getId(), id);
        walletRepository.save(wallet);

        //when
        walletRepository.deleteByCustomerId(customer.getId());

        //then
        List<Wallet> wallets = walletRepository.findByCustomerId(customer.getId());
        Assertions.assertThat(wallets).isEmpty();
    }

}
