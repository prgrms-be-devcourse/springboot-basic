package com.example.voucherproject.common.config;

import com.example.voucherproject.common.io.console.Input;
import com.example.voucherproject.common.io.console.Output;
import com.example.voucherproject.common.io.file.MyReader;
import com.example.voucherproject.common.io.file.MyWriter;
import com.example.voucherproject.voucher.domain.VoucherFactory;
import com.example.voucherproject.voucher.repository.VoucherFileRpository;
import com.example.voucherproject.voucher.repository.VoucherRepository;
import com.example.voucherproject.voucher.service.VoucherService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VoucherServiceConfig {

    @Bean
    public VoucherService voucherService(Input input, Output output, VoucherFactory voucherFactory, VoucherRepository voucherRepository){
        return new VoucherService(input, output, voucherFactory, voucherRepository);
    }

    @Bean
    public VoucherRepository voucherRepository(MyReader reader, MyWriter writer){
        return new VoucherFileRpository(reader, writer);
    }

    @Bean
    public VoucherFactory voucherFactory(){
        return new VoucherFactory();
    }
}
