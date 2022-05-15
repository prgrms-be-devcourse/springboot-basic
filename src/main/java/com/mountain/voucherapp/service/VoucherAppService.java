package com.mountain.voucherapp.service;


import com.mountain.voucherapp.dao.blackList.BlackListFileFormat;
import com.mountain.voucherapp.dto.CustomerDto;
import com.mountain.voucherapp.dto.VoucherCreateDto;
import com.mountain.voucherapp.dto.VoucherIdUpdateDto;
import com.mountain.voucherapp.model.VoucherEntity;

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
