# SpringBoot Basic Weekly2 Mission


### 설계도 

![image](/doc/바우처설계도_Step2.png)



### 요구사항

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


### 작업 할 순서 목록

- [ ] JdbcVouceherRespotitory CRUD 구현
- [ ] JdbcVouceherRespotitory CRUD 테스트코드 작성
- [ ] VoucherService 수정, 삭제, 조건 조회 기능 구현 
- [ ] VoucherService 수정, 삭제, 조건 조회 기능 테스트 코드 작성
- [ ] JdbcCustomerRespotitory CRUD 구현
- [ ] JdbcCustomerRespotitory CRUD 테스트 코드 작성
- [ ] CustomerService 생성, 수정, 삭제, 조회 기능 구현
- [ ] CustomerService 생성, 수정, 삭제, 조회 기능 테스트 코드 작성
- [ ] CustomerController 생성, 수정, 삭제, 조건 조회 요청 기능 구현
- [ ] ApplicationController 사용자 기능 요청 기능 구현
- [ ] Console 사용자관련 입출력 기능 구현
- [ ] Controller, Console 테스트코드 작성 
- [ ] CustomerService 사용자의 바우처 생성 조회 삭제 기능 구현 
- [ ] CustomerService 사용자의 바우처 생성 조회 삭제 테스트 코드 작성
- [ ] VoucherService 특정 바우처 보유 고객 조회 조회 기능 구현
- [ ] VoucherService 특정 바우처 보유 고객 조회 조회 테스트 코드 작성 
