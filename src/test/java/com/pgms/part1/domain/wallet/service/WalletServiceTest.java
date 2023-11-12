package com.pgms.part1.domain.wallet.service;

import com.pgms.part1.domain.customer.entity.Customer;
import com.pgms.part1.domain.customer.entity.CustomerBuilder;
import com.pgms.part1.domain.voucher.dto.VoucherWalletResponseDtos;
import com.pgms.part1.domain.voucher.entity.Voucher;
import com.pgms.part1.domain.voucher.entity.VoucherDiscountType;
import com.pgms.part1.domain.wallet.dto.WalletCreateRequestDto;
import com.pgms.part1.domain.wallet.entity.Wallet;
import com.pgms.part1.domain.wallet.repository.WalletRepository;
import com.pgms.part1.exception.VoucherApplicationException;
import com.pgms.part1.util.keygen.KeyGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest
public class WalletServiceTest {

    @InjectMocks
    private WalletService walletService;

    @Mock
    private WalletRepository walletRepository;
    
    @BeforeEach
    void setUp() {
        walletService = new WalletService(walletRepository, new KeyGenerator());
    }

    @Test
    void wallet_추가_성공(){
        // given
        Customer customer = new CustomerBuilder().id(1L).email("test").name("test").build();
        Voucher voucher = Voucher.newVocher(2L, 10, VoucherDiscountType.FIXED_AMOUNT_DISCOUNT);
        WalletCreateRequestDto walletCreateRequestDto = new WalletCreateRequestDto(voucher.getId(), customer.getId());

        // when
        walletService.addWallet(walletCreateRequestDto);

        // then
        Mockito.verify(walletRepository, Mockito.times((1))).addWallet(any(Wallet.class));
    }

    @Test
    void wallet_추가_voucher중복_실패(){
        // given
        Customer customer = new CustomerBuilder().id(1L).email("test").name("test").build();
        Voucher voucher = Voucher.newVocher(2L, 10, VoucherDiscountType.FIXED_AMOUNT_DISCOUNT);
        WalletCreateRequestDto walletCreateRequestDto = new WalletCreateRequestDto( customer.getId(), voucher.getId());
        when(walletRepository.findVouchersByCustomerId(anyLong())).thenReturn(List.of(new VoucherWalletResponseDtos(voucher.getId(), voucher.getDiscount(), voucher.getVoucherDiscountType(), 0L)));

        // expected
        Assertions.assertThrows(VoucherApplicationException.class,() ->  walletService.addWallet(walletCreateRequestDto));
    }
}
