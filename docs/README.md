# Voucher Application

## 기능 명세

### Voucher domain

- repository
  - VoucherRepository 구현한 InMemoryVoucherRepository
- domain
  - Voucher 클래스를 엔티티로 사용
  - VoucherPolicy 인터페이스를 구현한 FixedAmountPolicy, PercentDiscountPolicy
  - VoucherType enum 지정
- application
  - 생성과 조회를 담당하는 VoucherService
- dto
  - 바우처 생성에 필요한 VoucherCreateRequest
  - 생성된 바우처 반환에 필요한 VoucherResponse
- presentation
  - service 와 view 연결해주는 VoucherController

<br>

### ManagementController

- CommandLineRunner 이용하여 스프링부트 애플리케이션으로 생성

<br>

### view

- Input 인터페이스, Output 인터페이스를 구현한 구현체를 의존하지 않고 추상체에 의존하도록 Console 에 주입

<br>

### global

- AppConfig 에서 view 주입
- exception 에서 커스텀 익셉션 관리