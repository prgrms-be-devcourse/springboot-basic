package com.wonu606.vouchermanager.service.customerVoucherWallet;

import com.wonu606.vouchermanager.domain.CustomerVoucherWallet.CustomerVoucherWallet;
import com.wonu606.vouchermanager.domain.CustomerVoucherWallet.CustomerVoucherWalletDto;
import com.wonu606.vouchermanager.domain.customer.emailAddress.EmailAddress;
import com.wonu606.vouchermanager.repository.customerVoucherWallet.CustomerVoucherWalletRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class CustomerVoucherWalletService {

    private final CustomerVoucherWalletRepository customerVoucherWalletRepository;

    public CustomerVoucherWalletService(
            CustomerVoucherWalletRepository customerVoucherWalletRepository) {
        this.customerVoucherWalletRepository = customerVoucherWalletRepository;
    }

    public List<UUID> findVoucherIdByCustomerEmailAddress(String emailAddress) {
        return customerVoucherWalletRepository.findIdByCustomerEmailAddress(emailAddress);
    }

    public void deleteByCustomerVoucherWallet(CustomerVoucherWalletDto customerVoucherWalletDto) {
        CustomerVoucherWallet customerVoucherWallet = convertDtoToWallet(customerVoucherWalletDto);
        customerVoucherWalletRepository.deleteByWallet(customerVoucherWallet);
    }

    public CustomerVoucherWallet save(CustomerVoucherWalletDto customerVoucherWalletDto) {
        CustomerVoucherWallet customerVoucherWallet = convertDtoToWallet(customerVoucherWalletDto);
        return customerVoucherWalletRepository.save(customerVoucherWallet);
    }

    public List<String> findEmailAddressesByVoucherId(UUID voucherId) {
        return customerVoucherWalletRepository.findEmailAddressesByVoucherId(voucherId);
    }

    private static CustomerVoucherWallet convertDtoToWallet(
            CustomerVoucherWalletDto customerVoucherWalletDto) {
        return new CustomerVoucherWallet(
                customerVoucherWalletDto.getVoucherId(),
                new EmailAddress(customerVoucherWalletDto.getEmailAddress())
        );
    }

}
