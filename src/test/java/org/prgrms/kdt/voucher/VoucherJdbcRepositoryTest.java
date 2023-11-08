package org.prgrms.kdt.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.kdt.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.repository.VoucherJdbcRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VoucherJdbcRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private VoucherJdbcRepository voucherJdbcRepository;

    @Test
    @DisplayName("id로 바우처를 찾을 수 있어야한다.")
    public void givenVoucherId_whenFindById_thenReturnVoucher() {
        // Mock the behavior of JdbcTemplate.queryForObject to return a Voucher
        when(jdbcTemplate.queryForObject(any(String.class), any(RowMapper.class), any(Object.class))).thenReturn(
                new FixedAmountVoucher(UUID.randomUUID(), 50, LocalDateTime.now())
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
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 50, LocalDateTime.now());
        when(jdbcTemplate.update(any(String.class), any(Object.class))).thenReturn(1);

        Voucher savedVoucher = voucherJdbcRepository.save(fixedAmountVoucher);
        assertThat(savedVoucher).isEqualTo(fixedAmountVoucher);
    }

    @Test
    public void givenVoucher_whenSave_thenThrowRuntimeException() {
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 50, LocalDateTime.now());
        when(jdbcTemplate.update(any(String.class), any(Object.class))).thenReturn(0);

        assertThatThrownBy(() -> voucherJdbcRepository.save(fixedAmountVoucher))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Nothing was inserted");
    }

    @Test
    public void whenFindAll_thenReturnListOfVouchers() {
        // Mock the behavior of JdbcTemplate.query to return a list of Vouchers
        when(jdbcTemplate.query(any(String.class), any(RowMapper.class))).thenReturn(
                List.of(new FixedAmountVoucher(UUID.randomUUID(), 50, LocalDateTime.now()))
        );

        List<Voucher> vouchers = voucherJdbcRepository.findAll();
        assertThat(vouchers).hasSize(1);
    }
}
