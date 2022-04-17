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
import org.prgms.voucheradmin.domain.voucherwallet.dto.CreatVoucherWalletReqDto;
import org.prgms.voucheradmin.domain.voucherwallet.entity.VoucherWallet;
import org.prgms.voucheradmin.global.exception.CustomerNotFoundException;
import org.prgms.voucheradmin.global.exception.VoucherNotFoundException;
import org.prgms.voucheradmin.global.exception.VoucherWalletNotFoundException;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
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

    CreatVoucherWalletReqDto creatVoucherWalletReqDto = new CreatVoucherWalletReqDto(UUID.randomUUID(), UUID.randomUUID());
    Customer customer = new Customer(creatVoucherWalletReqDto.getCustomerId(), "a", "a@test.com", LocalDateTime.now());
    Voucher voucher = new FixedAmountVoucher(creatVoucherWalletReqDto.getVoucherId(), 1000);
    VoucherWallet voucherWallet = new VoucherWallet(UUID.randomUUID(), customer.getCustomerId(), voucher.getVoucherId());

    @Test
    @DisplayName("바우처 지갑 생성 고객 예외 톄스트")
    void testCreateVoucherWalletCustomerException() {
        try {
            when(customerRepository.findById(creatVoucherWalletReqDto.getCustomerId())).thenThrow(new CustomerNotFoundException(creatVoucherWalletReqDto.getCustomerId()));

            voucherWalletService.createVoucherWallet(creatVoucherWalletReqDto);
        }catch(CustomerNotFoundException e) {
            verify(voucherRepository, never()).findById(creatVoucherWalletReqDto.getVoucherId());
        }
    }

    @Test
    @DisplayName("바우처 지갑 생성 바우처 예외 톄스트")
    void testCreateVoucherWalletVoucherException() {
        try {
            when(customerRepository.findById(creatVoucherWalletReqDto.getCustomerId())).thenReturn(Optional.of(customer));
            when(voucherRepository.findById(creatVoucherWalletReqDto.getVoucherId())).thenThrow(new VoucherNotFoundException(creatVoucherWalletReqDto.getVoucherId()));

            voucherWalletService.createVoucherWallet(creatVoucherWalletReqDto);
        }catch(VoucherNotFoundException e) {
            verify(voucherWalletRepository, never()).create(any());
        }
    }

    @Test
    @DisplayName("바우처 지갑 생성 톄스트")
    void testCreateVoucherWallet() {
        when(customerRepository.findById(creatVoucherWalletReqDto.getCustomerId())).thenReturn(Optional.of(customer));
        when(voucherRepository.findById(creatVoucherWalletReqDto.getVoucherId())).thenReturn(Optional.of(voucher));

        voucherWalletService.createVoucherWallet(creatVoucherWalletReqDto);

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