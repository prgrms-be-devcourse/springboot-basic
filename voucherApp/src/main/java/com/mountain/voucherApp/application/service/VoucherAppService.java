package com.mountain.voucherApp.application.service;

import com.mountain.voucherApp.application.port.in.CustomerDto;
import com.mountain.voucherApp.application.port.in.VoucherAppUseCase;
import com.mountain.voucherApp.application.port.in.VoucherCreateDto;
import com.mountain.voucherApp.application.port.in.VoucherIdUpdateDto;
import com.mountain.voucherApp.application.port.out.BlackListPort;
import com.mountain.voucherApp.application.port.out.CustomerPort;
import com.mountain.voucherApp.application.port.out.VoucherPort;
import com.mountain.voucherApp.adapter.out.file.BlackListFileFormat;
import com.mountain.voucherApp.shared.UseCase;
import com.mountain.voucherApp.adapter.out.persistence.voucher.VoucherEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.mountain.voucherApp.shared.constants.ProgramMessage.CREATE_NEW_VOUCHER;

@UseCase
public class VoucherAppService implements VoucherAppUseCase {

    private static final Logger log = LoggerFactory.getLogger(VoucherAppService.class);
    private final CustomerPort customerPort;
    private final VoucherPort voucherPort;
    private final BlackListPort blackListPort;

    public VoucherAppService(CustomerPort customerPort,
                             VoucherPort voucherPort,
                             BlackListPort blackListPort) {
        this.customerPort = customerPort;
        this.voucherPort = voucherPort;
        this.blackListPort = blackListPort;
    }

    @Override
    public VoucherEntity create(VoucherCreateDto voucherCreateDto) {
        Optional<VoucherEntity> findEntity = voucherPort.findByPolicyIdAndDiscountAmount(voucherCreateDto.getPolicyId(),
                voucherCreateDto.getDiscountAmount());
        VoucherEntity newVoucherEntity = null;
        if (!findEntity.isPresent()) {
            newVoucherEntity = new VoucherEntity(UUID.randomUUID(),
                    voucherCreateDto.getPolicyId(),
                    voucherCreateDto.getDiscountAmount());
            voucherPort.insert(newVoucherEntity);
            log.info(CREATE_NEW_VOUCHER);
        }
        return findEntity.orElse(newVoucherEntity);
    }

    @Override
    public List<VoucherEntity> showVoucherList() {
        return voucherPort.findAll();
    }

    @Override
    public List<CustomerDto> showCustomerVoucherInfo() {
        return customerPort.findAll();
    }

    @Override
    public void addVoucher(VoucherIdUpdateDto voucherIdUpdateDto) {
        customerPort.updateVoucherId(voucherIdUpdateDto.getCustomerId(),
                voucherIdUpdateDto.getVoucherId());
    }

    @Override
    public void removeVoucher(UUID customerId) {
        customerPort.updateVoucherId(customerId, null);
    }

    @Override
    public List<CustomerDto> showByVoucher(UUID voucherId) {
        return customerPort.findByVoucherId(voucherId);
    }

    @Override
    public List<BlackListFileFormat> getBlackList() throws IOException {
        return blackListPort.getBlackList();
    }
}
