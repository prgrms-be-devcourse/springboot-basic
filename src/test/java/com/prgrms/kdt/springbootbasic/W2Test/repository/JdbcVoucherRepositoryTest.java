package com.prgrms.kdt.springbootbasic.W2Test.repository;

import com.prgrms.kdt.springbootbasic.W2Test.Config;
import com.prgrms.kdt.springbootbasic.entity.voucher.FixedAmountVoucher;
import com.prgrms.kdt.springbootbasic.entity.voucher.PercentDiscountVoucher;
import com.prgrms.kdt.springbootbasic.entity.voucher.Voucher;
import com.prgrms.kdt.springbootbasic.repository.JdbcVoucherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(Config.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcVoucherRepositoryTest {

    @Autowired
    JdbcVoucherRepository jdbcVoucherRepository;

    @Autowired
    DataSource dataSource;

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(),100);
    Voucher percentAmountVoucher = new PercentDiscountVoucher(UUID.randomUUID(),10);

    @BeforeEach
    void setup() {
        jdbcTemplate.update("SET foreign_key_checks = 0;", Collections.emptyMap());
        jdbcTemplate.update("truncate wallet;", Collections.emptyMap());
        jdbcTemplate.update("truncate customers;", Collections.emptyMap());
        jdbcTemplate.update("truncate vouchers;", Collections.emptyMap());
        jdbcTemplate.update("SET foreign_key_checks = 1;", Collections.emptyMap());

        jdbcVoucherRepository.saveVoucher(fixedAmountVoucher);
        jdbcVoucherRepository.saveVoucher(percentAmountVoucher);
    }

    @Test
    public void saveVoucher(){
        var newFixed = new FixedAmountVoucher(UUID.randomUUID(),90);
        var newPercent = new PercentDiscountVoucher(UUID.randomUUID(),9);
        var insertFixed = jdbcVoucherRepository.saveVoucher(newFixed);
        var insertPercent = jdbcVoucherRepository.saveVoucher(newPercent);

        assertThat(insertFixed.get()).as("Voucher").isEqualToComparingFieldByField(newFixed);
        assertThat(insertPercent.get()).as("Voucher").isEqualToComparingFieldByField(newPercent);
    }


    @Test
    public void findById(){
        var findFixed = jdbcVoucherRepository.findById(fixedAmountVoucher.getVoucherId());
        var findPercent = jdbcVoucherRepository.findById(percentAmountVoucher.getVoucherId());

        assertThat(findFixed.get()).as("Voucher").isEqualToComparingFieldByField(fixedAmountVoucher);
        assertThat(findPercent.get()).as("Voucher").isEqualToComparingFieldByField(percentAmountVoucher);
    }

    @Test
    public void findByIdNotExist(){
        var findVoucher = jdbcVoucherRepository.findById(UUID.randomUUID());

        assertThat(findVoucher.isEmpty()).isTrue();
    }

    @Test
    public void getAllVouchers(){
        List<Voucher> assertList = new ArrayList<>();
        assertList.add(fixedAmountVoucher);
        assertList.add(percentAmountVoucher);

        List<Voucher> foundList = jdbcVoucherRepository.getAllVouchers();
        assertThat(foundList).usingRecursiveFieldByFieldElementComparator()
                .hasSameElementsAs(assertList);
    }

}