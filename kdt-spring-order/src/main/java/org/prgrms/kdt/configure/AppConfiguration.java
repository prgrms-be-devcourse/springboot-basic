package org.prgrms.kdt.configure;

import org.prgrms.kdt.configure.Voucher;
import org.prgrms.kdt.entity.Order;
import org.prgrms.kdt.io.IoConsole;
import org.prgrms.kdt.repo.OrderRepository;
import org.prgrms.kdt.repo.VoucherMemoryRepo;
import org.prgrms.kdt.repo.VoucherRepository;
import org.prgrms.kdt.service.OrderService;
import org.prgrms.kdt.service.VoucherService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Configuration
public class AppConfiguration {

    @Bean
    public VoucherMemoryRepo voucherMemoryRepo(){
        return new VoucherMemoryRepo(){
            @Override
            public Optional<Voucher> findById(UUID voucherId) {
                return super.findById(voucherId);
            }

            @Override
            public List<Voucher> findAll() {
                return super.findAll();
            }

            @Override
            public void save(Voucher voucher) {
                super.save(voucher);
            }
        };
    }


    //과제는 VoucherMemoryRep 이용
    @Bean
    public VoucherRepository voucherRepository(){
        return new VoucherRepository() {
            @Override
            public Optional<Voucher> findById(UUID voucherId) {
                return Optional.empty();
            }
            @Override
            public List<Voucher> findAll() {
                return null;
            }
            @Override
            public void save(Voucher voucher) {
            }
        };
    }

    @Bean
    public OrderRepository orderRepository(){
        return new OrderRepository() {
            @Override
            public void save(Order order) {
            }
        };
    }

    @Bean
    public VoucherService voucherService(){
        return new VoucherService(voucherMemoryRepo());
    }

    @Bean
    public OrderService orderService(){
        return new OrderService(voucherService(),orderRepository());
    }


    @Bean
    public IoConsole ioConsole(){
        return new IoConsole();
    }





}
