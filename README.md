# SpringBoot Basic Weekly Mission

### 프로젝트 설계도 및 클래스 역할/책임
![image](https://github.com/sujjangOvO/springboot-basic/assets/89267864/a809b214-0ac2-42aa-be37-b62fb27e9645)



---
**(기본)** **바우처 관리 애플리케이션**

- [ ]  바우처 관리 애플리케이션에 단위테스트를 작성해보세요.
    - 가능한 많은 단위 테스트코드를 작성하려고 노력해보세요.
    - 엣지 케이스(예외 케이스)를 고려해서 작성해주세요.
    - Hamcrest 의 메쳐들을 다양하게 작성해보고 익숙해져 보세요.
- [ ]  바우처 관리 애플리케이션에서도 과정에서 다루었던 고객을 적용해보세요.
    - customers 테이블 정의 및 추가
    - CustomerRepository 추가 및 JdbcTemplate을 사용해서 구현
- [ ]  (1주차엔 파일로 관리하게 했다.) 바우처 정보를 DB로 관리해보세요.
    - 바우처에 엔터티에 해당하는 vouchers 테이블을 한번 정의해보세요.
    - 바우처 레포지토리를 만들어보세요. (JdbcTemplate을 사용해서 구현)
    - 기존의 파일에서 바우처를 관리한 것을 vouchers 테이블을 통해서 CRUD가 되게 해보세요.

**(심화)** **바우처 지갑을 만들어보세요.**

- 특정 고객에게 바우처를 할당할 수 있습니다.
- 고객이 어떤 바우처를 보유하고 있는지 조회할 수 있어야 합니다.
- 고객이 보유한 바우처를 제거할 수 있어야 합니다.
- 특정 바우처를 보유한 고객을 조회할 수 있어야 합니다.

---
### 작업 순서 List
- [ ] 메모리 바우처 관리 테스트 코드 작성
    - 바우처 메뉴 입력 테스트
    - 바우처 create 테스트
    - 바우처 crate 유효성 테스트
    - 바우처 list 테스트
- [ ] customer 도메인 정의 및 추가
    - customer는 voucher를 가진다 (FK)
- [ ] jdbcVoucherRepository 추가 및 구현
    - id로 voucher 탐색
    - db에 voucher save
    - db의 id에 해당하는 voucher delete
    - db의 모든 voucher findAll
- [ ] mysql db와 연결
    - jdbc template 설정
- [ ] jdbcCustomerRepository 추가 및 구현
    - id로 customer 탐색
    - db에 customer save
    - db의 id에 해당하는 customer delete
    - db의 모든 customer findAll
- [ ] jdbcCustomerRepository 테스트 코드 작성
    - 고객 조회 테스트
    - 고객이 가진 바우처 조회 테스트
