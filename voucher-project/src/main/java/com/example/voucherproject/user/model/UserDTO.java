package com.example.voucherproject.user.model;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Null;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Getter
@Builder
@RequiredArgsConstructor
public class UserDTO {

    private final UUID id;

    @NotEmpty
    private final String name;

    @NotEmpty
    private final UserType type;

    public static User asUserModel(UserDTO userDTO) {
        // TODO: validation
        return User.builder()
                .id(userDTO.getId())
                .name(userDTO.getName())
                .type(userDTO.getType())
                .createdAt(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS))
                .updatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS))
                .build();
    }

    public static UserDTO asUserDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .type(user.getType())
                .build();
    }
}
/*

이러한 이유로 @Valid는 기본적으로 컨트롤러에서만 동작하며
기본적으로 다른 계층에서는 검증이 되지 않는다.
 다른 계층에서 파라미터를 검증하기 위해서는 @Validated와 결합되어야 하는데,

파라미터의 유효성 검증은 컨트롤러에서 처리하고
서비스나 레포지토리 계층에서는 유효성 검증을 하지 않는 것이 바람직하다.
@Validated를 클래스 레벨에 선언하면 해당 클래스에 유효성 검증을 위한 인터셉터(MethodValidationInterceptor)가 등록된다. 그리고 해당 클래스의 메소드들이 호출될 때 AOP의 포인트 컷으로써 요청을 가로채서 인터셉터를 통해 유효성 검증을 진행한다. (물론 이러한 동작을 위해 @Validated가 붙은 클래스는 CGLib 기반으로 바이트 조작이 된다.)

이러한 이유로 @Validated를 사용하면 컨트롤러, 서비스, 레포지토리 등 계층에 무관하게 스프링 빈이라면 유효성 검증을 진행할 수 있다. 물론 @Validated는 유효성 검증 인터셉터를 등록하는 것이므로 검증을 진행할 메소드에는 @Valid를 선언해주어야 한다.

Controller

    @PatchMapping("/academies/{id}")
    public AcademyDto.Result patch(@PathVariable Long id,
        @Valid @RequestBody AcademyDto.Update dto, Authentication authentication) {
        return academyService.update(id, dto, authentication);
}
 */