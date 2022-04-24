package org.prgms.voucheradmin.domain.voucherwallet.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgms.voucheradmin.domain.customer.dao.customer.CustomerRepository;
import org.prgms.voucheradmin.domain.customer.entity.Customer;
import org.prgms.voucheradmin.domain.voucher.dao.VoucherRepository;
import org.prgms.voucheradmin.domain.voucher.entity.FixedAmountVoucher;
import org.prgms.voucheradmin.domain.voucher.entity.Voucher;
import org.prgms.voucheradmin.domain.voucherwallet.dao.VoucherWalletRepository;
import org.prgms.voucheradmin.domain.voucherwallet.dto.VoucherWalletReqDto;
import org.prgms.voucheradmin.domain.voucherwallet.entity.VoucherWallet;
import org.prgms.voucheradmin.global.exception.customexception.CustomerNotFoundException;
import org.prgms.voucheradmin.global.exception.customexception.VoucherNotFoundException;
import org.prgms.voucheradmin.global.exception.customexception.VoucherWalletNotFoundException;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VoucherWalletServiceTest {
    @InjectMocks
    VoucherWalletService voucherWalletService;

    @Mock
    CustomerRepository customerRepository;

    @Mock
    VoucherRepository voucherRepository;

    @Mock
    VoucherWalletRepository voucherWalletRepository;

    VoucherWalletReqDto voucherWalletReqDto = new VoucherWalletReqDto(UUID.randomUUID());
    Customer customer = Customer.builder()
            .customerId(UUID.randomUUID())
            .name("a")
            .email("a@test.com")
            .createdAt(LocalDateTime.now())
            .build();
    Voucher voucher = new FixedAmountVoucher(voucherWalletReqDto.getVoucherId(), 1000);
    VoucherWallet voucherWallet = new VoucherWallet(UUID.randomUUID(), customer.getCustomerId(), voucher.getVoucherId());

    @Test
    @DisplayName("바우처 지갑 생성 고객 예외 톄스트")
    void testCreateVoucherWalletCustomerException() {
        try {
            when(customerRepository.findById(customer.getCustomerId())).thenThrow(new CustomerNotFoundException(customer.getCustomerId()));

            voucherWalletService.createVoucherWallet(customer.getCustomerId(), voucherWalletReqDto.getVoucherId());
        }catch(CustomerNotFoundException e) {
            verify(voucherRepository, never()).findById(voucherWalletReqDto.getVoucherId());
        }
    }

    @Test
    @DisplayName("바우처 지갑 생성 바우처 예외 톄스트")
    void testCreateVoucherWalletVoucherException() {
        try {
            when(customerRepository.findById(customer.getCustomerId())).thenReturn(Optional.of(customer));
            when(voucherRepository.findById(voucherWalletReqDto.getVoucherId())).thenThrow(new VoucherNotFoundException(voucherWalletReqDto.getVoucherId()));

            voucherWalletService.createVoucherWallet(customer.getCustomerId(), voucherWalletReqDto.getVoucherId());
        }catch(VoucherNotFoundException e) {
            verify(voucherWalletRepository, never()).create(any());
        }
    }

    @Test
    @DisplayName("바우처 지갑 생성 톄스트")
    void testCreateVoucherWallet() {
        when(customerRepository.findById(customer.getCustomerId())).thenReturn(Optional.of(customer));
        when(voucherRepository.findById(voucherWalletReqDto.getVoucherId())).thenReturn(Optional.of(voucher));

        voucherWalletService.createVoucherWallet(customer.getCustomerId(), voucherWalletReqDto.getVoucherId());

        verify(voucherWalletRepository).create(any());
    }

    @Test
    @DisplayName("할당된 바우처 조회 예외 테스트")
    void testGetAllocatedVoucherException() {
        try {
            when(customerRepository.findById(customer.getCustomerId())).thenThrow(new CustomerNotFoundException(customer.getCustomerId()));

            voucherWalletService.getAllocatedVouchers(customer.getCustomerId());
        }catch(CustomerNotFoundException e) {
            verify(voucherRepository, never()).findAllocatedVouchers(customer.getCustomerId());
        }
    }

    @Test
    @DisplayName("할당된 바우처 조회 테스트")
    void testGetAllocatedVoucher() {
        when(customerRepository.findById(customer.getCustomerId())).thenReturn(Optional.of(customer));

        voucherWalletService.getAllocatedVouchers(customer.getCustomerId());

        verify(voucherRepository).findAllocatedVouchers(customer.getCustomerId());
    }

    @Test
    @DisplayName("바우처 보유 고객 조회 예외 테스트")
    void testGetVoucherOwnersException() {;
        try {
            when(voucherRepository.findById(voucher.getVoucherId())).thenThrow(new VoucherNotFoundException(voucher.getVoucherId()));

            voucherWalletService.getVoucherOwners(voucher.getVoucherId());
        }catch(VoucherNotFoundException e) {
            verify(customerRepository, never()).findVoucherOwners(voucher.getVoucherId());
        }
    }

    @Test
    @DisplayName("바우처 보유 고객 조회 테스트")
    void testGetVoucherOwners() {
        when(voucherRepository.findById(voucher.getVoucherId())).thenReturn(Optional.of(voucher));

        voucherWalletService.getVoucherOwners(voucher.getVoucherId());

        verify(customerRepository).findVoucherOwners(voucher.getVoucherId());
    }

    @Test
    @DisplayName("바우처 지갑 삭제 예외 테스트")
    void testDeleteVoucherWalletException() {
        try {
            when(voucherWalletRepository.findByCustomerIdAndVoucherId(customer.getCustomerId(), voucher.getVoucherId())).thenThrow(new VoucherWalletNotFoundException(customer.getCustomerId(), voucher.getVoucherId()));

            voucherWalletService.deleteVoucherWallet(customer.getCustomerId(), voucher.getVoucherId());
        }catch(VoucherWalletNotFoundException e) {
            verify(voucherWalletRepository, never()).deleteVoucherWallet(voucherWallet);
        }
    }

    @Test
    @DisplayName("바우처 지갑 삭제 테스트")
    void testDeleteVoucherWallet() {
        when(voucherWalletRepository.findByCustomerIdAndVoucherId(customer.getCustomerId(), voucher.getVoucherId())).thenReturn(Optional.of(voucherWallet));

        voucherWalletService.deleteVoucherWallet(customer.getCustomerId(), voucher.getVoucherId());

        verify(voucherWalletRepository).deleteVoucherWallet(voucherWallet);
    }
}