package com.mountain.voucherApp.service;


import com.mountain.voucherApp.dao.blackList.BlackListFileFormat;
import com.mountain.voucherApp.dto.CustomerDto;
import com.mountain.voucherApp.dto.VoucherCreateDto;
import com.mountain.voucherApp.dto.VoucherIdUpdateDto;
import com.mountain.voucherApp.model.VoucherEntity;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface VoucherAppService {

    boolean create(VoucherCreateDto voucherCreateDto);

    List<VoucherEntity> showVoucherList();

    List<CustomerDto> showCustomerVoucherInfo();

    void addVoucher(VoucherIdUpdateDto voucherIdUpdateDto);

    void removeVoucher(UUID customerId);

    List<CustomerDto> showByVoucher(UUID voucherId);

    List<BlackListFileFormat> getBlackList() throws IOException;

}
