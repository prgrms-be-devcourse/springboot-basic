package com.prgrms.voucher_manage.domain.wallet.repository;

import com.prgrms.voucher_manage.domain.customer.entity.Customer;
import com.prgrms.voucher_manage.domain.voucher.entity.FixedAmountVoucher;
import com.prgrms.voucher_manage.domain.voucher.entity.Voucher;
import com.prgrms.voucher_manage.domain.wallet.entity.Wallet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static com.prgrms.voucher_manage.domain.customer.entity.CustomerType.BLACK;
import static com.prgrms.voucher_manage.domain.customer.entity.CustomerType.NORMAL;
import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@ActiveProfiles({"prod", "test"})
@Import(JdbcWalletRepository.class)
class JdbcWalletRepositoryTest {
    @Autowired
    private JdbcWalletRepository repository;

    @Test
    @DisplayName("wallet을 저장할 수 있다.")
    public void jdbcWalletRepository_test() {
        //given
        Customer customer = new Customer("까치", BLACK);
        Voucher voucher = new FixedAmountVoucher(1000L);
        Wallet wallet = new Wallet(customer.getId(), voucher.getId());
        //when
        repository.save(wallet);
        //then
        assertThat(wallet).isNotNull();
        assertThat(customer.getId()).isEqualTo(wallet.getCustomerId());
        assertThat(voucher.getId()).isEqualTo(wallet.getVoucherId());
    }

    @Test
    @DisplayName("wallet을 customerId로 조회할 수 있다.")
    public void jdbcWalletRepository_findByCustomerId() {
        //given
        Customer customer = new Customer("까치", BLACK);

        Voucher voucher1 = new FixedAmountVoucher(1000L);
        Wallet wallet1 = new Wallet(customer.getId(), voucher1.getId());

        Voucher voucher2 = new FixedAmountVoucher(2000L);
        Wallet wallet2 = new Wallet(customer.getId(), voucher2.getId());

        repository.save(wallet1);
        repository.save(wallet2);

        //when
        List<Wallet> wallets = repository.findByCustomerId(customer.getId());
        //then
        assertThat(wallets.size()).isEqualTo(2);
        assertThat(List.of(wallet1.getVoucherId(), wallet2.getVoucherId()))
                .containsOnly(voucher1.getId(), voucher2.getId());
    }

    @Test
    @DisplayName("voucher id로 고객을 조회할 수 있다.")
    public void jdbcWalletRepository_findByVoucherId() {
        //given
        Voucher voucher = new FixedAmountVoucher(1000L);

        Customer customer1 = new Customer("까치", BLACK);
        Wallet wallet1 = new Wallet(customer1.getId(), voucher.getId());

        Customer customer2 = new Customer("까마귀", NORMAL);
        Wallet wallet2 = new Wallet(customer2.getId(), voucher.getId());

        repository.save(wallet1);
        repository.save(wallet2);

        //when
        List<Wallet> wallets = repository.findByVoucherId(voucher.getId());

        //then
        assertThat(wallets.size()).isEqualTo(2);
        assertThat(List.of(wallet1.getCustomerId(), wallet2.getCustomerId()))
                .containsOnly(customer1.getId(), customer2.getId());
    }

    @Test
    @DisplayName("wallet을 id로 삭제할 수 있다.")
    public void jdbcWalletRepository_deleteById() {
        //given
        Customer customer = new Customer("까치", BLACK);
        Voucher voucher = new FixedAmountVoucher(1000L);
        Wallet wallet = new Wallet(customer.getId(), voucher.getId());

        repository.save(wallet);

        //when
        repository.delete(wallet);

        //then
        assertThat(repository.findByCustomerId(customer.getId())).isEmpty();
    }

}
