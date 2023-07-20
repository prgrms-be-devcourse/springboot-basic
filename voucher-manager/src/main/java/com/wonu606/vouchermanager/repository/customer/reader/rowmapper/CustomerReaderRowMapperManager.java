package com.wonu606.vouchermanager.repository.customer.reader.rowmapper;

import com.wonu606.vouchermanager.util.TypedRowMapper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CustomerReaderRowMapperManager {

    private final List<TypedRowMapper<?>> rowMapperList;

    public CustomerReaderRowMapperManager() {
        rowMapperList = new ArrayList<>();
        rowMapperList.add(new CustomerResultSetRowMapper());
    }

    @SuppressWarnings("unchecked")
    public <T> TypedRowMapper<T> getRowMapperForType(Class<T> targetType) {
        for (TypedRowMapper<?> rowMapper : rowMapperList) {
            if (rowMapper.canConvert(targetType)) {
                return (TypedRowMapper<T>) rowMapper;
            }
        }
        throw new IllegalArgumentException(targetType.getName() + "으로 변환할 수 없습니다.");
    }
}
