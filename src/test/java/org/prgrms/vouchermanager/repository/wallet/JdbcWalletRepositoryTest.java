package org.prgrms.vouchermanager.repository.wallet;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.prgrms.vouchermanager.domain.customer.Customer;
import org.prgrms.vouchermanager.domain.voucher.Voucher;
import org.prgrms.vouchermanager.domain.wallet.Wallet;
import org.prgrms.vouchermanager.domain.wallet.WalletRequest;
import org.prgrms.vouchermanager.repository.customer.JdbcCustomerRepository;
import org.prgrms.vouchermanager.repository.voucher.JdbcVoucherRepository;
import org.prgrms.vouchermanager.testdata.CustomerData;
import org.prgrms.vouchermanager.testdata.VoucherData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class JdbcWalletRepositoryTest {
    private final JdbcVoucherRepository jdbcVoucherRepository;
    private final JdbcCustomerRepository jdbcCustomerRepository;
    private final JdbcWalletRepository jdbcWalletRepository;
    @Autowired
    JdbcWalletRepositoryTest(JdbcVoucherRepository jdbcVoucherRepository, JdbcCustomerRepository jdbcCustomerRepository, JdbcWalletRepository jdbcWalletRepository) {
        this.jdbcVoucherRepository = jdbcVoucherRepository;
        this.jdbcCustomerRepository = jdbcCustomerRepository;
        this.jdbcWalletRepository = jdbcWalletRepository;
    }
    @Nested
    @DisplayName("생성, 이메일 통해 조회")
    class save {
        @Test
        @DisplayName("지갑에 등록된 고객, 바우처 정보를 이메일을 통해 가져온다")
        void findByEmail() {
            Customer customer = jdbcCustomerRepository.save(CustomerData.getCustomer());
            Voucher voucher = jdbcVoucherRepository.save(VoucherData.getFixedVoucher());
            WalletRequest dto = WalletRequest.builder()
                    .customerEmail(customer.getEmail())
                    .voucher(voucher).build();
            jdbcWalletRepository.save(dto);

            Optional<Wallet> wallet = jdbcWalletRepository.findByEmail(customer.getEmail());

            assertThat(wallet).isPresent();
            assertThat(wallet.get().getVoucherId()).isEqualTo(voucher.getVoucherId());
        }
        @Test
        @DisplayName("지갑에 등록돼있지 않은 고객의 이메일을 통해 조회 시 null이 반환된다.")
        void findByIdNotExists(){
            Customer customer = CustomerData.getCustomer();
            jdbcWalletRepository.findByEmail(customer.getEmail());
        }
    }
    @Nested
    @DisplayName("바우처 통해 조회")
    class findByVoucher {
        @Test
        @DisplayName("특정 바우처를 가진 고객을 조회할 수 있다.")
        void findByVoucherExist() {
            Customer customer = jdbcCustomerRepository.save(CustomerData.getCustomer());
            Voucher voucher = jdbcVoucherRepository.save(VoucherData.getFixedVoucher());
            WalletRequest dto = WalletRequest.builder()
                    .customerEmail(customer.getEmail())
                    .voucher(voucher).build();
            jdbcWalletRepository.save(dto);

            Optional<Wallet> wallet = jdbcWalletRepository.findByVoucher(voucher);

            assertThat(wallet).isPresent();
        }
        @Test
        @DisplayName("특정 바우처를 가진 고객이 없을 시 null이 반환된다")
        void findByVoucherNotExist(){
            Voucher percentVoucher = VoucherData.getPercentVoucher();

            Optional<Wallet> wallet = jdbcWalletRepository.findByVoucher(percentVoucher);

            assertThat(wallet).isEmpty();
        }
    }

    @Nested
    @DisplayName("삭제")
    class delete {
        @Test
        @DisplayName("고객의 이메일을 해당 고객의 바우처를 제거할 수 있다.")
        void deleteByEmail(){
            Customer customer = jdbcCustomerRepository.save(CustomerData.getCustomer());
            Voucher voucher = jdbcVoucherRepository.save(VoucherData.getFixedVoucher());
            WalletRequest dto = WalletRequest.builder()
                    .customerEmail(customer.getEmail())
                    .voucher(voucher).build();
            jdbcWalletRepository.save(dto);

            jdbcWalletRepository.deleteByEmail(customer.getEmail());
            Optional<Wallet> wallet = jdbcWalletRepository.findByEmail(customer.getEmail());

            assertThat(wallet).isEmpty();
        }
        @Test
        @DisplayName("없는 고객의 이메일을 해당 고객의 바우처를 제거할 수 있다.")
        void deleteByEmailNotExist(){
            Customer customer = jdbcCustomerRepository.save(CustomerData.getCustomer());
            Voucher voucher = jdbcVoucherRepository.save(VoucherData.getFixedVoucher());
            WalletRequest dto = WalletRequest.builder()
                    .customerEmail(customer.getEmail())
                    .voucher(voucher).build();
            jdbcWalletRepository.save(dto);

            Optional<Wallet> wallet1 = jdbcWalletRepository.deleteByEmail(customer.getEmail());
            Optional<Wallet> wallet = jdbcWalletRepository.findByEmail(customer.getEmail());

            assertThat(wallet).isEmpty();
            assertThat(wallet1).isEmpty();
        }
    }
}