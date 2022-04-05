
package org.prgms.voucheradmin.global.config;

import static org.prgms.voucheradmin.global.database.Database.vouchers;

import java.util.List;

import org.prgms.voucheradmin.domain.voucher.console.VoucherConsole;
import org.prgms.voucheradmin.domain.voucher.dao.VoucherRepository;
import org.prgms.voucheradmin.domain.voucher.entity.Voucher;
import org.prgms.voucheradmin.domain.voucher.service.VoucherService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {
    @Bean
    public VoucherRepository voucherRepository() {
        return new VoucherRepository() {
            @Override
            public Voucher create(Voucher voucher) {
                vouchers.add(voucher);
                return voucher;
            }

            @Override
            public List<Voucher> getAll() {
                return vouchers;
            }
        };
    }

    @Bean
    public VoucherService voucherService(VoucherRepository voucherRepository) {
        return new VoucherService(voucherRepository);
    }

    @Bean
    public VoucherConsole voucherConsole(VoucherService voucherService) {
        return new VoucherConsole(voucherService);
    }
}
