package com.programmers.part1.member.repository;

import java.io.IOException;
import java.util.List;

public interface MemberRepository <ID, T>{
    List<T> findAllBlackMember() throws IOException;
}
