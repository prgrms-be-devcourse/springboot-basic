package org.prgms.w3d1.service;

import org.prgms.w3d1.model.customer.Customer;
import org.prgms.w3d1.model.voucher.Voucher;
import org.prgms.w3d1.model.wallet.VoucherWallet;
import org.prgms.w3d1.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId){
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(()-> new RuntimeException("Can not find a voucher for " + voucherId));
    }

    public void saveVoucher(Voucher voucher){
        voucherRepository.save(voucher);
    }

    public List<Voucher> findAll(){
        return voucherRepository.findAll();
    }

    public VoucherWallet findVoucherWallet(UUID customerId) {
        return voucherRepository.findVoucherWallet(customerId);
    }

    public void serVoucherWallet(Customer customer, VoucherWallet voucherWallet) {
        customer.setVoucherWallet(voucherWallet);
    }

    public void useVoucher(Voucher voucher) {

    }

}
