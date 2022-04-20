package com.example.voucherproject.common.config;

import com.example.voucherproject.common.io.console.Input;
import com.example.voucherproject.common.io.console.Output;
import com.example.voucherproject.common.io.file.MyReader;
import com.example.voucherproject.common.io.file.MyWriter;
import com.example.voucherproject.voucher.repository.VoucherFileRepository;
import com.example.voucherproject.voucher.repository.VoucherRepository;
import com.example.voucherproject.voucher.service.VoucherService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VoucherServiceConfig {

    @Bean
    public VoucherService voucherService(Input input, Output output, VoucherRepository voucherRepository){
        return new VoucherService(input, output, voucherRepository);
    }

    @Bean
    public VoucherRepository voucherRepository(MyReader reader, MyWriter writer){
        return new VoucherFileRepository(reader, writer);
    }

}
