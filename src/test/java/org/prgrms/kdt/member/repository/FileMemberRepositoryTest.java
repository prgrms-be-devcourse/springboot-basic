package org.prgrms.kdt.member.repository;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class FileMemberRepositoryTest {

    public FileMemberRepository fileMemberRepository;

    @BeforeEach
    public void setup(){
        fileMemberRepository = new FileMemberRepository();
        fileMemberRepository.init();
    }
}