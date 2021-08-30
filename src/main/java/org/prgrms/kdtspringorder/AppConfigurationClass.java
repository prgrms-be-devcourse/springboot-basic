package org.prgrms.kdtspringorder;

import org.prgrms.kdtspringorder.io.abstraction.Input;
import org.prgrms.kdtspringorder.io.abstraction.Output;
import org.prgrms.kdtspringorder.io.implementation.ConsoleInput;
import org.prgrms.kdtspringorder.io.implementation.ConsoleOutput;
import org.prgrms.kdtspringorder.voucher.repository.abstraction.VoucherRepository;
import org.prgrms.kdtspringorder.voucher.repository.implementation.MemoryVoucherRepository;
import org.prgrms.kdtspringorder.voucher.service.VoucherService;
import org.prgrms.kdtspringorder.io.validation.CommandValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@ComponentScan(
    basePackages = {"org.prgrms.kdtspringorder.io", "org.prgrms.kdtspringorder.voucher"},
    basePackageClasses = {App.class}
)
public class AppConfigurationClass {}
