package com.programmers.part1.configuration;

import com.programmers.part1.member.MemberController;
import com.programmers.part1.member.MemberService;
import com.programmers.part1.member.repository.FileMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(
        basePackages = "com.programmers.part1"
)
public class AppConfiguration {

}
