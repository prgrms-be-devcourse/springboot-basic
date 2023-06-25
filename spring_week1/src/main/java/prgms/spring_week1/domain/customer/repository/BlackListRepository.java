package prgms.spring_week1.domain.customer.repository;

import prgms.spring_week1.domain.customer.model.BlackConsumer;
import java.util.List;

public interface BlackListRepository {
    public List<BlackConsumer> getBlackConsumerList();
}
