package org.prgrms.kdt.voucher;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.prgrms.kdt.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.repository.VoucherJdbcRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class VoucherJdbcRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private VoucherJdbcRepository voucherJdbcRepository;

    @Test
    public void givenVoucherId_whenFindById_thenReturnVoucher() {
        // Mock the behavior of JdbcTemplate.queryForObject to return a Voucher
        when(jdbcTemplate.queryForObject(any(String.class), any(RowMapper.class), any(Object.class))).thenReturn(
                new FixedAmountVoucher(UUID.randomUUID(), 50)
        );

        UUID voucherId = UUID.randomUUID();
        Optional<Voucher> voucher = voucherJdbcRepository.findById(voucherId);
        assertThat(voucher).isPresent();
    }

    @Test
    public void givenEmptyVoucherId_whenFindById_thenReturnEmptyOptional() {
        // Mock the behavior of JdbcTemplate.queryForObject to return EmptyResultDataAccessException
        when(jdbcTemplate.queryForObject(any(String.class), any(RowMapper.class), any(Object.class)))
                .thenThrow(new EmptyResultDataAccessException(1));

        UUID voucherId = UUID.randomUUID();
        Optional<Voucher> voucher = voucherJdbcRepository.findById(voucherId);
        assertThat(voucher).isEmpty();
    }

    @Test
    public void givenVoucher_whenSave_thenReturnSavedVoucher() {
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 50);
        when(jdbcTemplate.update(any(String.class), any(Object.class))).thenReturn(1);

        Voucher savedVoucher = voucherJdbcRepository.save(fixedAmountVoucher);
        assertThat(savedVoucher).isEqualTo(fixedAmountVoucher);
    }

    @Test
    public void givenVoucher_whenSave_thenThrowRuntimeException() {
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 50);
        when(jdbcTemplate.update(any(String.class), any(Object.class))).thenReturn(0);

        assertThatThrownBy(() -> voucherJdbcRepository.save(fixedAmountVoucher))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Nothing was inserted");
    }

    @Test
    public void whenFindAll_thenReturnListOfVouchers() {
        // Mock the behavior of JdbcTemplate.query to return a list of Vouchers
        when(jdbcTemplate.query(any(String.class), any(RowMapper.class))).thenReturn(
                List.of(new FixedAmountVoucher(UUID.randomUUID(), 50))
        );

        List<Voucher> vouchers = voucherJdbcRepository.findAll();
        assertThat(vouchers).hasSize(1);
    }
}
