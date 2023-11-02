package com.weeklyMission.member.dto;

import com.weeklyMission.member.domain.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequest{

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotNull
    private Integer age;

    public Member toEntity(){
        return new Member(UUID.randomUUID().toString(), name, email, age);
    }
}
