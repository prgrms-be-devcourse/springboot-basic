package com.programmers.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.programmers.repository.customer.BlacklistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

class BlacklistServiceTest {

    @Mock
    private BlacklistRepository blacklistRepository;

    private BlacklistService blacklistService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        blacklistService = new BlacklistService(blacklistRepository);
    }

    @DisplayName("모든 블랙리스트를 조회한다")
    @Test
    public void findAll() {
        //given
        List<String> expectedBlacklist = new ArrayList<>();
        expectedBlacklist.add("[ BlackList User Id = e876b46c-44f5-42e7-83b6-779aa2416a47, name = Gold ]");
        expectedBlacklist.add("[ BlackList User Id = grw3wd4g-5g8r-32dd-24d4-dfe11gt4ht44, name = Ridden ]");
        expectedBlacklist.add("[ BlackList User Id = d5ewa2de-d6g2-45kj-f9q7-df1f2s4da24c, name = Mary ]");

        when(blacklistRepository.findAll()).thenReturn(expectedBlacklist);

        //when
        List<String> actualBlacklist = blacklistService.findAll();

        //then
        assertThat(expectedBlacklist, is(actualBlacklist));
    }
}