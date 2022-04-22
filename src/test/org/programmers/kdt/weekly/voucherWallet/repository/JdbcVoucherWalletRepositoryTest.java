package org.programmers.kdt.weekly.voucherWallet.repository;

//import static org.hamcrest.MatcherAssert.*;
//import static org.hamcrest.Matchers.*;
//
//import java.time.LocalDateTime;
//import java.util.UUID;
//import javax.sql.DataSource;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.programmers.kdt.weekly.customer.model.Customer;
//import org.programmers.kdt.weekly.voucher.model.Voucher;
//import org.programmers.kdt.weekly.voucher.model.VoucherType;
//import org.programmers.kdt.weekly.voucher.repository.JdbcTemplateVoucherRepository;
//import org.programmers.kdt.weekly.voucherWallet.model.VoucherWallet;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
//
//@SpringJUnitConfig
//@SpringBootTest
//class JdbcVoucherWalletRepositoryTest {
//    @Autowired
//    JdbcTemplateVoucherRepository jdbcTemplateVoucherRepository;
//
//    @Autowired
//    DataSource dataSource;
//
//    Voucher fixVoucher = voucherService.createVoucher(VoucherType.FIXED_AMOUNT_VOUCHER, 20000).get();
//    Voucher percentVoucher = voucherService.createVoucher(VoucherType.PERCENT_DISCOUNT_VOUCHER, 15).get();
//    Customer customer = customerService.createCustomer("test@test.com", "testUser");
//
//    @Test
//    @DisplayName("해당 CUSTOMER_ID 고객의 WALLET에 해당 VOUCHER_ID 바우처가 들어가야함")
//    void insert(){
//        //given
//        var customerId = customer.getCustomerId();
//        var voucherId = fixVoucher.getVoucherId();
//        var walletId = UUID.randomUUID();
//        var voucherWallet = new VoucherWallet(walletId, customerId, voucherId, LocalDateTime.now().withNano(0),LocalDateTime.now().withNano(0).plusDays(30L));
//        //when
//        voucherWalletRepository.insert(voucherWallet);
//        //then
//        var findVoucherWallet = voucherWalletRepository.findById(customerId,walletId);
//        assertThat(findVoucherWallet.isEmpty(), is(false));
//        assertThat(voucherWallet, samePropertyValuesAs(findVoucherWallet));
//    }
//}