package org.prgrms.springbasic.repository.voucher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.springbasic.domain.customer.Customer;
import org.prgrms.springbasic.domain.voucher.Voucher;
import org.prgrms.springbasic.repository.TestDBContainer;
import org.prgrms.springbasic.repository.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static java.util.UUID.randomUUID;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.prgrms.springbasic.domain.customer.Customer.normalCustomer;
import static org.prgrms.springbasic.domain.voucher.Voucher.fixedVoucher;
import static org.prgrms.springbasic.domain.voucher.VoucherType.FIXED;
import static org.prgrms.springbasic.domain.voucher.VoucherType.PERCENT;

class VoucherJdbcRepositoryTest extends TestDBContainer {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    VoucherRepository voucherRepository;

    Customer customer = normalCustomer(randomUUID(), "new-user");

    Voucher voucher = fixedVoucher(randomUUID(), 10);

    @BeforeEach
    public void cleanup() {
        customerRepository.deleteCustomers();
        voucherRepository.deleteVouchers();
    }

    @Test
    @DisplayName("바우처를 저장하면 반환되는 객체는 저장한 바우처과 같아야 하고 레포지토리의 사이즈는 1이 되어야 한다.")
    void testSave() {
        var newVoucher = voucherRepository.save(voucher);

        assertThat(newVoucher, notNullValue());
        assertThat(newVoucher, samePropertyValuesAs(voucher));
        assertThat(voucherRepository.countVouchers(), is(1));
    }

    @Test
    @DisplayName("중복되는 아이디를 가진 바우처를 저장할 경우 예외가 발생한다.")
    void testDuplicatedVoucher() {
        var newVoucher = voucherRepository.save(voucher);

        assertThrows(DataAccessException.class, () -> voucherRepository.save(newVoucher));
    }

    @Test
    @DisplayName("바우처 아이디로 바우처를 조회할 수 있다.")
    void testFindByVoucherId() {
        var savedVoucher = voucherRepository.save(voucher);

        Optional<Voucher> retrievedVoucher = voucherRepository.findByVoucherId(savedVoucher.getVoucherId());

        assertThat(retrievedVoucher.isEmpty(), is(false));
        assertThat(retrievedVoucher.get(), samePropertyValuesAs(voucher));
    }

    @Test
    @DisplayName("고객이 어떤 바우처를 보유하고 있는지 조회할 수 있다.")
    void testFindByCustomerId() {
        var savedCustomer = customerRepository.save(customer);

        var percentVoucher = Voucher.percentVoucher(randomUUID(), 10);
        percentVoucher.assignToCustomer(savedCustomer.getCustomerId());
        voucherRepository.save(percentVoucher);

        List<Voucher> retrievedVoucher = voucherRepository.findByCustomerId(customer.getCustomerId());

        assertThat(retrievedVoucher.isEmpty(), is(false));
        assertThat(retrievedVoucher.get(0), samePropertyValuesAs(percentVoucher));
    }

    @Test
    @DisplayName("바우처 아이디로 바우처를 찾지 못하는 경우 빈 객체가 반환되어야 한다.")
    void testFindByIdException() {
        Optional<Voucher> retrievedVoucher = voucherRepository.findByVoucherId(randomUUID());

        assertThat(retrievedVoucher.isEmpty(), is(true));
        assertThrows(NoSuchElementException.class, retrievedVoucher::get);
    }

    @Test
    @DisplayName("모든 바우처을 조회했을 때 저장한 바우처 객체를 가지고 있어야 하며 사이즈가 같아야 한다.")
    void testFindAll() {
        Voucher newVoucher1 = fixedVoucher(randomUUID(), 10);
        Voucher newVoucher2 = Voucher.percentVoucher(randomUUID(), 10);
        Voucher newVoucher3 = fixedVoucher(randomUUID(), 10);
        Voucher newVoucher4 = Voucher.percentVoucher(randomUUID(), 10);
        Voucher newVoucher5 = fixedVoucher(randomUUID(), 10);

        voucherRepository.save(newVoucher1);
        voucherRepository.save(newVoucher2);
        voucherRepository.save(newVoucher3);
        voucherRepository.save(newVoucher4);
        voucherRepository.save(newVoucher5);

        var vouchers = voucherRepository.findVouchers();

        assertThat(vouchers.size(), is(voucherRepository.countVouchers()));
        assertThat(vouchers.get(0), samePropertyValuesAs(newVoucher1));
        assertThat(vouchers, containsInRelativeOrder(samePropertyValuesAs(newVoucher1), samePropertyValuesAs(newVoucher2), samePropertyValuesAs(newVoucher3), samePropertyValuesAs(newVoucher4), samePropertyValuesAs(newVoucher5)));
    }

