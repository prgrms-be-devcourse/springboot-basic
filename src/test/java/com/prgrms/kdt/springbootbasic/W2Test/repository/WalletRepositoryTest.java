package com.prgrms.kdt.springbootbasic.W2Test.repository;

import com.prgrms.kdt.springbootbasic.W2Test.Config;
import com.prgrms.kdt.springbootbasic.customer.entity.Customer;
import com.prgrms.kdt.springbootbasic.wallet.entity.Wallet;
import com.prgrms.kdt.springbootbasic.voucher.entity.FixedAmountVoucher;
import com.prgrms.kdt.springbootbasic.voucher.entity.PercentDiscountVoucher;
import com.prgrms.kdt.springbootbasic.voucher.entity.Voucher;
import com.prgrms.kdt.springbootbasic.customer.repository.CustomerRepository;
import com.prgrms.kdt.springbootbasic.voucher.repository.JdbcVoucherRepository;
import com.prgrms.kdt.springbootbasic.wallet.repository.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(Config.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WalletRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    JdbcVoucherRepository jdbcVoucherRepository;

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    DataSource dataSource;

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    Customer customer = new Customer(UUID.randomUUID(), "tester", "tester@gmail.com");
    Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(),100);
    Wallet wallet = new Wallet(UUID.randomUUID(),customer.getCustomerId(), voucher.getVoucherId());

    @BeforeEach
    void setup() {
        jdbcTemplate.update("SET foreign_key_checks = 0;", Collections.emptyMap());
        jdbcTemplate.update("truncate wallet;", Collections.emptyMap());
        jdbcTemplate.update("truncate customers;", Collections.emptyMap());
        jdbcTemplate.update("truncate vouchers;", Collections.emptyMap());
        jdbcTemplate.update("SET foreign_key_checks = 1;", Collections.emptyMap());

        customerRepository.saveCustomer(customer);
        jdbcVoucherRepository.saveVoucher(voucher);
        walletRepository.saveWallet(wallet);
    }

    @Test
    public void saveWallet(){
        var newCustomer = new Customer(UUID.randomUUID(),"tester2", "tester2@gmail.com");
        customerRepository.saveCustomer(newCustomer);
        Wallet newWallet = new Wallet(UUID.randomUUID(),newCustomer.getCustomerId(), voucher.getVoucherId());
        var insertResult = walletRepository.saveWallet(newWallet);

        assertThat(insertResult.get()).isEqualToComparingFieldByField(newWallet);
    }

    @Test
    public void findWalletById(){
        var findResult = walletRepository.findWalletById(wallet.getWalletId());

        assertThat(findResult.get()).as("Wallet").isEqualToComparingFieldByField(wallet);
    }

    @Test
    public void findWalletByVoucherId(){
        //Given
        List<Wallet> walletList = new ArrayList<>();
        walletList.add(wallet);
        var newCustomer = new Customer(UUID.randomUUID(),"tester2", "tester2@gmail.com");
        customerRepository.saveCustomer(newCustomer);
        Wallet newWallet = new Wallet(UUID.randomUUID(),newCustomer.getCustomerId(), voucher.getVoucherId());
        walletRepository.saveWallet(newWallet);
        walletList.add(newWallet);

        //When
        List<Wallet> findWalletList = walletRepository.findWalletByVoucherId(voucher.getVoucherId());

        //Then
        assertThat(findWalletList)
                .usingRecursiveFieldByFieldElementComparator()
                .hasSameElementsAs(walletList);
    }

    @Test
    public void findWalletByCustomerId(){
        //Given
        List<Wallet> walletList = new ArrayList<>();
        walletList.add(wallet);
        var newVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 10, LocalDateTime.now());
        jdbcVoucherRepository.saveVoucher(newVoucher);
        Wallet newWallet = new Wallet(UUID.randomUUID(), customer.getCustomerId(), newVoucher.getVoucherId());
        walletRepository.saveWallet(newWallet);
        walletList.add(newWallet);

        //When
        List<Wallet> findWalletList = walletRepository.findWalletByCustomerId(customer.getCustomerId());

        //Then
        assertThat(findWalletList)
                .usingRecursiveFieldByFieldElementComparator()
                .hasSameElementsAs(walletList);
    }

    @Test
    public void findVoucherByCustomerId(){
        //Given
        List<Voucher> voucherList = new ArrayList<>();
        voucherList.add(voucher);
        var newVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 10, LocalDateTime.now());
        jdbcVoucherRepository.saveVoucher(newVoucher);
        Wallet newWallet = new Wallet(UUID.randomUUID(), customer.getCustomerId(), newVoucher.getVoucherId());
        walletRepository.saveWallet(newWallet);
        voucherList.add(newVoucher);

        //When
        List<Voucher> findVoucherList = walletRepository.findVoucherByCustomerId(customer.getCustomerId());

        //Then
        assertThat(findVoucherList)
                .usingRecursiveFieldByFieldElementComparator()
                .hasSameElementsAs(voucherList);
    }

    @Test
    public void findCustomerByVoucherId(){
        //Given
        List<Customer> customerList = new ArrayList<>();
        customerList.add(customer);
        var newCustomer = new Customer(UUID.randomUUID(),"tester2", "tester2@gmail.com");
        customerRepository.saveCustomer(newCustomer);
        Wallet newWallet = new Wallet(UUID.randomUUID(), newCustomer.getCustomerId(), voucher.getVoucherId());
        walletRepository.saveWallet(newWallet);
        customerList.add(newCustomer);

        //When
        List<Customer> findCustomerList = walletRepository.findCustomerByVoucherId(voucher.getVoucherId());

        //Then
        assertThat(findCustomerList)
                .usingRecursiveFieldByFieldElementComparator()
                .hasSameElementsAs(customerList);
    }

    @Test
    public void getAllWallets(){
        //Given
        List<Wallet> walletList = new ArrayList<>();
        walletList.add(wallet);
        var newCustomer = new Customer(UUID.randomUUID(),"tester2", "tester2@gmail.com");
        customerRepository.saveCustomer(newCustomer);
        Wallet newWallet = new Wallet(UUID.randomUUID(), newCustomer.getCustomerId(), voucher.getVoucherId());
        walletRepository.saveWallet(newWallet);
        walletList.add(newWallet);

        //When
        List<Wallet> findWalletList = walletRepository.getAllWallets();

        //Then
        assertThat(findWalletList)
                .usingRecursiveFieldByFieldElementComparator()
                .hasSameElementsAs(walletList);
    }

    @Test
    public void deleteWallet(){
        var deleteResult = walletRepository.deleteWallets(wallet);

        assertThat(deleteResult).isTrue();
    }
}