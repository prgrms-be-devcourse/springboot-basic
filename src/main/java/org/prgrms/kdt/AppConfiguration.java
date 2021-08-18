package org.prgrms.kdt;


import org.prgrms.kdt.Model.Order;
import org.prgrms.kdt.Model.Voucher;
import org.prgrms.kdt.Repository.OrderRepository;
import org.prgrms.kdt.Repository.VoucherRepository;
import org.prgrms.kdt.Service.OrderService;
import org.prgrms.kdt.Service.VoucherService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

//설정 메타 데이터(빈을 정의 한 도면인것을)인것을 알려줘야한다
@Configuration
//의존 관계를 맺는 클래스
public class AppConfiguration {
    private final Map<UUID,Voucher> db=new HashMap<>();

    @Bean
    public VoucherRepository voucherRepository() {

        return new VoucherRepository() {
            @Override
            public Optional<Voucher> findById(UUID voucherId) {
                return Optional.empty();
            }

            @Override
            public Voucher save(Voucher voucher) {
                return db.put(voucher.getVoucherId(),voucher);
            }

            @Override
            public List<Voucher> findAllById() {
               return new ArrayList<>(db.values());
            }

        };



    }
   @Bean
    public OrderRepository orderRepository() {
        return new OrderRepository() {
            @Override
            public void save(Order order) {

            }
        };
    }


    @Bean
    public VoucherService voucherService() {
        return new VoucherService(voucherRepository());
    }
    @Bean
    public OrderService orderService() {
        return new OrderService(voucherService(), orderRepository());
    }

}