    @Test
    @DisplayName("지갑 테스트: 특정 고객에게 바우처를 할당할 수 있다.")
    void testFindWallet() {
        var customer1 = normalCustomer(randomUUID(), "voucher-user");
        var voucher1 = fixedVoucher(randomUUID(), 10);
        voucher1.assignToCustomer(customer1.getCustomerId()); //할당

        var customer2 = normalCustomer(randomUUID(), "no-voucher-user");
        var voucher2 = fixedVoucher(randomUUID(), 10); //할당 안함

        customerRepository.save(customer1);
        voucherRepository.save(voucher1);

        customerRepository.save(customer2);
        voucherRepository.save(voucher2);

        var wallets = voucherRepository.findWallets();
        var wallet = wallets.get(0);

        assertThat(wallets.size(), is(1));
        assertThat(wallet.getCustomerId(), is(customer1.getCustomerId()));
        assertThat(wallet.getVoucherId(), is(voucher1.getVoucherId()));
    }

    @Test
    @DisplayName("입력된 바우처 정보대로 업데이트가 잘 되어야 한다.")
    void testUpdateVoucher() throws InterruptedException {
        var newVoucher = fixedVoucher(randomUUID(), 10L);
        voucherRepository.save(newVoucher);

        Thread.sleep(1000);

        var updatedVoucher = newVoucher.update(PERCENT, 20L, null);
        var retrievedVoucher = voucherRepository.update(updatedVoucher);

        assertThat(retrievedVoucher, notNullValue());
        assertThat(retrievedVoucher.getVoucherId(), is(newVoucher.getVoucherId()));
        assertThat(retrievedVoucher.getVoucherType(), not(is(FIXED)));
        assertThat(retrievedVoucher.getDiscountInfo(), is(20L));
        assertThat(retrievedVoucher.getModifiedAt(), notNullValue());
    }

    @Test
    @DisplayName("바우처 아이디로 바우처를 삭제할 수 있다.")
    void testDeleteByVoucherId() {
        var newVoucher = fixedVoucher(randomUUID(), 10L);
        var savedVoucher = voucherRepository.save(newVoucher);

        Optional<Voucher> retrievedVoucher = voucherRepository.findByVoucherId(savedVoucher.getVoucherId());

        assertThat(retrievedVoucher.isEmpty(), is(false));

        voucherRepository.deleteByVoucherId(savedVoucher.getVoucherId());

        Optional<Voucher> deletedVoucher = voucherRepository.findByVoucherId(newVoucher.getVoucherId());

        assertThat(deletedVoucher.isEmpty(), is(true));
    }

    @Test
    @DisplayName("특정 회원의 바우처를 삭제할 수 있다.")
    void testDeleteByCustomerId() {
        var customer = normalCustomer(randomUUID(), "voucher-user");
        var voucher = fixedVoucher(randomUUID(), 10);
        voucher.assignToCustomer(customer.getCustomerId());

        customerRepository.save(customer);
        voucherRepository.save(voucher);

        var deleted = voucherRepository.deleteByCustomerId(customer.getCustomerId());

        assertThat(deleted, is(true));

        Optional<Voucher> retrievedVoucher = voucherRepository.findByVoucherId(voucher.getVoucherId());

        assertThat(retrievedVoucher.isEmpty(), is(true));
    }

    @Test
    @DisplayName("모든 바우처을 삭제하고 나면 레객포지토리의 사이즈는 0이 되어야한다.")
    void testDeleteAll() {
        voucherRepository.save(fixedVoucher(randomUUID(), 10));
        voucherRepository.save(fixedVoucher(randomUUID(), 10));
        voucherRepository.save(fixedVoucher(randomUUID(), 10));

        assertThat(voucherRepository.countVouchers(), greaterThan(0));

        voucherRepository.deleteVouchers();

        assertThat(voucherRepository.findVouchers().isEmpty(), is(true));
        assertThat(voucherRepository.countVouchers(), is(0));
    }

}