package com.programmers.customer.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;

import com.programmers.customer.repository.BlacklistRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class BlacklistRepositoryTest {

    String filePath = "src/test/resources/blacklist.csv";
    BlacklistRepository blacklistRepository = new BlacklistRepository(filePath);

    @DisplayName("블랙리스트를 조회한다")
    @Test
    void findAll() {
        //given
        String expected = "[ BlackList User Id = e876b46c-44f5-42e7-83b6-779aa2416a47, name = Gold ]";

        //when
        List<String> result = blacklistRepository.findAll();

        //then
        assertThat(result, hasItems(expected));
    }
}