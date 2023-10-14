package org.prgrms.vouchermanager.Repository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

class CsvCustomerRepositoryTest {

    CustomerRepositroy repository = new CsvCustomerRepository();


    @Test
    public void findAll(){
        List<List<String>> all = repository.findAll();
        String s = all.get(0).get(1);
        assertThat(s).isEqualTo("최인준");
    }

}