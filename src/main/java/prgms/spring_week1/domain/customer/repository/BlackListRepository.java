package prgms.spring_week1.domain.customer.repository;

import org.springframework.stereotype.Repository;
import prgms.spring_week1.domain.customer.model.BlackConsumer;

import java.util.List;

@Repository
public interface BlackListRepository {
    List<BlackConsumer> getBlackConsumerList();
}
