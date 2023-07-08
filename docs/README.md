# Voucher Application

## 기능 명세

### Voucher domain

- repository
  - VoucherRepository 구현한 InMemoryVoucherRepository, JdbcVoucherRepository
- domain
  - Voucher 클래스를 엔티티로 사용
  - VoucherPolicy 인터페이스를 구현한 FixedAmountPolicy, PercentDiscountPolicy
  - VoucherType enum 지정
- application
  - CRUD 담당하는 VoucherService
- dto
  - 바우처 생성에 필요한 VoucherCreateRequest
  - 바우처 수정에 필요한 VoucherUpdateRequest
  - 생성된 바우처 반환에 필요한 VoucherResponse
- presentation
  - ManagementController 와 연결되는 VoucherController

<br>

### Customer domain

- repository
  - CustomerRepository 구현한 JdbcCustomerRepository
- domain
  - Customer 클래스를 엔티티로 사용
  - Customer 가 보유한 바우처를 저장하는 Wallet
  - FindType enum 지정 (id, email 어느 것으로 찾을 지)
- application
  - CRUD 담당하는 CustomerService
- dto
  - 고객 생성에 필요한 CustomerCreateRequest
  - 고객 수정에 필요한 CustomerUpdateRequest
  - 생성된 고객 반환에 필요한 CustomerResponse
  - 보유한 바우처 반환에 필요한 WalletResponse
- presentation
  - ManagementController 와 연결되는 CustomerController

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
- LoggerAspect 에서 aop 활용한 로깅